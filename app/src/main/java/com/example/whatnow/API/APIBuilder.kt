package com.example.whatnow.API

import android.util.Log
import com.example.whatnow.BuildConfig
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class APIBuilder {
    companion object {
        //    /v2/everything?q=bitcoin&from=2021-08-01&to=2021-08-01&sortBy=popularity&apiKey=API_KEY
        fun everythingCall(
            query: String,
            domains: String = "",
            excludeDomains: String = "",
            from: String = "",
            to: String = "",
            language: Languages = Languages.None,
            sortBy: SortBy = SortBy.None
        ): String {
            Log.d("APIBuilder", "Query entered to everything call: $query" )
            return baseChunk() + BuildConfig.API_Topics_Everything + addQuery(query) + addDomains(
                domains,
                excludeDomains
            ) + addFrom(from) + addTo(to) + addLanguage(language) + addSortBy(sortBy) + addAPIKey()
        }

        //   /v2/top-headlines?country=us&category=business&sources=bbc-news&q=trump&apiKey=API_KEY
        fun topHeadlinesCall(
            country: Countries = Countries.None,
            category: Categories = Categories.None,
            q: String = ""
        ): String {
            var finalCountry = country
            if (country == Countries.None && category == Categories.None && q.isEmpty()) {
                finalCountry = Countries.US
            }
            return baseChunk() + BuildConfig.API_Topics_Top_Headlines + addCountry(finalCountry) + addCategory(
                category
            ) + addQuery(q) + addAPIKey()
        }

        private fun addCategory(category: Categories): String {
            if (category != Categories.None) {
                return BuildConfig.API_Category + category.code + "&"
            }
            return ""
        }

        private fun addSortBy(sortBy: SortBy = SortBy.PUBLISHED_AT): String {
            if (sortBy != SortBy.None) {
                return BuildConfig.API_SortBy + sortBy.value + "&"
            }
            return ""
        }

        private fun addLanguage(language: Languages): String {
            if (language != Languages.None) {
                return BuildConfig.API_Language + language.code + "&"
            }
            return ""
        }

        private fun addTo(to: String = ""): String {
            return to.takeIf { it.isNotEmpty() }?.let { BuildConfig.API_To + it + "&" } ?: ""
        }

        private fun addFrom(from: String = ""): String {
//            if (from.isNotEmpty()) {
//                return BuildConfig.API_From + from+"/"
//            }
//            return ""
            return from.takeIf { it.isNotEmpty() }?.let { BuildConfig.API_From + it + "&" } ?: ""
        }

        private fun addDomains(Domains: String = "", excludeDomains: String = ""): String {
            return if (Domains.isNotEmpty() && excludeDomains.isNotEmpty()) {
                BuildConfig.API_Domains + Domains + "&" + BuildConfig.API_Exclude_Domains + excludeDomains
            } else if (Domains.isNotEmpty()) {
                BuildConfig.API_Domains + Domains + "&"
            } else if (excludeDomains.isNotEmpty()) {
                BuildConfig.API_Exclude_Domains + excludeDomains + "&"
            } else ""
        }

        private fun addQuery(query: String): String {
//            if (!query.isEmpty()) {
//                buildSearchQuery(query)
//                return BuildConfig.API_q + query
//            }
//            return ""
            return query.takeIf { it.isNotEmpty() }
                ?.let { BuildConfig.API_q + buildSearchQuery(it) + "&" } ?: ""
        }

        private fun addCountry(country: Countries): String {
            return if (country != Countries.None) {
                BuildConfig.API_Country + country.code + "&"
            } else {
                ""
            }
        }

        private fun baseChunk(): String {
            return BuildConfig.API_baseUrl + BuildConfig.API_Version
        }

        private fun buildSearchQuery(q: String): String {
            return URLEncoder.encode(q, StandardCharsets.UTF_8.toString())
        }

        private fun addAPIKey(): String {
            return BuildConfig.API_KEY
        }
    }
}