package com.example.navigation.screens.activities


import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.navigation.R
import com.slowmac.autobackgroundremover.BackgroundRemover
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener


class MlGifActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ml_gif)
        supportActionBar?.hide()


    }

}