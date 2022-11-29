package com.example.navigation.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.databinding.FragmentHomeBinding
import com.example.navigation.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


class Home : Fragment() {
    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var snackbar:Snackbar
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        mAuth = FirebaseAuth.getInstance()
        binding.signout.setOnClickListener {
            mAuth.signOut()
            displaySnackBar("See you Soon 🙋‍")
            findNavController().navigate(R.id.register)
//            val fragmentManager = requireActivity().supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(com.example.navigation.R.id.nav_host_fragment, Login())
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
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