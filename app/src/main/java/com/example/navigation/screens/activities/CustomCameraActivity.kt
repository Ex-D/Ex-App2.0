package com.example.navigation.screens.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.navigation.databinding.ActivityCustomCameraBinding
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.common.util.concurrent.ListenableFuture
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CustomCameraActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityCustomCameraBinding
    private lateinit var executor: ExecutorService
    private lateinit var imageCaptureInstance: ImageCapture
    private lateinit var previewUseCase: Preview
    private lateinit var processCameraProvider: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraInstance:Camera
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCustomCameraBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()
        previewUseCase = Preview.Builder().build()
        processCameraProvider = ProcessCameraProvider.getInstance(this)
        executor = Executors.newSingleThreadExecutor()
        imageCaptureInstance = ImageCapture.Builder().build()
        requestCameraPermission()


        _binding.cameraBtn.setOnClickListener {
            takePhoto()
            processCameraProvider.get().unbind(previewUseCase)
            _binding.cameraZoomSeekbar.visibility = View.GONE
        }
        //UseItWithBtnClickListener

    }

    override fun onResume() {
        super.onResume()
        _binding.cameraZoomSeekbar.progress = 0
        startCamera()
    }

    private fun requestCameraPermission() {
        requestCameraPermissionIfMissing() { granted ->
            if (granted)
                startCamera()
            else
                Toast.makeText(this, "Please Allow the Permission", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestCameraPermissionIfMissing(onResult: ((Boolean) -> Unit)) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
            onResult(true)
        else
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                onResult(it)
            }.launch(android.Manifest.permission.CAMERA)

    }

    private fun startCamera() {
        processCameraProvider.addListener({
            val cameraProvider = processCameraProvider.get()
            previewUseCase.also { it.setSurfaceProvider(_binding.cameraPreview.surfaceProvider) }

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA, previewUseCase,
                    imageCaptureInstance
                )
                zoomUsingSeekBar(camera)
                getCameraInstance(camera)
//                zoomUsingGesture(camera)

            } catch (e: java.lang.Exception) {
                Log.e(ContentValues.TAG, "Camera Preview Failed", e)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCaptureInstance ?: return
        //ForDateFormat for storing unique photos specific timings are considered
        val name = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }
        val outPutOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()
        imageCapture.takePicture(outPutOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e(
                        ContentValues.TAG,
                        "Photo capture Failed: ${exception.message}",
                        exception
                    )
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, msg)
                }
            }
        )

    }
    private fun zoomUsingGesture(camera:Camera){
        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                // Get the camera's current zoom ratio
                val currentZoomRatio = camera.cameraInfo.zoomState.value?.zoomRatio ?: 0F

                // Get the pinch gesture's scaling factor
                val delta = detector.scaleFactor
                // Update the camera's zoom ratio. This is an asynchronous operation that returns
                // a ListenableFuture, allowing you to listen to when the operation completes.
                camera.cameraControl.setZoomRatio(currentZoomRatio * delta)

                // Return true, as the event was handled
                return true
            }
        }
        val scaleGestureDetector = ScaleGestureDetector(this, listener)

// Attach the pinch gesture listener to the viewfinder
        _binding.cameraPreview.setOnTouchListener { view, event ->
            view.performClick()
            scaleGestureDetector.onTouchEvent(event)
            return@setOnTouchListener true
        }

    }
    private fun zoomUsingSeekBar(camera:Camera){
        _binding.cameraZoomSeekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                camera.cameraControl.setLinearZoom(progress / 100.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    fun getCameraInstance(camera:Camera):Camera{
        return camera
    }


}