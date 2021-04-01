package com.example.cloudinteractivenevic.apiresult

import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
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
import timber.log.Timber
import java.net.URL


class ApiResultAdapter(val viewmodel: ApiResultViewModel) :
    ListAdapter<Photos, ViewHolder>(PhotosDiffCallback()) {
    private var lruCache: LruCache<String, Bitmap>? = null
    private val maxMemSize = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 4 //全部記憶體的 1/8

    fun ApiResultAdapter(viewmodel: ApiResultViewModel) {
        lruCache = object : LruCache<String, Bitmap>(maxMemSize) {
            override fun entryRemoved(
                evicted: Boolean,
                key: String?,
                oldValue: Bitmap?,
                newValue: Bitmap?
            ) {
                super.entryRemoved(evicted, key, oldValue, newValue)
                if (evicted && oldValue != null){
                    oldValue.recycle();
                }
            }
            

            //設定預計的 cache 大小
            override fun sizeOf(key: String?, bitmap: Bitmap): Int { //用來計算被 cache 的圖的大小
                return bitmap.byteCount
            }
        }
    }
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
}

class ViewHolder(val binding: ResultItemsBinding) : RecyclerView.ViewHolder(binding.root)

class PhotosDiffCallback : DiffUtil.ItemCallback<Photos>() {
    override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean =
        oldItem == newItem

}
