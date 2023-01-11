package com.example.navigation.viewModels.viewModel

import android.app.Application


import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GoogleSignInVM @Inject constructor(
    private val app: Application
) : ViewModel() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    fun gSignIn(): GoogleSignInClient {
        val gso=  GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("83242210429-pf8vslcva6m3gk1gpk7et15ls53ab3qo.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(app,gso)
        return mGoogleSignInClient
    }

}