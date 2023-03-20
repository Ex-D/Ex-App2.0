package com.example.navigation.screens.activities


import android.Manifest.permission.*
import android.app.Dialog

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.internal.annotation.CameraExecutor
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.OneShotPreDrawListener.add
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.camera.core.Preview
import com.example.navigation.R
import com.example.navigation.databinding.ActivityTestactivityBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityTestactivityBinding
    private lateinit var customDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTestactivityBinding.inflate(layoutInflater)
        customDialog = Dialog(this)
        setContentView(_binding.root)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(com.example.navigation.R.id.bottomNavigationView)
        val navHostFragment =
            supportFragmentManager.findFragmentById(com.example.navigation.R.id.fgx) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        _binding.CameraBtn.setOnClickListener {
            showDialog()

        }
    }

    private fun showDialog() {
        //end the dialog when it is on resumed
        customDialog.setContentView(com.example.navigation.R.layout.creategifdialog)
        val dismissGifDialog = customDialog.findViewById<ImageView>(R.id.dismissGifDialog)
        val startCameraDialog = customDialog.findViewById<Button>(R.id.gifCamera)
        val startGalleryDialog = customDialog.findViewById<Button>(R.id.ChooseFromGallery)
        startCameraDialog.setOnClickListener {
            startActivity(Intent(this, CustomCameraActivity::class.java))
            finish()
        }
        customDialog.show()
        customDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        customDialog.window?.setGravity(Gravity.BOTTOM)
        dismissGifDialog.setOnClickListener {
            customDialog.dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        customDialog.dismiss()
    }


//    fun replaceFragment(){
//        supportFragmentManager.commit {
//            replace(com.example.navigation.R.id.fgx, Camera(), "Your_TAG").addToBackStack(null)
//        }
//    }


}


