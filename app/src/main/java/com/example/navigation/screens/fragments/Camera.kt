package com.example.navigation.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigation.R
import com.example.navigation.databinding.FragmentCameraBinding
import com.example.navigation.databinding.FragmentGifpageBinding
import com.example.navigation.databinding.FragmentRegisterBinding

class Camera : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

}