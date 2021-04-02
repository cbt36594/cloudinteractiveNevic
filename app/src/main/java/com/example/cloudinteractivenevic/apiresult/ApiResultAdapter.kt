package com.example.cloudinteractivenevic.apiresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudinteractivenevic.databinding.ResultItemsBinding
import com.example.cloudinteractivenevic.model.Photos
import kotlinx.coroutines.*


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
            executePendingBindings()
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
