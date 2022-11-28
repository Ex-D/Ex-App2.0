package com.example.navigation.auth.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordVM:ViewModel() {
    private lateinit var mAuth: FirebaseAuth
    fun resetPassword(email:String): Task<Void> {
        mAuth = FirebaseAuth.getInstance()
        return mAuth.sendPasswordResetEmail(email)
    }
}