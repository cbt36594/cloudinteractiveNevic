package com.example.cloudinteractivenevic.apiresult

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.cloudinteractivenevic.model.Photos
import javax.sql.DataSource


object ApiResultBinding {

    @BindingAdapter("photoList")
    @JvmStatic
    fun bindPhotoList(
        recyclerView: RecyclerView,
        photoList: List<Photos>?
    ) {
        if (recyclerView.adapter == null) {
            recyclerView.adapter = ApiResultAdapter()
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
//        if (url.isNotEmpty()){
            Glide.with(image.context)
                .load(url)
                .centerCrop()
                .placeholder(placeHolder)
                .error(error)
                .listener(object : RequestListener<Drawable> {


                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        var test = isFirstResource
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        var test = e
                        return false
                    }
                })
                .into(image)
//        }
//        else{
//            image.setImageDrawable(placeHolder)
//        }

    }
}

