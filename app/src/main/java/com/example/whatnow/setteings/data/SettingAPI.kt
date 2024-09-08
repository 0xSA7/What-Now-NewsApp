package com.example.whatnow.setteings.data

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whatnow.BuildConfig
import com.example.whatnow.core.ui.MainActivity
import com.example.whatnow.R
import com.example.whatnow.core.api.APIBuilder
import com.example.whatnow.core.data.Categories
import com.example.whatnow.core.data.Countries
import com.example.whatnow.databinding.ActivitySettingApiBinding

class SettingAPI : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivitySettingApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val countrySpinner: Spinner = binding.countrySpinner
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            countrySpinner.adapter = adapter
        }
        val categoriesSpinner: Spinner = binding.categoriesSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            categoriesSpinner.adapter = adapter
        }

        binding.submit.setOnClickListener {
            val topics = binding.topicEt.text.toString()
                .split(" ")
                .filter { it.isNotEmpty() }
                .joinToString("+") { it.replace(Regex("[^A-Za-z0-9]"), "") }

            val queryUrl = APIBuilder.Builder(BuildConfig.API_Topics_Top_Headlines)
                .setCountry(
                    Countries.returnAsEnum(
                        countrySpinner.selectedItem.toString().uppercase()
                    )
                )
                .setCategory(
                    Categories.returnAsEnum(
                        categoriesSpinner.selectedItem.toString().uppercase()
                    )
                )
                .setQuery(topics)
                .build()
                .buildUrl()
            val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("query", queryUrl)
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java).putExtra("query", queryUrl))
        }
    }
}