package com.example.navigation.screens.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.auth.viewModel.LoginVM
import com.example.navigation.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : Fragment() {
    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!
    private lateinit var loginVM: LoginVM
    private lateinit var snackbar: Snackbar
    lateinit var manager: FragmentManager
    private var check =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loginVM = ViewModelProvider(this)[LoginVM::class.java]
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
//        binding.progressBar.visibility = View.INVISIBLE
        binding.LoginBtn.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
           if(!checkMail() or !checkPassword()){
               Log.d("Verification Failed"," ")
               binding.progressBar.visibility=View.GONE
           }
            else {
                val response = loginVM.loginWithEmailPassword (
                    binding.LoginMail.text.toString(),
                    binding.LoginPassword.text.toString() )
                response.addOnSuccessListener {
                    snackbar = view?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "Logged In Successfully :)",
                            Snackbar.LENGTH_LONG
                        )
                    }!!
                    snackbar.show()
                    findNavController().navigate(R.id.action_login_to_home)
                    binding.progressBar.visibility=View.GONE
                }.addOnFailureListener {
                    snackbar = view?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "Authentication Failed",
                            Snackbar.LENGTH_LONG
                        )
                    }!!
                    snackbar.show()
                    binding.progressBar.visibility=View.GONE
                }
            }
        }
        binding.ForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_resetPassword2)
        }
        return binding.root
    }
    private fun checkMail(): Boolean {
            val email: String = binding.LoginMail.text.toString().trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            return if (email.isEmpty()) {
                binding.LoginMail.error = "Email can not be empty"
                false
            } else if (!email.matches(emailPattern.toRegex())) {
                binding.LoginMail.error = "Email format is Wrong!"
                false
            } else {
                binding.LoginMail.error = null
                true
            }
    }
    private fun checkPassword():Boolean{
        return if((binding.LoginPassword.text.toString()).isEmpty()){
            binding.LoginPassword.error = "Password cannot be empty!"
            false
        }else{
            binding.LoginPassword.error = null
            true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }
}