package com.example.cloudinteractivenevic.apiresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel(val id_: String,val title_: String,val thumbnailUrl_: String) : ViewModel() {
    private var _id = MutableLiveData<String>()
    val id: String = id_
    private var _title = MutableLiveData<String>()
    val title: String = title_
    private var _thumbnailUrl = MutableLiveData<String>()
    val thumbnailUrl: String = thumbnailUrl_

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