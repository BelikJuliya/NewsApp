package com.ybelik.news.screen.settings

sealed class SettingsIntent(
    val settingsName: SettingsKey
) {

    data class ChangeLanguage(
        val name: SettingsKey,
        val language: String
    ) : SettingsIntent(name)

    data class ChangeInterval(
        val name: SettingsKey,
        val interval: String
    ) : SettingsIntent(name)

    data class ToggleNotificationsEnabled(
        val name: SettingsKey,
        val isEnabled: Boolean
    ) : SettingsIntent(name)

    data class ToggleIsWifiOnly(
        val name: SettingsKey,
        val isWifiOnly: Boolean
    ) : SettingsIntent(name)
}