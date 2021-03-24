package com.example.cloudinteractivenevic.apiresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    private var _id = MutableLiveData<String>()
    val id: LiveData<String> = _id
    private var _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private var _thumbnailUrl = MutableLiveData<String>()
    val thumbnailUrl: LiveData<String> = _thumbnailUrl

    var clickItemDetailBack: () -> Unit = {}

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