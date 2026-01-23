package com.ybelik.news.screen.settings

import com.ybelik.domain.model.Interval
import com.ybelik.domain.model.Language

sealed class SettingsIntent(
    val settingsName: SettingsName
) {

    data class ChangeLanguage(
        val name: SettingsName,
        val language: Language
    ) : SettingsIntent(name)

    data class ChangeInterval(
        val name: SettingsName,
        val interval: Interval
    ) : SettingsIntent(name)

    data class ToggleNotificationsEnabled(
        val name: SettingsName,
        val isEnabled: Boolean
    ) : SettingsIntent(name)

    data class ToggleIsWifiOnly(
        val name: SettingsName,
        val isWifiOnly: Boolean
    ) : SettingsIntent(name)
}