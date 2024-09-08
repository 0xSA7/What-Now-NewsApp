package com.example.whatnow

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    private lateinit var modeSwitch: SwitchCompat
    private var nightMode:Boolean=false
    private var editor: SharedPreferences.Editor?=null
    private var  sharedPreferences:SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val switchLanguage: SwitchCompat = findViewById(R.id.switch_language)
        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val savedLanguage = sharedPref.getString("language", "en") ?: "en"

        // Set switch state based on current locale
        switchLanguage.isChecked = savedLanguage == "ar"

        switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            val language = if (isChecked) "ar" else "en"
            val editor = sharedPref.edit()
            editor.putString("language", language)
            editor.apply()
            recreate()
        }
        modeSwitch=findViewById(R.id.switch_theme)
        sharedPreferences=getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode=sharedPreferences?.getBoolean("night",false)!!

        if (nightMode){
            modeSwitch.isChecked=true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        modeSwitch.setOnCheckedChangeListener{compoundButton,state->
            if (nightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor=sharedPreferences?.edit()
                editor?.putBoolean("night",false)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor=sharedPreferences?.edit()
                editor?.putBoolean("night",true)


            }
            editor?.apply()

            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)


            supportActionBar?.title = "Setting"


            supportActionBar?.setDisplayHomeAsUpEnabled(true)


            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }



        }

    }
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        // Save language preference
        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("language", languageCode)
            apply()
        }

        // Restart the activity to apply changes
        val intent = intent
        finish()
        startActivity(intent)
    }
}