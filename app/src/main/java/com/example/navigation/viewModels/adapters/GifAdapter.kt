package com.example.navigation.viewModels.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigation.R
import com.example.navigation.models.dto.Data

class GifAdapter:RecyclerView.Adapter<GifAdapter.GifViewHolder>() {
    private val items = mutableListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.gifsdisplaypattern,parent,false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val currentItem = items[position]
        Glide.with(holder.itemView.context).load(currentItem.embed_url).into(holder.gif)
        Glide.with(holder.itemView.context).load(currentItem.user.avatar_url).into(holder.UserImage)
    }

    override fun getItemCount(): Int {
        Log.d("Rohit", items.size.toString())
        return items.size
    }
    fun initData(itemsList: List<Data>){
        this.items.clear()
        this.items.addAll(itemsList)
        notifyDataSetChanged()
    }

    class GifViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val gif = itemView.findViewById<ImageView>(R.id.tv_Image)
        val UserImage =itemView.findViewById<ImageView>(R.id.usr_img)
    }

}