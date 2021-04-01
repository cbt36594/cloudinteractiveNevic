package com.example.cloudinteractivenevic.apiresult

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractivenevic.api.cloudService
import com.example.cloudinteractivenevic.extension.BitmapCache
import com.example.cloudinteractivenevic.extension.toBitmap
import com.example.cloudinteractivenevic.extension.urlToBitmap
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import java.net.URL

class ApiResultViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _photoResponse = MutableLiveData<List<Photos>>()
    val photoResponse: LiveData<List<Photos>> = _photoResponse

    private val _urlBitmap = MutableLiveData<String>()
    val urlBitmap: LiveData<String> = _urlBitmap
    private val _urlImage = MutableLiveData<URL>()
    val urlImage: LiveData<URL> = _urlImage

    var clickGetPhotos: () -> Unit = {}
    var clickItemDetail: (id:String, title:String,thumbnailUrl:String) -> Unit = { _, _, _ ->  }

    @ExperimentalCoroutinesApi
    fun getPhotos() {
        _isLoading.value = true
        viewModelScope.launch {
            cloudService.fetchPhotos()
                .onStart {

                }
                .catch { error ->
                    _isLoading.value = false
                    Timber.d("${error.message}")
                }
                .onCompletion {

                }
                .collectLatest {
                    _isLoading.value = false
                    _photoResponse.value = it
                }
        }
    }

    fun setUrl(url:String) {
        _urlBitmap.value = url
        _urlImage.value = URL(url)
        val result: Deferred<Bitmap?> = GlobalScope.async(Dispatchers.IO) {
            urlImage.value?.toBitmap()
        }

        GlobalScope.launch {
            val bitmapCache = BitmapCache()
//            val bitmap: Bitmap? = bitmapCache.getBitmap(url)
            val temp = result.await()
//            if (bitmap == null) {
            bitmapCache.putBitmap(urlBitmap.value, temp)
            Log.d("TestBit: ", "await1 ${temp}")
            Log.d("TestBit: ", "await2")
//            }
            Log.d("TestBit: ", "${bitmapCache.getBitmap(urlBitmap.value)}")
            temp?.apply {
                bitmapCache.putBitmap(urlBitmap.value, temp)
//                val bitmap: Bitmap? = bitmapCache.getBitmap(url)
//                val savedUri: Uri? = saveToInternalStorage(image.context)
                Timber.d("TestBit: url")
                Log.d("TestBit2: ", "${bitmapCache.getBitmap(urlBitmap.value)}")
//                image.setImageURI(savedUri)
            }
        }
    }
}