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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.navigation.R
import com.example.navigation.databinding.FragmentCameraBinding
import com.example.navigation.viewModels.viewModel.MlGifViewModel
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
    private lateinit var mlGifViewModel: MlGifViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        intent = requireActivity().intent
        mlGifViewModel = ViewModelProvider(this)[MlGifViewModel::class.java]
        uri = intent.getStringExtra("imageUri")?.toUri()!!
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        mlGifViewModel.getRemovedPhoto(uri,requireContext())
        lifecycleScope.launch {
            mlGifViewModel.mainEvent.collect() { event ->
                when (event) {
                    is MlGifViewModel.MainEvent.SetGifImageBitmap -> {
                        binding.gifImage.setImageBitmap(event.bitmap)
                    }
                    is MlGifViewModel.MainEvent.ShowSnackBar -> {
                        displaySnackBar("Failed to remove background")
                    }
                }
            }
        }
        return binding.root
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