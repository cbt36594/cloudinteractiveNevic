package com.example.cloudinteractivenevic.apiresult

import android.R.drawable
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.cloudinteractivenevic.extension.*
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.*
import timber.log.Timber
import java.net.URL
import kotlin.coroutines.CoroutineContext


object ApiResultBinding {

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
//        val urlImage: URL = URL(url)
//        val result: Deferred<Bitmap?> = GlobalScope.async(Dispatchers.IO) {
//            urlImage.toBitmap()
//        }

//        GlobalScope.launch {
            val bitmapCache = BitmapCache()
            val bitmap: Bitmap? = bitmapCache.getBitmap(url)
//            val temp = result.await()
//            if (bitmap == null) {
//                bitmapCache.putBitmap(url, temp)
//                Log.d("TestBit: ","await1 ${bitmap}")
//                Log.d("TestBit: ","await2")
//            }
//            Log.d("TestBit: ", "${ bitmapCache.getBitmap(url)}")
//            temp?.apply {
//                val savedUri : Uri? = saveToInternalStorage(image.context)
//                image.setImageURI(savedUri)
//            }
            if (bitmap != null) {
//                Timber.d("TestBit: url")
//                image.setImageBitmap(bitmap)


//            val glideUrl = GlideUrl(
//                url, LazyHeaders.Builder()
//                    .addHeader(
//                        "User-Agent",
//                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36"
//                    )
//                    .build()
//            )
//            Glide.with(image.context)
//                .load(glideUrl)
//                .centerCrop()
//                .placeholder(placeHolder)
//                .error(error)
//                .listener(object : RequestListener<Drawable> {
//
//
//                    override fun onResourceReady(
//                        resource: Drawable?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        dataSource: com.bumptech.glide.load.DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        image.setImageDrawable(resource)
//
//                        return true
//                    }
//
//
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        return false
//                    }
//                })
//                .into(image)
            } else {
                Log.d("TestBit: "," error")
//                    image.setImageDrawable(placeHolder)
//                    val bitmapDraw = vectorDrawableToBitmap(placeHolder)
//                    bitmapCache.putBitmap(url, bitmapDraw)
            }
//            image.setImageDrawable(placeHolder)
//        }


    }
}

