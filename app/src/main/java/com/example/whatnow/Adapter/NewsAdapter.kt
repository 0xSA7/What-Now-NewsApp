package com.example.whatnow.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.whatnow.API.Articles
import com.example.whatnow.R
import com.example.whatnow.databinding.ArticalListItemBinding

class NewsAdapter(val activity: Context, val newsList: ArrayList<Articles>) :
    Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: ArticalListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ArticalListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    //Single-expression function
    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d("trace", "Article image: ${newsList[position].urlToImage}")
        holder.binding.articalTitle.text = newsList[position].title
        val imageUrl = newsList[position].urlToImage
        Glide
            .with(holder.binding.articalImage.context)
            .load(imageUrl)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articalImage)

        val url = newsList[position].url
        holder.binding.articalContiner.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            activity.startActivity(intent)
        }
        holder.binding.shareBtn.setOnClickListener {
            ShareCompat
                .IntentBuilder(activity)
                .setType("text/plain")
                .setChooserTitle("Share Article")
                .setText(url)
                .startChooser()
        }
    }


}