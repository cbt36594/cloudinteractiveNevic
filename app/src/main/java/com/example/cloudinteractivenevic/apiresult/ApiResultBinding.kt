package com.example.cloudinteractivenevic.apiresult

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudinteractivenevic.extension.BitmapCache
import com.example.cloudinteractivenevic.extension.toBitmap
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.*
import java.net.URL
import java.net.URLConnection


object ApiResultBinding {
    val bitmapCache = BitmapCache()
    @BindingAdapter("photoList")
    @JvmStatic
    fun bindPhotoList(
        recyclerView: RecyclerView,
        photoList: List<Photos>?
    ) {
        (recyclerView.adapter as ApiResultAdapter).submitList(photoList)
    }

    @BindingAdapter("photoUrl", "placeholder", "error")
    @JvmStatic
    fun bindImage(
        image: AppCompatImageView,
        url: String,
        placeHolder: Drawable,
        error: Drawable
    ) {
        val connection: URLConnection = URL(url).openConnection()
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")

        if(bitmapCache.getBitmap(url) == null) {

            val result: Deferred<Bitmap?> = GlobalScope.async(Dispatchers.IO) {
                connection.toBitmap()
            }
            GlobalScope.launch(Dispatchers.Main) {

                val bitmap = result.await()

                if (bitmap != null) {
                    bitmapCache.putBitmap(url, bitmap)
                    image.setImageBitmap(bitmapCache.getBitmap(url))
                } else {
                    image.setImageDrawable(error)
                }
            }
        } else {
            image.setImageBitmap(bitmapCache.getBitmap(url))
        }


    }
}

