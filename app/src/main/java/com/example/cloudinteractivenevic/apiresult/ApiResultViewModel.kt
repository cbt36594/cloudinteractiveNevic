package com.example.cloudinteractivenevic.apiresult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractivenevic.api.CloudService
import com.example.cloudinteractivenevic.api.adpter.FlowCallAdapterFactory
import com.example.cloudinteractivenevic.api.cloudService
import com.example.cloudinteractivenevic.api.httpClient.client
import com.example.cloudinteractivenevic.extension.onApiFailed
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiResultViewModel : ViewModel() {

    private var _id = MutableLiveData<String>()
    val id: LiveData<String> = _id
    private var _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _photoResponse = MutableLiveData<List<Photos>>()
    val photoResponse: LiveData<List<Photos>> = _photoResponse

    var clickGetPhotos: () -> Unit = {}
    var clickItemDetail: () -> Unit = {}

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
                Log.d("nevic", "完成")
                Log.d("nevic", "$it")
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

}