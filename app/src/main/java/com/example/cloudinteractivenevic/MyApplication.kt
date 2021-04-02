package com.example.cloudinteractivenevic

import android.app.Application
import timber.log.Timber

class MyApplication: Application() {

    private var mInstance: MyApplication? = null

    @Synchronized
    fun getInstance(): MyApplication? {
        return mInstance
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        Timber.plant(Timber.DebugTree())
    }
}