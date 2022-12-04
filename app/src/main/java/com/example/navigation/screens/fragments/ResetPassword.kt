package com.example.navigation.screens.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.auth.viewModel.ResetPasswordVM
import com.example.navigation.databinding.FragmentResetPasswordBinding
import com.google.android.material.snackbar.Snackbar


class ResetPassword : Fragment() {
    private var _binding: FragmentResetPasswordBinding?=null
    private val binding get() = _binding!!
    private lateinit var snackbar: Snackbar
    private lateinit var resetPasswordVM: ResetPasswordVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater,container,false)
        resetPasswordVM = ViewModelProvider(this)[ResetPasswordVM::class.java]
        binding.ResetBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if(!isEmailFilled()){
                Log.d("Validation Failed"," ")
                binding.progressBar.visibility = View.GONE
            }else{
                val response = resetPasswordVM.resetPassword(binding.ResetEmail.text.toString().trim())
                response.addOnSuccessListener {
                    snackbar = view?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "Email sent successfully :)",
                            Snackbar.LENGTH_LONG
                        )
                    }!!
                    snackbar.show()
                    binding.progressBar.visibility = View.GONE
                    binding.cancelText.text = "Back to login"
                }.addOnFailureListener {
                    snackbar = view?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "User doesn't Exist",
                            Snackbar.LENGTH_LONG
                        )
                    }!!
                    binding.progressBar.visibility = View.GONE
                    snackbar.show()
                }
            }
        }
        binding.cancelText.setOnClickListener {

            findNavController().popBackStack()


        }
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return binding.root
    }


    private fun isEmailFilled(): Boolean {
        val email: String = binding.ResetEmail.text.toString().trim()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return if (email.isEmpty()) {
            binding.ResetEmail.error = "Email can not be empty"
            false
        } else if (!email.matches(emailPattern.toRegex())) {
            binding.ResetEmail.error = "Invalid Email!"
            false
        } else {
            binding.ResetEmail.error = null
            true
        }
    }


}