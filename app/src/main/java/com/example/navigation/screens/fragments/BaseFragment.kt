package com.example.navigation.screens.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigation.R
import com.example.navigation.databinding.FragmentBaseBinding
import com.example.navigation.databinding.FragmentCameraBinding


class BaseFragment : Fragment() {
    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var mProgressDialog:Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBaseBinding.inflate(layoutInflater,container,false)
        binding.progressBar
        return binding.root
    }
    fun showProgressBar(){
        mProgressDialog = Dialog(requireActivity())
        mProgressDialog.setContentView(binding.progressBar)
        mProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }


}