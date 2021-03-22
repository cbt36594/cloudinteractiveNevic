package com.example.cloudinteractivenevic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractivenevic.api.cloudService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel()  {
    private var _id = MutableLiveData<Int>(0)
    val id: LiveData<Int> = _id
    private var _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    var clickGetPhotos: () -> Unit = {}

    fun getPhotos() {
        viewModelScope.launch {
            cloudService.fetchPhotos()
                .onStart {

                }
                .catch { error ->
                    println(error.message)
                }
                .onCompletion {

                }
                .collectLatest {
                    Log.d("nevic", "$it")
                    _title.value = it[10].title
                }
        }
    }
}