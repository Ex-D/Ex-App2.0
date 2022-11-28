package com.example.navigation.screens.activities

//Rohit -- 16-11-22 -- 13:36 (started project added dependencies and initiated project)
// Update -- 25-11-22 -- 16:56 (Splash screen api added)
//Update 3 -- 28-11-22 -- 10:02 AM (Login and Sign Up done with Firebase)

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import com.example.navigation.R
import com.example.navigation.screens.fragments.Login
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser!=null){
            val manager: FragmentManager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment,com.example.navigation.screens.fragments.Main()).commit()
            //doNot add the fragment to addToBackStack as this will give you false results
           //            transaction.addToBackStack(null).commit()
        }

    }

}