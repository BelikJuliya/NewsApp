package com.ybelik.news.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ybelik.domain.model.Language
import com.ybelik.domain.model.Settings
import com.ybelik.domain.usecase.settings.GetSettingsUseCase
import com.ybelik.domain.usecase.settings.UpdateIntervalUseCase
import com.ybelik.domain.usecase.settings.UpdateLanguageUseCase
import com.ybelik.domain.usecase.settings.UpdateNotificationsUseCase
import com.ybelik.domain.usecase.settings.UpdateWifiOnlyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateIntervalUseCase: UpdateIntervalUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateNotificationsUseCase: UpdateNotificationsUseCase,
    private val updateWifiOnlyUseCase: UpdateWifiOnlyUseCase
) : ViewModel() {

    private val settingsList = listOf<SettingsUIModel>(
        SettingsUIModel.MenuSettings(
            name = SettingsName.LANGUAGE,
            title = "Select language",
            description = "Select language for news search",
            currentSelection = Settings.DEFAULT_LANGUAGE.name,
            possibleSelections = Language.entries.map { it.name }
        ),
        SettingsUIModel.MenuSettings(
            name = SettingsName.INTERVAL,
            title = "Update interval",
            description = "How often update news",
            currentSelection = Settings.DEFAULT_LANGUAGE.name,
            possibleSelections = Language.entries.map { it.name }
        ),
        SettingsUIModel.SwitchSettings(
            name = SettingsName.NOTIFICATIONS,
            title = "Notifications",
            description = "Show notifications about new articles",
            isChecked = false,

            ),
        SettingsUIModel.SwitchSettings(
            name = SettingsName.WIFI,
            title = "Update only via Wi-Fi",
            description = "Save mobile data",
            isChecked = false,

            )
    )

    private val _state = MutableStateFlow(SettingsState(settingsList))
    val state = _state.asStateFlow()

    fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.ChangeInterval -> TODO()
            is SettingsIntent.ChangeLanguage -> TODO()
            is SettingsIntent.ToggleIsWifiOnly -> {
                viewModelScope.launch {
//                    _state.update { previousState ->
//                        updateWifiOnlyUseCase(isWifiOnly = intent.isWifiOnly)
//                        val newSettings = previousState.settingsList.map { settingsItem ->
//                            if (settingsItem.settingsName == intent.settingsName) {
//                                settingsItem.co
//                            }
//                        }
//                    }
                }
            }

            is SettingsIntent.ToggleNotificationsEnabled -> TODO()
        }
    }

    fun processAction(settingsName: SettingsName) {
        when (settingsName) {
            SettingsName.LANGUAGE -> TODO()
            SettingsName.INTERVAL -> TODO()
            SettingsName.WIFI -> TODO()
            SettingsName.NOTIFICATIONS -> TODO()
        }
    }
}