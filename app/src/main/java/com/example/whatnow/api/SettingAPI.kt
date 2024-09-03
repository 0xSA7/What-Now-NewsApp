package com.example.whatnow.api

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whatnow.MainActivity
import com.example.whatnow.R
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
            val query = APIBuilder.topHeadlinesCall(
                country = Countries.returnThisAsEnum(
                    countrySpinner.selectedItem.toString().uppercase()
                ),
                category = Categories.returnThisAsEnum(
                    categoriesSpinner.selectedItem.toString().uppercase()
                ),
                q = topics
            )
            Log.d("SettingAPI", "Query: $query")
            startActivity(Intent(this, MainActivity::class.java).putExtra("query", query))
        }
    }
}