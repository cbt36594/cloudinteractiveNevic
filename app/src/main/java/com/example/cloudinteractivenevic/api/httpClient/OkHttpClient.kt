package com.example.cloudinteractivenevic.api.httpClient

import com.example.cloudinteractivenevic.api.interceptor.DefaultHttpLoggingInterceptor
import com.example.cloudinteractivenevic.api.interceptor.interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.internal.immutableListOf
import java.util.concurrent.TimeUnit

const val DEFAULT_CONNECTION_TIME_OUT_IN_SECONDS: Long = 20
const val DEFAULT_READ_TIME_OUT_IN_SECONDS: Long = 20
const val DEFAULT_WRITE_TIME_OUT_IN_SECONDS: Long = 20

const val DEFAULT_BACK_PRESSED_LIMIT_TIME: Long = 1500

val defaultClientBuilder: OkHttpClient.Builder
    get() = OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(true)
        .cache(null)
        .connectTimeout(
            DEFAULT_CONNECTION_TIME_OUT_IN_SECONDS,
            TimeUnit.SECONDS
        )
        .readTimeout(DEFAULT_READ_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_WRITE_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(DefaultHttpLoggingInterceptor())

val client: OkHttpClient
    get() =
        defaultClientBuilder
            .protocols(immutableListOf(Protocol.HTTP_1_1))
            .build()
