//package com.example.whatnow.api
//
//import android.util.Log
//import com.example.whatnow.BuildConfig
//import java.net.URLEncoder
//import java.nio.charset.StandardCharsets
//
//class APIBuilder {
//    companion object {
//        //    /v2/everything?q=bitcoin&from=2021-08-01&to=2021-08-01&sortBy=popularity&apiKey=API_KEY
//        fun everythingCall(
//            query: String,
//            domains: String = "",
//            excludeDomains: String = "",
//            from: String = "",
//            to: String = "",
//            language: Languages = Languages.None,
//            sortBy: SortBy = SortBy.None
//        ): String {
//            Log.d("APIBuilder", "Query entered to everything call: $query" )
//            return baseChunk() + BuildConfig.API_Topics_Everything + addQuery(query) + addDomains(
//                domains,
//                excludeDomains
//            ) + addFrom(from) + addTo(to) + addLanguage(language) + addSortBy(sortBy) + addAPIKey()
//        }
//
//        //   /v2/top-headlines?country=us&category=business&sources=bbc-news&q=trump&apiKey=API_KEY
//        fun topHeadlinesCall(
//            country: Countries = Countries.None,
//            category: Categories = Categories.None,
//            q: String = ""
//        ): String {
//            var finalCountry = country
//            if (country == Countries.None && category == Categories.None && q.isEmpty()) {
//                finalCountry = Countries.US
//            }
//            return baseChunk() + BuildConfig.API_Topics_Top_Headlines + addCountry(finalCountry) + addCategory(
//                category
//            ) + addQuery(q) + addAPIKey()
//        }
//
//        private fun addCategory(category: Categories): String {
//            if (category != Categories.None) {
//                return BuildConfig.API_Category + category.code + "&"
//            }
//            return ""
//        }
//
//        private fun addSortBy(sortBy: SortBy = SortBy.PUBLISHED_AT): String {
//            if (sortBy != SortBy.None) {
//                return BuildConfig.API_SortBy + sortBy.value + "&"
//            }
//            return ""
//        }
//
//        private fun addLanguage(language: Languages): String {
//            if (language != Languages.None) {
//                return BuildConfig.API_Language + language.code + "&"
//            }
//            return ""
//        }
//
//        private fun addTo(to: String = ""): String {
//            return to.takeIf { it.isNotEmpty() }?.let { BuildConfig.API_To + it + "&" } ?: ""
//        }
//
//        private fun addFrom(from: String = ""): String {
////            if (from.isNotEmpty()) {
////                return BuildConfig.API_From + from+"/"
////            }
////            return ""
//            return from.takeIf { it.isNotEmpty() }?.let { BuildConfig.API_From + it + "&" } ?: ""
//        }
//
//        private fun addDomains(Domains: String = "", excludeDomains: String = ""): String {
//            return if (Domains.isNotEmpty() && excludeDomains.isNotEmpty()) {
//                BuildConfig.API_Domains + Domains + "&" + BuildConfig.API_Exclude_Domains + excludeDomains
//            } else if (Domains.isNotEmpty()) {
//                BuildConfig.API_Domains + Domains + "&"
//            } else if (excludeDomains.isNotEmpty()) {
//                BuildConfig.API_Exclude_Domains + excludeDomains + "&"
//            } else ""
//        }
//
//        private fun addQuery(query: String): String {
////            if (!query.isEmpty()) {
////                buildSearchQuery(query)
////                return BuildConfig.API_q + query
////            }
////            return ""
//            return query.takeIf { it.isNotEmpty() }
//                ?.let { BuildConfig.API_q + buildSearchQuery(it) + "&" } ?: ""
//        }
//
//        private fun addCountry(country: Countries): String {
//            return if (country != Countries.None) {
//                BuildConfig.API_Country + country.code + "&"
//            } else {
//                ""
//            }
//        }
//
//        private fun baseChunk(): String {
//            return BuildConfig.API_baseUrl + BuildConfig.API_Version
//        }
//
//        private fun buildSearchQuery(q: String): String {
//            return URLEncoder.encode(q, StandardCharsets.UTF_8.toString())
//        }
//
//        private fun addAPIKey(): String {
//            return BuildConfig.API_KEY
//        }
//    }
//}
package com.example.whatnow

import com.example.whatnow.API.Categories
import com.example.whatnow.API.Countries
import com.example.whatnow.API.Languages
import com.example.whatnow.API.SortBy
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class APIBuilder private constructor(
    private val endpoint: String,
    private val query: String?,
    private val fromDate: String?,
    private val toDate: String?,
    private val language: Languages?,
    private val sortBy: SortBy?,
    private val country: Countries?,
    private val category: Categories?
) {

    class Builder(private val endpoint: String) {
        private var query: String? = null
        private var fromDate: String? = null
        private var toDate: String? = null
        private var language: Languages? = null
        private var sortBy: SortBy? = null
        private var country: Countries? = null
        private var category: Categories? = null

        fun setQuery(query: String) = apply { this.query = query }
        fun setFromDate(fromDate: String) = apply { this.fromDate = fromDate }
        fun setToDate(toDate: String) = apply { this.toDate = toDate }
        fun setLanguage(language: Languages) = apply { this.language = language }
        fun setSortBy(sortBy: SortBy) = apply { this.sortBy = sortBy }
        fun setCountry(country: Countries) = apply { this.country = country }
        fun setCategory(category: Categories) = apply { this.category = category }

        fun build(): APIBuilder {
            return APIBuilder(
                endpoint,
                query,
                fromDate,
                toDate,
                language,
                sortBy,
                country,
                category
            )
        }
    }

    fun buildUrl(): String {
        return StringBuilder().apply {
            append(baseChunk())
            append(endpoint)
            query?.let { append(addQuery(it)) }
            fromDate?.let { append(addFrom(it)) }
            toDate?.let { append(addTo(it)) }
            language?.let { append(addLanguage(it)) }
            sortBy?.let { append(addSortBy(it)) }
            country?.let { append(addCountry(it)) }
            category?.let { append(addCategory(it)) }
            append(addAPIKey())
        }.toString()
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
        return from.takeIf { it.isNotEmpty() }?.let { BuildConfig.API_From + it + "&" } ?: ""
    }

    private fun addQuery(query: String): String {
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