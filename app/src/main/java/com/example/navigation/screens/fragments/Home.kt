package com.example.navigation.screens.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.navigation.R
import com.example.navigation.databinding.FragmentGifpageBinding
import com.example.navigation.viewModels.viewModel.GiphyViewModel
import com.example.navigation.viewModels.viewModel.GoogleSignInVM
import com.example.navigation.paging.GifPagingAdapter
import com.example.navigation.viewModels.adapters.GifAdapter
import com.example.navigation.viewModels.viewModel.GiphyPagingViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {
    private var _binding: FragmentGifpageBinding? = null
    private val binding get() = _binding!!
    private lateinit var snackbar: Snackbar
    private lateinit var giphyViewModel: GiphyPagingViewModel
    private lateinit var imageRecycler: RecyclerView
    private lateinit var adapter: GifPagingAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInVM: GoogleSignInVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGifpageBinding.inflate(inflater, container, false)
        imageRecycler = binding.recyclerView
        adapter = GifPagingAdapter()
        imageRecycler.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        imageRecycler.adapter = adapter
        giphyViewModel = ViewModelProvider(this)[GiphyPagingViewModel::class.java]
        getGifs()
        mAuth = FirebaseAuth.getInstance()
        googleSignInVM = ViewModelProvider(this)[GoogleSignInVM::class.java]
        binding.signout.setOnClickListener {
            //firebase signout
            //03-12-22 12:09 PM SignOut issue with gSignIn
            signOut()
        }
        return binding.root
    }

    fun getGifs() {
//        giphyViewModel.gifsResp.observe(viewLifecycleOwner) { gifs ->
//            Log.d("gifs", "$gifs")
//            adapter.initData(gifs)
//        }
        giphyViewModel.list.observe(viewLifecycleOwner){
            adapter.submitData(lifecycle,it)
        }
    }

    fun signOut() {
        mAuth.signOut()
        displaySnackBar("Ex-D Bye!")
        Log.d("gsign", "${mAuth.signOut()}")

        findNavController().navigate(R.id.action_apphome_to_register)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displaySnackBar(text: String) {
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