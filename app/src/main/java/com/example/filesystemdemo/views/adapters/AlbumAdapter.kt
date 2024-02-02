package com.example.filesystemdemo.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filesystemdemo.databinding.RecyclerAlbumItemBinding
import com.example.filesystemdemo.model.Album

class AlbumAdapter(private val albums: List<Album>) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding =
            RecyclerAlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.binding.tvAlbumTitle.text = albums[position].title
    }

    override fun getItemCount(): Int = albums.size

    class AlbumViewHolder(val binding: RecyclerAlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}