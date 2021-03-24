package com.example.cloudinteractivenevic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel()  {
    private var _id = MutableLiveData<Int>(0)
    val id: LiveData<Int> = _id
    private var _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    var clickGetPhotos: () -> Unit = {}

}