package com.example.whatnow.API

enum class Countries(val code: String) {
    ARGENTINA("ar"),
    AUSTRALIA("au"),
    AUSTRIA("at"),
    BELGIUM("be"),
    BRAZIL("br"),
    CANADA("ca"),
    CHINA("cn"),
    EGYPT("eg"),
    GERMANY("de"),
    HUNGARY("hu"),
    ITALY("it"),
    JAPAN("jp"),
    MALAYSIA("my"),
    MOROCCO("ma"),
    NORWAY("no"),
    POLAND("pl"),
    RUSSIA("ru"),
    SAUDI_ARABIA("sa"),
    SINGAPORE("sg"),
    SWITZERLAND("ch"),
    TURKEY("tr"),
    UAE("ae"),
    UNITED_KINGDOM("gb"),
    US("us"),
    None("");
    companion object{
        fun returnAsEnum(toBeReturnedAsEnum: String): Countries {
            for (country in entries) {
                if (country.toString() == toBeReturnedAsEnum) {
                    return country

                }
            }
            return None
        }
    }
}