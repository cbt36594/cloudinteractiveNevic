package com.example.cloudinteractivenevic.extension

import android.graphics.Bitmap
import android.util.LruCache
import timber.log.Timber
import timber.log.Timber.DebugTree


class BitmapCache(maxSize: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8) : LruCache<String, Bitmap>(
    maxSize
) , ImageCache {

    init {
        Timber.plant(DebugTree())
        Timber.tag("TestBit")
    }

    override fun sizeOf(key: String?, value: Bitmap?): Int {
        return if (value == null) 0 else value.rowBytes * value.height / 1024
    }

    override fun getBitmap(url: String?): Bitmap? { //透過 url 檢查有沒有圖在 cache 中，有就回傳。或可不可以新建，可以就回傳。
        Timber.d("TestBit: getlruCache ${get(url)}")
        return get(url)
    }

    override fun putBitmap(url: String?, bitmap: Bitmap?) { //把圖存到 cache 中
        Timber.d("TestBit: putlruCache")
        put(url, bitmap)
    }

    override fun clearBitmap() {
        evictAll()
    }
}
interface ImageCache {
    fun getBitmap(url: String?): Bitmap?
    fun putBitmap(url: String?, bitmap: Bitmap?)
    fun clearBitmap()
}