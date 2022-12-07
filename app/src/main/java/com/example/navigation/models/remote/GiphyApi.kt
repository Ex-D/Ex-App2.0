package com.example.navigation.models.remote

import com.example.navigation.models.dto.GiphyApiDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("/v1/gifs/search")
    suspend fun getGifs(
        @Query("api_key") key:String=Constants.apiKey,
        @Query("q")q:String = "flower",
        @Query("limit")limit:Int = 50,
    ): Response<GiphyApiDto>

}