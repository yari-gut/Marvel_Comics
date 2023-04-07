package com.example.marvel_comics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ComicAdapter(private val comicList: List<String>, private val comicNameList: List<String>, private val comicDes: List<String>) : RecyclerView.Adapter<ComicAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val comicImage: ImageView
        val comicName = itemView.findViewById<TextView>(R.id.comic_name)
        val comicDescription = itemView.findViewById<TextView>(R.id.comic_description)


        init {
            // Find our RecyclerView item's ImageView for future use
            comicImage = itemView.findViewById(R.id.comic_image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comic_item, parent, false)


        return ViewHolder(view)
    }

    override fun getItemCount() = comicList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = comicNameList[position]
        val description = comicDes[position]
        val textViewName = holder.comicName
        textViewName.setText(name)
        val textViewDes = holder.comicDescription
        textViewDes.setText(description)

        Glide.with(holder.itemView)
            .load(comicList[position])
            .centerCrop()
            .into(holder.comicImage)
    }
}