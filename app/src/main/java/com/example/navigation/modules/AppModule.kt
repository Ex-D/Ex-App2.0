package com.example.navigation.modules

import com.example.navigation.models.remote.Constants
import com.example.navigation.models.remote.GiphyApi
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun fireObject(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun gSignIn(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    @Provides
    @Singleton
    fun retrofitHelper(): GiphyApi =
        Retrofit.Builder().baseUrl(Constants.Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApi::class.java)


}