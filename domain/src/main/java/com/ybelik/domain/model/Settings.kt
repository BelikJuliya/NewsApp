package com.ybelik.domain.model

data class Settings(
    val language: Language,
    val interval: Interval,
    val isNotificationsEnabled: Boolean,
    val isWifiOnly: Boolean
) {

    companion object {

        val DEFAULT_LANGUAGE = Language.RUSSIAN
        val DEFAULT_INTERVAL = Interval.MIN_15
        const val DEFAULT_NOTIFICATIONS_ENABLED = false
        const val DEFAULT_WIFI_ONLY = false
    }
}

enum class Language(val queryParamName: String) {
    RUSSIAN("ru"),
    ENGLISH("en"),
    FRENCH("fr"),
    GERMAN("de")
}

enum class Interval(val minutes: Int, readableName: String) {
    MIN_15(15, readableName = "15 minute"),
    MIN_30(30, readableName = "30 minute"),
    HOUR_1(60, readableName = "1 hour"),
    HOUR_2(120, readableName = "2 hours"),
    HOUR_4(240, readableName = "4 hours"),
    HOUR_8(480, readableName = "8 hours"),
    HOUR_24(1440, readableName = "24 hours"),
}
