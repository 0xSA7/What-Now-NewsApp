package com.example.whatnow.API


enum class Languages(val code: String) {
    Arabic("ar"),
    German("de"),
    English("en"),
    Spanish("es"),
    French("fr"),
    Italian("it"),
    Dutch("nl"),
    Norwegian("no"),
    Portuguese("pt"),
    Russian("ru"),
    Swedish("sv"),
    Urdu("ud"),
    Chinese("zh"),
    None("");

    companion object {
            fun returnAsEnum(toBeReturnedAsEnum: String): Languages {
                for (Language in Languages.entries) {
                    if (Language.toString() == toBeReturnedAsEnum) {
                        return Language

                    }
                }
                return Languages.None
            }
    }
}