package com.example.filesystemdemo.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.filesystemdemo.databinding.RecyclerUnsplashImageItemBinding
import com.example.filesystemdemo.model.UnsplashImageResponseItem
import com.example.filesystemdemo.workers.DownloadWorker

class UnsplashImageAdapter(
    private val context: Context,
    private val unsplashImages: List<UnsplashImageResponseItem>
) :
    RecyclerView.Adapter<UnsplashImageAdapter.UnsplashImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashImageViewHolder {
        val binding = RecyclerUnsplashImageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UnsplashImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnsplashImageViewHolder, position: Int) {
        val image = unsplashImages[position]
        Glide.with(context).load(image.urls?.regular).into(holder.binding.imgUnsplashImage)
        holder.binding.tvUnsplashBio.text = image.user?.bio
        val workerData = Data.Builder()
            .putString(DownloadWorker.DOWNLOAD_URL_KEY, image.urls?.regular)
            .putString(DownloadWorker.IMAGE_NAME_KEY, image.id)
            .build()
        val workerConstraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
        val workerRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            .setConstraints(workerConstraint)
            .setInputData(workerData)
            .build()
        WorkManager.getInstance(context).enqueue(workerRequest)
    }

    override fun getItemCount(): Int = unsplashImages.size

    class UnsplashImageViewHolder(val binding: RecyclerUnsplashImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}