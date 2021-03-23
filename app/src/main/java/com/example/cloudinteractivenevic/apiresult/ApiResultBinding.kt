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
import javax.sql.DataSource


object ApiResultBinding {

    @BindingAdapter("photoList", "clickItem")
    @JvmStatic
    fun bindPhotoList(
        recyclerView: RecyclerView,
        photoList: List<Photos>?,
        click: ApiResultViewModel
    ) {
        if (recyclerView.adapter == null) {
            recyclerView.adapter = ApiResultAdapter(click)
        }
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
        val glideUrl = GlideUrl(url,  LazyHeaders.Builder()
            .addHeader("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
            .build())
        if (url.isNotEmpty()){
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

