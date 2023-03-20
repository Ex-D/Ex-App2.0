package com.example.navigation.screens.fragments


import android.content.ContentResolver
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.navigation.R
import com.example.navigation.databinding.FragmentCameraBinding
import ja.burhanrashid52.photoeditor.PhotoEditorView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream


class Camera : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var bundle: Bundle
    private lateinit var intent: Intent
    private lateinit var uri: Uri
    private lateinit var bitmap: Bitmap
    private lateinit var contentResolver: ContentResolver
    private lateinit var matrix: Matrix

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        intent = requireActivity().intent

        uri = intent.getStringExtra("imageUri")?.toUri()!!
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        val inputStream: InputStream? = activity?.contentResolver?.openInputStream(uri)
        var yourDrawable = Drawable.createFromStream(inputStream, uri.toString())


        val mPhotoEditorView: PhotoEditorView = binding.photoEditorView

        GlobalScope.launch(Dispatchers.Main) {
            val test = withContext(Dispatchers.IO) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        requireContext().contentResolver,
                        uri
                    )
                )
            }

//            val bmp = getBitmapFromView(binding.editImage)

//            val bmp: Bitmap = Bitmap.createBitmap(binding.editImage.getDrawingCache())
//            Log.d("editbitmap","$bitmap_1")

            binding.removebg.setOnClickListener {
//                combineImages(test, bmp)
//                binding.gifImage.setImageBitmap(combineImages(test,bmp))
//                binding.gifImage.invalidate()
//                BackgroundRemover.bitmapForProcessing(
//                    test, true,
//                    object : OnBackgroundChangeListener {
//                        override fun onSuccess(bitmap: Bitmap) {
//                            //do what ever you want to do with this bitmap
//
//                            binding.gifImage.setImageBitmap(bitmap)
//                            Log.d("imagetesting", "${bitmap}")
//                        }
//
//                        override fun onFailed(exception: Exception) {
//                            Toast.makeText(
//                                requireContext(),
//                                "Something went wrong",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                )


                //here is code 1

            }
        }

//        getBitmap()


        return binding.root
    }

    private fun combineImages(background: Bitmap?, foreground: Bitmap?): Bitmap {
        val matrix = Matrix()
        val rect = Rect()
        rect.set(0,0,100,100)

//        matrix.setValues(floatArrayOf(1f, .5f, 0f, 0f, 1f, 0f, 0f, 0f, 1f))
        var width = 0
        var height = 0
        width = background!!.width
        height = background.height
        matrix.postScale(width.toFloat(), height.toFloat())
        val cs: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val comboImage = Canvas(cs)

        val storage = Bitmap.createScaledBitmap(background, width, height, true)

        comboImage.drawBitmap(storage,matrix,null)
//        comboImage.drawBitmap(storage,rect, null)
        if (foreground != null) {
            comboImage.drawBitmap(storage,matrix,null)

        }
        return cs
    }


    fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width, view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
    fun getBitmap(): Bitmap {
        Thread(Runnable {
            // a potentially time consuming task
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        requireContext().contentResolver,
                        uri
                    )
                )
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
        }).start()
        return bitmap
    }


}