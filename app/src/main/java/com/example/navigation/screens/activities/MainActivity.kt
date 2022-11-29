package com.example.navigation.screens.activities

//Rohit -- 16-11-22 -- 13:36 (started project added dependencies and initiated project)
// Update -- 25-11-22 -- 16:56 (Splash screen api added)
//Update 3 -- 28-11-22 -- 10:02 AM (Login and Sign Up done with Firebase)
//update 4 -- 29-11-22 -- 16:41 navigation (some issues left like after  exiting the app when backbutton pressed)

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.example.navigation.R
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
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.apphome)
//            val manager: FragmentManager = supportFragmentManager
//            val transaction = manager.beginTransaction()
//            transaction.replace(R.id.nav_host_fragment,com.example.navigation.screens.fragments.Home()).commit()
            //doNot add the fragment to addToBackStack as this will give you false results
           //            transaction.addToBackStack(null).commit()
        }

    }

}