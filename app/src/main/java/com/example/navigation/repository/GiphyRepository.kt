package com.example.navigation.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.navigation.models.dto.GiphyApiDto
import com.example.navigation.models.remote.GiphyApi
import com.example.navigation.paging.GifPagingSource
import com.example.navigation.paging.NETWORK_PAGE_SIZE
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val api:GiphyApi
) {
    suspend fun getGifs() = api.getGifs()
    fun getGiphy() = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false),
        pagingSourceFactory = { GifPagingSource(api) }
    ).liveData
}