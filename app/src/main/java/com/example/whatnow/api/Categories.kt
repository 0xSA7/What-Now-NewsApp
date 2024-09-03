package com.example.whatnow.api

enum class Categories(val code: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology"),
    None("");
    companion object{
        fun returnThisAsEnum(toBeReturnedAsEnum: String): Categories {
            for (category in entries) {
                if (category.toString() == toBeReturnedAsEnum) {
                    return category

                }
            }
            return None
        }
    }
}

