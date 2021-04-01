package com.example.cloudinteractivenevic.apiresult

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudinteractivenevic.databinding.ResultItemsBinding
import com.example.cloudinteractivenevic.extension.BitmapCache
import com.example.cloudinteractivenevic.extension.toBitmap
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.*
import okhttp3.internal.concurrent.Task
import java.net.URL


class ApiResultAdapter(val viewmodel: ApiResultViewModel) :
    ListAdapter<Photos, ViewHolder>(PhotosDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ResultItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            this.viewModel = this@ApiResultAdapter.viewmodel
            this.photos = getItem(position)
//            this@ApiResultAdapter.viewmodel.setUrl(getItem(position).thumbnailUrl)
            loadBitmaps(holder.binding.imgItemOne, getItem(position).thumbnailUrl);
            executePendingBindings()
        }
    }
    private fun loadBitmaps(imageView: ImageView?, url: String) {
        val urlImage = URL(url)
        val result: Deferred<Bitmap?> = GlobalScope.async(Dispatchers.IO) {
            urlImage.toBitmap()
        }
        GlobalScope.launch {
            val bitmapCache = BitmapCache()
            val bitmap = result.await()
            bitmapCache.putBitmap(url, bitmap)
            if (bitmapCache.getBitmap(url) == null) {
                bitmapCache.putBitmap(url, bitmap)
            } else {
                imageView?.setImageBitmap(bitmap)
            }
        }
    }
}

class ViewHolder(val binding: ResultItemsBinding) : RecyclerView.ViewHolder(binding.root)

class PhotosDiffCallback : DiffUtil.ItemCallback<Photos>() {
    override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean =
        oldItem == newItem

}