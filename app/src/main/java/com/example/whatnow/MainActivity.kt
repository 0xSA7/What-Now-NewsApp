package com.example.whatnow

import SearchFragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.whatnow.api.APIBuilder
import com.example.whatnow.api.Countries
import com.example.whatnow.api.DefaultRetrofitFactory
import com.example.whatnow.api.Languages
import com.example.whatnow.api.NewsCallable
import com.example.whatnow.api.SortBy
import com.example.whatnow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SearchFragment.OnSearchListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsManager: NewsManager
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

        val retrofit = DefaultRetrofitFactory().createRetrofit()
        val newsCallable = retrofit.create(NewsCallable::class.java)
        newsManager = NewsManager(this, binding, newsCallable)
        val query = APIBuilder.topHeadlinesCall()
        newsManager.loadNews(query)
        binding.swipeRefresh.setOnRefreshListener { newsManager.loadNews(query) }

        if (intent.getBooleanExtra("openSearchFragment", false)) {
            openSearchFragment()
        }
        val openSearchBtn: ImageButton = findViewById(R.id.search_ib)
        openSearchBtn.setOnClickListener {
            openSearchFragment()
        }

    }

    private fun openSearchFragment() {
        val searchFragment = SearchFragment()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_right,
                R.anim.exit_to_left
            )
            .replace(R.id.fragment_container, searchFragment)
            .addToBackStack(null)
            .commit()
    }
    fun showNoNewsFragment() {
        replaceFragment(NoNewsFragment())
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onSearch(query: String, language: String, fromDate: String, toDate: String) {
        val selectedLanguage = Languages.returnAsEnum(language)
        newsManager.performSearch(query, language = selectedLanguage, from = fromDate, to = toDate)
    }

}