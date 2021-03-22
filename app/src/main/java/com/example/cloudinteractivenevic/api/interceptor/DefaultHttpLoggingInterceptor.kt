package com.example.cloudinteractivenevic.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    .apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

class DefaultHttpLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val request = builder.build()
        val response = chain.proceed(request)
        return response
    }

}