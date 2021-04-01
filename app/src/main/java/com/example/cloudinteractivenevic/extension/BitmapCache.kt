package com.example.cloudinteractivenevic.extension

import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache;
import timber.log.Timber


class BitmapCache {
    private var lruCache: LruCache<String, Bitmap>? = null

    fun BitmapCache() {
//            int maxMemSize = 10*1024*1024;//預計 cache 大小：10M
        val maxMemSize = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 4 //全部記憶體的 1/8
        lruCache = object : LruCache<String, Bitmap>(maxMemSize) {
            //設定預計的 cache 大小
            override fun sizeOf(key: String?, bitmap: Bitmap): Int { //用來計算被 cache 的圖的大小
                return bitmap.byteCount
            }
        }
    }
    fun getBitmap(url: String?): Bitmap? { //透過 url 檢查有沒有圖在 cache 中，有就回傳。或可不可以新建，可以就回傳。
        Log.d("TestBit: ", "getlruCache${lruCache?.get(url)}")
        return lruCache?.get(url)
    }

    fun putBitmap(url: String?, bitmap: Bitmap?) { //把圖存到 cache 中
        if (getBitmap(url) == null) {
            Log.d("TestBit: ", "putlruCache")
            lruCache?.put(url, bitmap);
        } else {
            Timber.d("CacheFail")
        }

    }

    fun clearBitmap(){
        lruCache?.evictAll()
    }
}