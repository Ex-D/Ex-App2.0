package com.example.navigation.viewModels.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.models.dto.Data
import com.example.navigation.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class GiphyViewModel @Inject constructor(
    private val repo:GiphyRepository,
    private val app:Application
):ViewModel() {

    init {
        getGiphy()
    }
    private val _resp = MutableLiveData<List<Data>>()
    val gifsResp: LiveData<List<Data>>
        get() = _resp


    private fun getGiphy() =
        viewModelScope.launch {
            repo.getGifs(50,100).let {
                response->
                if(response.isSuccessful){
                    Log.d("getgifs","${response.body()!!.data}")
                    _resp.postValue(response.body()!!.data)
                }else{
                    Toast.makeText(app, "Something Went Wrong :(", Toast.LENGTH_SHORT).show()
                }
            }
        }

}