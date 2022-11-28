package com.example.navigation.auth.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor (
    private val application: Application
        ):ViewModel(){

    lateinit var mAuth: FirebaseAuth
    fun loginWithEmailPassword(email: String, password: String): Task<AuthResult> {
        mAuth = FirebaseAuth.getInstance()
        return mAuth.signInWithEmailAndPassword(email,password)
    }


}