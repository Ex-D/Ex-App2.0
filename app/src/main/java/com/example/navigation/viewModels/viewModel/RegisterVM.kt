package com.example.navigation.viewModels.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class RegisterVM  :ViewModel() {
    lateinit var mAuth: FirebaseAuth
    fun signInClient(email:String,password:String): Task<AuthResult> {
        mAuth = FirebaseAuth.getInstance()
        return mAuth.createUserWithEmailAndPassword(email,password)
    }
}