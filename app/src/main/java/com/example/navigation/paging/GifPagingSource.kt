package com.example.navigation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.navigation.models.dto.Data
import com.example.navigation.models.dto.GiphyApiDto
import com.example.navigation.models.remote.GiphyApi
import com.example.navigation.repository.GiphyRepository
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 50
private const val INITIAL_LOAD_SIZE = 1

class GifPagingSource @Inject constructor(val api: GiphyApi) :
    PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        var position = params.key ?: INITIAL_LOAD_SIZE
        val offset =
            if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE
        return try {
            val response: List<Data> =
                api.getGifs(limit = NETWORK_PAGE_SIZE, offset = offset).body()!!.data
            val nextKey = if (response.toString().isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey,
            )

        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}