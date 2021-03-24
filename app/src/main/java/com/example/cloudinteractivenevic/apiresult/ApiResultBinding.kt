package com.example.cloudinteractivenevic.apiresult

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.cloudinteractivenevic.model.Photos


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

        if (url.isNotEmpty()){
            val glideUrl = GlideUrl(url,  LazyHeaders.Builder()
                .addHeader("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                .build())
            Glide.with(image.context)
                .load(glideUrl)
                .centerCrop()
                .placeholder(placeHolder)
                .error(error)
                .listener(object : RequestListener<Drawable> {


                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(image)
        }
        else{
            image.setImageDrawable(placeHolder)
        }

    }
}

