package com.example.navigation.screens.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.auth.viewModel.GoogleSignInVM
import com.example.navigation.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {
    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var snackbar:Snackbar
    private lateinit var mAuth: FirebaseAuth
    lateinit var  acct:GoogleSignInAccount
    private lateinit var googleSignInVM:GoogleSignInVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        mAuth = FirebaseAuth.getInstance()
        val account = context?.let { it1 -> GoogleSignIn.getLastSignedInAccount(it1) }
        googleSignInVM = ViewModelProvider(this)[GoogleSignInVM::class.java]
        binding.signout.setOnClickListener {
            //firebase signout
            //03-12-22 12:09 PM SignOut issue with gSignIn
                mAuth.signOut()
                displaySnackBar("Ex-D Bye!")
//                val q = googleSignInVM.gSignIn().signOut()
                    Log.d("gsign","${mAuth.signOut()}")

                findNavController().navigate(R.id.action_apphome_to_register)

        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

}