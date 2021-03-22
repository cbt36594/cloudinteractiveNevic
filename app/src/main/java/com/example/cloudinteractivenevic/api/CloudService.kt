package com.example.cloudinteractivenevic.api

import com.example.cloudinteractivenevic.api.adpter.FlowCallAdapterFactory
import com.example.cloudinteractivenevic.api.converter.defaultGsonConverterFactory
import com.example.cloudinteractivenevic.api.httpClient.client
import com.example.cloudinteractivenevic.model.SuccessResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface CloudService {

    val cloudService: CloudService
        get() = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .client(client)
            .build()
            .create(CloudService::class.java)

    @GET("/photos")
    fun fetchPhotos(): Flow<String>

}