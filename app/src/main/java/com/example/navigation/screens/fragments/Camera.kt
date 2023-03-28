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
import androidx.lifecycle.lifecycleScope
import com.example.navigation.R
import com.example.navigation.databinding.FragmentCameraBinding
import com.google.android.material.snackbar.Snackbar
import com.slowmac.autobackgroundremover.BackgroundRemover
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener
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
    private lateinit var snackbar: Snackbar
    private lateinit var bitmap: Bitmap
    private lateinit var contentResolver: ContentResolver
    private lateinit var matrix: Matrix

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        intent = requireActivity().intent

        uri = intent.getStringExtra("imageUri")?.toUri()!!
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        removeBg(uri)

        return binding.root
    }

    private fun removeBg(uri: Uri){
        lifecycleScope.launch(Dispatchers.IO){
            val newbitmap:Bitmap? = getBitmap(uri)
            if (newbitmap != null) {
                BackgroundRemover.bitmapForProcessing(
                    newbitmap,
                    true,
                    object: OnBackgroundChangeListener {
                        override fun onSuccess(bitmap: Bitmap) {
                            //bitmap
                            binding.gifImage.setImageBitmap(bitmap)
                        }
                        override fun onFailed(exception: Exception) {
                            displaySnackBar("Failed to remove background")
                        }
                    }
                )
            }
        }
    }

    fun getBitmap(uri: Uri): Bitmap? {
        val inputStream: InputStream? = activity?.contentResolver?.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun displaySnackBar(text:String){
        snackbar = view?.let { it1 ->
            Snackbar.make(
                it1,
                text,
                Snackbar.LENGTH_LONG
            )
        }!!
        snackbar.show()
    }
}