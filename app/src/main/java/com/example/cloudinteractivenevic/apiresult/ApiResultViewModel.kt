package com.example.cloudinteractivenevic.apiresult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractivenevic.api.cloudService
import com.example.cloudinteractivenevic.extension.launchInWithDefaultErrorHandler
import com.example.cloudinteractivenevic.extension.onApiFailed
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ApiResultViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _photoResponse = MutableLiveData<List<Photos>>()
    val photoResponse: LiveData<List<Photos>> = _photoResponse

    var clickGetPhotos: () -> Unit = {}
    var clickItemDetail: (id:String, title:String,thumbnailUrl:String) -> Unit = { _, _, _ ->  }

    fun getPhotos() {
        _isLoading.value = true
        cloudService.fetchPhotos()
            .onApiFailed {
                Log.i("Rect", " catchError: $it")
            }
            .map { it.body() }
            .filterNotNull()
            .onEach {
                    _isLoading.value = false
                    _photoResponse.value = it
            }.launchInWithDefaultErrorHandler(viewModelScope) {
                Log.i("Rect", " catchError: $it")
            }
    }
}