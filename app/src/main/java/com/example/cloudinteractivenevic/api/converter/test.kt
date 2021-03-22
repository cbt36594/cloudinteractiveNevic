package com.example.cloudinteractivenevic.api.converter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

val defaultGsonConverterFactory: Converter.Factory by lazy {
    GsonConverterFactory.create(defaultGson)
}

val defaultGson: Gson by lazy {
    GsonBuilder()
//        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .create()
}