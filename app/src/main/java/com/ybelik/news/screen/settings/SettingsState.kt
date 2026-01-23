package com.ybelik.news.screen.settings

data class SettingsState(
    val settingsList: List<SettingsUIModel>
)

enum class SettingType {
    TOGGLE, SELECTOR
}

sealed class SettingsUIModel(val settingsName: SettingsName) {
    data class MenuSettings(
        val name: SettingsName,
        val title: String,
        val description: String,
        val currentSelection: String,
        val possibleSelections: List<String>,
        val callback: (SettingsName) -> Unit = {
            // TODO yet not implemented
        }
    ): SettingsUIModel(name)
    data class SwitchSettings(
        val name: SettingsName,
        val title: String,
        val description: String,
        val isChecked: Boolean,
        val callback: (SettingsName) -> Unit = {
            // TODO yet not implemented
        }
    ): SettingsUIModel(name)
}

enum class SettingsName {
    LANGUAGE, INTERVAL, WIFI, NOTIFICATIONS
}