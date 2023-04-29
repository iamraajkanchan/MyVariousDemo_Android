package com.example.filesystemdemo.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filesystemdemo.databinding.NetworkInfoItemBinding

class NetworkInfoAdapter(private val networkInfoList: List<String>) :
    RecyclerView.Adapter<NetworkInfoAdapter.NetworkInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkInfoViewHolder {
        val binding =
            NetworkInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NetworkInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NetworkInfoViewHolder, position: Int) {
        holder.bind(networkInfoList[position])
    }

    override fun getItemCount(): Int = networkInfoList.size

    class NetworkInfoViewHolder(private val binding: NetworkInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.tvNetworkInfo.text = data
        }
    }

}