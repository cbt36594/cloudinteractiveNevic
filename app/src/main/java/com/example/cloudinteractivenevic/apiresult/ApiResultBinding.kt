package com.example.cloudinteractivenevic.apiresult

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

    @BindingAdapter("photoUrl")
    @JvmStatic
    fun bindImage(
        image: AppCompatImageView,
        url: String
    ) {
        Glide.with(image.context)
            .load(url)
            .into(image)
    }
}