package com.example.navigation.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.navigation.models.remote.GiphyApi
import com.example.navigation.paging.GifPagingSource
import com.example.navigation.paging.NETWORK_PAGE_SIZE
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val api:GiphyApi
) {
   suspend fun getGifs(limit:Int,offset:Int) = api.getGifs(limit = limit, offset = offset)
    fun getGiphy() = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false),
        pagingSourceFactory = {GifPagingSource(api)}
    ).liveData
}