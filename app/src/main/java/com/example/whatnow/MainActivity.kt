package com.example.whatnow

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.whatnow.api.APIBuilder
import com.example.whatnow.api.Categories
import com.example.whatnow.api.Countries
import com.example.whatnow.api.Languages
import com.example.whatnow.api.SortBy
import com.example.whatnow.databinding.ActivityMainBinding
import com.google.android.material.search.SearchBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        startActivity(Intent(this, SettingAPI::class.java))
        val searchBar: SearchBar = binding.searchBar
        searchBar.setOnClickListener {
            // Handle the click event
            performSearch("volkswagen", from = "2021-09-01", to = "2021-09-30")
        }
//        val query = intent.getStringExtra("query") ?: ""
        val query = APIBuilder.topHeadlinesCall(Countries.USA)
        loadNews(query)
        binding.swipeRefresh.setOnRefreshListener { loadNews(query) }
    }

    private fun performSearch(
        query: String,
        domains: String = "",
        excludeDomains: String = "",
        from: String = "",
        to: String = "",
        language: Languages = Languages.None,
        sortBy: SortBy = SortBy.None
    ) {
        val queryUrl =
            APIBuilder.everythingCall(query, domains, excludeDomains, from, to, language, sortBy)
        loadNews(queryUrl)
    }

    private fun loadNews(queryUrl: String) {
        val baseUrl = BuildConfig.API_baseUrl
        val retrofit =
            Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .build()
        val newsCallable = retrofit.create(NewsCallable::class.java)
        Log.d("Query", "Query URL: $queryUrl")
        val news = newsCallable.getNews(queryUrl)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    if (news.totalResults == 0) {
                        // when no news found
                        Log.d("MainActivity", "No news found")
                        setContentView(R.layout.fragment_no_news)
                    } else {
                        val adapter = news.articles
//                        adapter.removeAll {
//                            it.title == "[Removed]" || it.urlToImage == null || it.url == null
//                        }
                        showNews(adapter)
                    }
                } else {
                    Log.d("MainActivity", "News response is null")
                }
                binding.progressBar.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("MainActivity", "Error: ${t.message}")
                binding.progressBar.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showNews(articles: ArrayList<Articles>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}