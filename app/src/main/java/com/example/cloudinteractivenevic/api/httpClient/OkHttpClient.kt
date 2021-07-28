package com.example.cloudinteractivenevic.api.httpClient

import android.text.TextUtils
import android.util.Log
import com.example.cloudinteractivenevic.api.interceptor.DefaultHttpLoggingInterceptor
import com.example.cloudinteractivenevic.api.interceptor.interceptor
import com.example.cloudinteractivenevic.common.CustomX509TrustManager
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.internal.immutableListOf
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

const val DEFAULT_CONNECTION_TIME_OUT_IN_SECONDS: Long = 20
const val DEFAULT_READ_TIME_OUT_IN_SECONDS: Long = 20
const val DEFAULT_WRITE_TIME_OUT_IN_SECONDS: Long = 20

const val DEFAULT_BACK_PRESSED_LIMIT_TIME: Long = 1500



fun defaultClientBuilder(): OkHttpClient.Builder {
    val apiServerUrl: List<String> = "https://jsonplaceholder.typicode.com".split("/")

    val sslContext: SSLContext = SSLContext.getInstance("TLSv1.2")

    sslContext.init(null, CustomX509TrustManager.getTrustManagers(null), SecureRandom())
    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

    return OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(true)
        .cache(null)
        .connectTimeout(
            DEFAULT_CONNECTION_TIME_OUT_IN_SECONDS,
            TimeUnit.SECONDS
        )
        .sslSocketFactory(
            sslSocketFactory,
            CustomX509TrustManager.getTrustManagers(null)[0] as X509TrustManager
        )
        .hostnameVerifier { hostname, session ->// 校驗域名是否合法
            TextUtils.equals(apiServerUrl[2], hostname)
        }
        .readTimeout(DEFAULT_READ_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_WRITE_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(DefaultHttpLoggingInterceptor())
}

val client: OkHttpClient
    get() =
        defaultClientBuilder()
            .protocols(immutableListOf(Protocol.HTTP_1_1))
            .build()
