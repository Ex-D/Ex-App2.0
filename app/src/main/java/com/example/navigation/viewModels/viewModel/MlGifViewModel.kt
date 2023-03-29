package com.example.navigation.viewModels.viewModel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowmac.autobackgroundremover.BackgroundRemover
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.InputStream
import java.lang.ref.WeakReference
import javax.inject.Inject

class MlGifViewModel @Inject constructor():ViewModel() {
    private val mainEventChannel = Channel<MainEvent>()
    val mainEvent = mainEventChannel.receiveAsFlow()


    fun getRemovedPhoto(uri: Uri,context: Context){
        viewModelScope.launch(Dispatchers.IO){
            val newbitmap: Bitmap? = getBitmap(uri,context)
            if (newbitmap != null) {
                BackgroundRemover.bitmapForProcessing(
                    newbitmap,
                    true,
                    object: OnBackgroundChangeListener {
                        override fun onSuccess(bitmap: Bitmap) {
                            val setBitmapEvent = MainEvent.SetGifImageBitmap(bitmap)
                            viewModelScope.launch {
                                mainEventChannel.send(setBitmapEvent)
                            }
                        }
                        override fun onFailed(exception: Exception) {
                            val failureEvent = MainEvent.ShowSnackBar("Failed to remove background")
                            viewModelScope.launch {
                                mainEventChannel.send(failureEvent)
                            }
//                            displaySnackBar("Failed to remove background")
                        }
                    }
                )
            }
        }
    }

    sealed class MainEvent {
        data class ShowSnackBar(val msg: String) : MainEvent()
        data class SetGifImageBitmap(val bitmap: Bitmap) : MainEvent()
    }


    private fun getBitmap(uri: Uri,context:Context): Bitmap? {
        val inputStream: InputStream? = context.contentResolver?.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }
}