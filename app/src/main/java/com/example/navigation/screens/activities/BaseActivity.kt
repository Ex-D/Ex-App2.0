package com.example.navigation.screens.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigation.R
import com.example.navigation.databinding.ActivityBaseBinding
import com.example.navigation.databinding.FragmentBaseBinding

open class BaseActivity : AppCompatActivity() {
    private var _binding: ActivityBaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var mProgressDialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun showProgressBar(){
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.activity_base)
        mProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
}