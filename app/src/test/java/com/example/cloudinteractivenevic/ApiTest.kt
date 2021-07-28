package com.example.cloudinteractivenevic

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractivenevic.api.CloudService
import com.example.cloudinteractivenevic.api.adpter.FlowCallAdapterFactory
import com.example.cloudinteractivenevic.api.converter.defaultGsonConverterFactory
import com.example.cloudinteractivenevic.api.httpClient.defaultClientBuilder
import com.example.cloudinteractivenevic.extension.launchInWithDefaultErrorHandler
import com.example.cloudinteractivenevic.extension.onApiFailed
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.internal.immutableListOf
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiTest {
    val client: OkHttpClient
        get() {
            return defaultClientBuilder()
                .protocols(immutableListOf(Protocol.HTTP_1_1))
                .build()
        }

    val api: CloudService
        get() = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .client(client)//show basic log
            .build()
            .create(CloudService::class.java)

    @Test
    fun testApi() {
        var temp : List<Photos>
//        runBlocking {
//            api.fetchPhotos()
//                .onStart {
//
//                }
//                .catch { error ->
//                    println(error.message)
//                }
//                .onCompletion {
//
//                }
//                .collectLatest {
//                    temp = it
//                    println(it)
//                }
//        }
    }

    @Test
    fun testApi2() {

        runBlocking {
            api.fetchPhotos()
                .onApiFailed {
                    Log.d("nevic", "${it.error.detail}")
                    println(it)
                }.map { it.body() }
                .filterNotNull()
                .collectLatest {
                    Log.d("nevic", "${it[0].id}")
                    println(it[0].id)
                }
        }
    }
}