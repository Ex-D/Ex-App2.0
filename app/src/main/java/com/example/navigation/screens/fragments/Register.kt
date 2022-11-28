package com.example.navigation.screens.fragments

import android.R.attr.password
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.auth.viewModel.RegisterVM
import com.example.navigation.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern


@AndroidEntryPoint
class Register : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private lateinit var registerVM: RegisterVM
    private lateinit var mAuth: FirebaseAuth
    private val binding get() = _binding!!
    private lateinit var snackbar: Snackbar
    private var eX = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mAuth = FirebaseAuth.getInstance()
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        registerVM = ViewModelProvider(this)[RegisterVM::class.java]
        binding.navToUsrLgn.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        binding.SignupBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (!isEmailFilled() or !isNameFilled() or !isPasswordFilled() or !isConfirmPasswordFilled() or !isPasswordMatch()) {
                snackbar = view?.let { it1 ->
                    Snackbar.make(
                        it1,
                        "Something went wrong!",
                        Snackbar.LENGTH_LONG
                    )
                }!!
                snackbar.show()
                binding.progressBar.visibility=View.GONE
            } else {
                val response = registerVM.signInClient(
                    binding.SignUpEmail.text.toString(),
                    binding.SignupPassword.text.toString()
                )
                response.addOnSuccessListener { task ->
                    snackbar = view?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "Account Created Successfully",
                            Snackbar.LENGTH_LONG
                        )
                    }!!
                    snackbar.show()
                    val currentUser = mAuth.currentUser
                    val userInfo: MutableMap<String, Any> = HashMap()
                    userInfo["name"] = binding.SignUpName.text.toString()
                    findNavController().navigate(R.id.action_register_to_main)
                    binding.progressBar.visibility = View.GONE


                }.addOnFailureListener {
                    snackbar = view?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "Account Creation Failed",
                            Snackbar.LENGTH_LONG
                        )
                    }!!
                    snackbar.show()
                    binding.progressBar.visibility = View.GONE
                }
            }

        }
        return binding.root
    }

    private fun isNameFilled(): Boolean {
        val userName: String = binding.SignUpName.text.toString().trim()
        return if (userName.isEmpty()) {
            binding.SignUpName.error = "Name can not be empty"
            false
        } else {
            binding.SignUpName.error = null
            true
        }
    }

    private fun isEmailFilled(): Boolean {
        val email: String = binding.SignUpEmail.text.toString().trim()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return if (email.isEmpty()) {
            binding.SignUpEmail.error = "Email can not be empty"
            false
        } else if (!email.matches(emailPattern.toRegex())) {
            binding.SignUpEmail.error = "Invalid Email!"
            false
        } else {
            binding.SignUpEmail.error = null
            true
        }
    }

    private fun isPasswordFilled(): Boolean {
        val passwordValue: String = binding.SignupPassword.text.toString().trim()
        val password = binding.SignupPassword
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$"
        )
        return if (passwordValue.isEmpty()) {
            password.error = "Password can not be empty"

            false
        } else if (!passwordValue.matches(passwordREGEX.toRegex())) {
            password.error =
                "Password must contain 8 digits containing at least 1 digit , 1 uppercase letter , 1 lower case , 1 special character !"
            false
        } else {
            password.error = null
            true
        }
    }

    private fun isConfirmPasswordFilled(): Boolean {
        return if (binding.SignUpConfirmPassword.text.toString().isEmpty()) {
            binding.SignUpConfirmPassword.error = "Field cannot be empty!"
            false
        } else {
            true
        }
    }

    private fun isPasswordMatch(): Boolean {
        return if (binding.SignupPassword.text.toString() != binding.SignUpConfirmPassword.text.toString()) {
            snackbar = view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Password Do Not Match",
                    Snackbar.LENGTH_LONG
                )
            }!!
            snackbar.show()
            false
        } else {
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

//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }

}