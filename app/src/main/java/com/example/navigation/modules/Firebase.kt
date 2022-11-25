package com.example.navigation.modules

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Auth {

    @Provides
    fun fireObject(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}