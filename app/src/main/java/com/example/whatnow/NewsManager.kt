package com.example.whatnow

import android.content.Context
import android.util.Log
import androidx.core.view.isVisible
import com.example.whatnow.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager(
    private val context: Context,
    private val binding: ActivityMainBinding,
    private val newsCallable: NewsCallable
) {

    fun performSearch(
        query: String,
        from: String = "",
        to: String = "",
        language: Languages = Languages.None,
        sortBy: SortBy = SortBy.None
    ) {
        val queryUrl = APIBuilder.Builder(BuildConfig.API_Topics_Everything)
            .setQuery(query)
            .setFromDate(from)
            .setToDate(to)
            .setLanguage(language)
            .setSortBy(sortBy)
            .build()
            .buildUrl()
        loadNews(queryUrl)
    }

    fun loadNews(queryUrl: String) {
        Log.d("Call", "API Call: $queryUrl")
        val news = newsCallable.getNews(queryUrl)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    if (news.totalResults == 0) {
                        Log.d("NewsManager", "No news found")
                        (context as MainActivity).showNoNewsFragment()
                    } else {
                        val adapter = news.articles
                        showNews(adapter)
                    }
                } else {
                    Log.d("NewsManager", "News response is null")
                    (context as MainActivity).showNoNewsFragment()
                }
                binding.progressBar.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("NewsManager", "Error: ${t.message}")
                binding.progressBar.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }
        })
    }


    private fun showNews(articles: ArrayList<Articles>) {
        val adapter = NewsAdapter(context, articles)
        binding.newsList.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}