package com.example.navigation.repository

import android.util.Log
import com.example.navigation.models.dto.GiphyApiDto
import com.example.navigation.models.remote.GiphyApi
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val api:GiphyApi
) {
    suspend fun getGifs() = api.getGifs()
}