package com.example.cloudinteractivenevic.apiresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractivenevic.api.cloudService
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

class ApiResultViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _photoResponse = MutableLiveData<List<Photos>>()
    val photoResponse: LiveData<List<Photos>> = _photoResponse

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
}