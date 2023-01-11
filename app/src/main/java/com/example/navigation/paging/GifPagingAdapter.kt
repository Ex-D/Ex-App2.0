package com.example.navigation.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigation.R
import com.example.navigation.models.dto.Data
import com.example.navigation.viewModels.adapters.GifAdapter

class GifPagingAdapter : PagingDataAdapter<Data, GifPagingAdapter.GifPagingViewHolder>(COMPARATOR) {

    class GifPagingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gif = itemView.findViewById<ImageView>(R.id.tv_Image)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: GifPagingViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            Glide.with(holder.itemView.context)
                .load(item.images.preview_gif.url)
                .into(holder.gif);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifPagingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.gifsdisplaypattern,parent,false)
        return GifPagingAdapter.GifPagingViewHolder(view)
    }
}