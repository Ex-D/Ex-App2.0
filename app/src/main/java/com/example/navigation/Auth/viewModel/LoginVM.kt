package com.example.navigation.Auth.viewModel

import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LoginVM (){
    lateinit var mAuth: FirebaseAuth
}