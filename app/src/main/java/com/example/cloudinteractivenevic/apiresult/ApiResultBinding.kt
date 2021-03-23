package com.example.cloudinteractivenevic.apiresult

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.example.cloudinteractivenevic.model.Photos


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
                .into(image)
//        }
//        else{
//            image.setImageDrawable(placeHolder)
//        }

    }
}

