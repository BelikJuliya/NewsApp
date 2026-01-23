package com.ybelik.news.screen.settings

import com.ybelik.domain.model.Settings

data class SettingsState(
    val settingsList: List<SettingsUIModel>
)

sealed class SettingsUIModel(open val name: SettingsKey) {

    abstract fun update(value: Any): SettingsUIModel
    data class MenuSettings(
        override val name: SelectableSettingsName,
        val title: String,
        val description: String,
        val currentSelection: String,
        val possibleSelections: List<String>
    ) : SettingsUIModel(name) {

        override fun update(value: Any): SettingsUIModel =
            copy(currentSelection = value as String)
    }

    data class SwitchSettings(
        override val name: SwitchableSettingsName,
        val title: String,
        val description: String,
        val isChecked: Boolean
    ) : SettingsUIModel(name) {

        override fun update(value: Any): SettingsUIModel =
            copy(isChecked = value as Boolean)
    }

    fun updateFrom(settings: Settings): SettingsUIModel =
        when (name.key) {
            SelectableSettingsName.LANGUAGE.key ->
                if (this is MenuSettings && currentSelection != settings.language.name)
                    copy(currentSelection = settings.language.name)
                else this

            SelectableSettingsName.INTERVAL.key ->
                if (this is MenuSettings && currentSelection != settings.interval.name)
                    copy(currentSelection = settings.interval.name)
                else this

            SwitchableSettingsName.NOTIFICATIONS.key ->
                if (this is SwitchSettings && isChecked != settings.isNotificationsEnabled)
                    copy(isChecked = settings.isNotificationsEnabled)
                else this

            SwitchableSettingsName.WIFI.key ->
                if (this is SwitchSettings && isChecked != settings.isWifiOnly)
                    copy(isChecked = settings.isWifiOnly)
                else this

            else -> this
        }

}

enum class SwitchableSettingsName : SettingsKey {
    WIFI, NOTIFICATIONS;

    override val key: String = name
}

enum class SelectableSettingsName : SettingsKey {
    LANGUAGE, INTERVAL;

    override val key: String = name
}

interface SettingsKey {

    val key: String
}