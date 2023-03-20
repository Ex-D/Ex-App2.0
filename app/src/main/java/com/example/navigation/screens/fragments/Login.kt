package com.example.navigation.screens.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.viewModels.viewModel.GoogleSignInVM
import com.example.navigation.viewModels.viewModel.LoginVM
import com.example.navigation.databinding.FragmentLoginBinding
import com.example.navigation.screens.activities.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.auth.api.Auth

import com.google.android.gms.auth.api.signin.GoogleSignInResult





@AndroidEntryPoint
class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginVM: LoginVM
    private lateinit var snackbar: Snackbar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInVM: GoogleSignInVM
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginVM = ViewModelProvider(this)[LoginVM::class.java]

        mAuth = FirebaseAuth.getInstance()
        //for google sign in
        googleSignInVM = ViewModelProvider(this)[GoogleSignInVM::class.java]
        googleSignInClient = googleSignInVM.gSignIn()

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.LoginBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (!checkMail() or !checkPassword()) {
                Log.d("Verification Failed", " ")
                binding.progressBar.visibility = View.GONE
            } else {
                val response = loginVM.loginWithEmailPassword(
                    binding.LoginMail.text.toString(),
                    binding.LoginPassword.text.toString()
                )
                Log.d("loginresponse","$response")

                response.addOnSuccessListener {
                    displaySnackBar("Logged In Successfully :)")
                    findNavController().navigate(R.id.action_login_to_home)
                    binding.progressBar.visibility = View.GONE
                }.addOnFailureListener {
                    displaySnackBar("Authentication Failed")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        binding.gSignIn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            signInGoogle()
        }
        binding.ForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_resetPassword2)
        }
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        return binding.root
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent

        launcher.launch(signInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        Log.d("handleresult","${result}")
        // to be  solved ...
        //02-12-22 01:50 AM signinclientissue //solved
        if(result.resultCode == Activity.RESULT_OK){
            val id: GoogleSignInResult? = result.data?.let {
                Auth.GoogleSignInApi.getSignInResultFromIntent(it)
            }

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }else{
            binding.progressBar.visibility = View.GONE
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result

            if (account != null) {
                updateUi(account)
            } else {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUi(account: GoogleSignInAccount) {
        Log.d("testing","${account.idToken}")
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)

        mAuth.signInWithCredential(credential).addOnCompleteListener { task->
            if (task.isSuccessful){
                binding.progressBar.visibility = View.GONE
                displaySnackBar("Signed In Successfully :)")
                startActivity(Intent(activity,HomeActivity::class.java))
            }else{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun checkPassword(): Boolean {
        return if ((binding.LoginPassword.text.toString()).isEmpty()) {
            binding.LoginPassword.error = "Password cannot be empty!"
            false
        } else {
            binding.LoginPassword.error = null
            true
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }
}