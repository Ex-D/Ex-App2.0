package com.example.navigation.viewModels.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.cachedIn
import com.example.navigation.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import javax.inject.Inject

@HiltViewModel
class GiphyPagingViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository
): ViewModel(){
    @OptIn(DelicateCoroutinesApi::class)
    val list = giphyRepository.getGiphy().cachedIn(GlobalScope)
}