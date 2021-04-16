package com.example.cloudinteractivenevic.apiresult

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
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

            val result: Deferred<Bitmap?>? = image.lifecycleOwner?.lifecycleScope?.async(Dispatchers.IO) {
                connection.toBitmap()
            }
            image.lifecycleOwner?.lifecycleScope?.launch(Dispatchers.Main) {

                val bitmap = result?.await()

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
    private val View.lifecycleOwner: LifecycleOwner? get() = try {
        val fragment = findFragment<Fragment>()
        fragment.viewLifecycleOwner
    } catch (e: IllegalStateException) {
        when (val activity = context.getActivity()) {
            is ComponentActivity -> activity
            else -> null
        }
    }
    private tailrec fun Context?.getActivity(): Activity? = when (this) {
        is Activity -> this

        else -> {
            val contextWrapper = this as? ContextWrapper
            contextWrapper?.baseContext?.getActivity()
        }
    }
}

