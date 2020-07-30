package com.image.superimageview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.image.imageview.view.SuperImageView

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.Ia>() {
    private var imageList:MutableList<String> = mutableListOf()
    private var context:Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.Ia {
        context = parent.context
        return Ia(LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview,parent,false))
    }

    class Ia(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val siImg = itemView.findViewById<SuperImageView>(R.id.siImg)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageAdapter.Ia, position: Int) {
        holder.siImg.requestUrlImage(imageList.get(position))
        //Glide.with(context!!).load(imageList.get(position)).into(holder.siImg)
    }

    fun setImageList(imageList:MutableList<String>){
        this.imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}