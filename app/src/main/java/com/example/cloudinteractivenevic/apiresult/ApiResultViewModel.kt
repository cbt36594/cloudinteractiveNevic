package com.example.cloudinteractivenevic.apiresult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractivenevic.api.cloudService
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ApiResultViewModel : ViewModel() {

    private var _id = MutableLiveData<String>()
    val id: LiveData<String> = _id
    private var _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private var _thumbnailUrl = MutableLiveData<String>()
    val thumbnailUrl: LiveData<String> = _thumbnailUrl
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _photoResponse = MutableLiveData<List<Photos>>()
    val photoResponse: LiveData<List<Photos>> = _photoResponse

    var clickGetPhotos: () -> Unit = {}
    var clickItemDetail: () -> Unit = {}

    fun getPhotos() {
        _isLoading.value = true
        viewModelScope.launch {
            cloudService.fetchPhotos()
                .onStart {

                }
                .catch { error ->
                    _isLoading.value = false
                    println(error.message)
                }
                .onCompletion {

                }
                .collectLatest {
                    Log.d("nevic", "完成")
                    Log.d("nevic", "$it")
                    _isLoading.value = false
                    _photoResponse.value = it
                }
        }
    }

    fun setId(str: String) {
        _id.value = str
    }

    fun setTitle(str: String) {
        _title.value = str
    }

    fun setThumbnailUrl(str: String) {
        _thumbnailUrl.value = str
    }
}