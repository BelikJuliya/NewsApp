package com.ybelik.news.screen.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ybelik.data.mapper.toInterval
import com.ybelik.data.mapper.toLanguage
import com.ybelik.domain.model.Interval
import com.ybelik.domain.model.Language
import com.ybelik.domain.model.Settings
import com.ybelik.domain.usecase.settings.GetSettingsUseCase
import com.ybelik.domain.usecase.settings.UpdateIntervalUseCase
import com.ybelik.domain.usecase.settings.UpdateLanguageUseCase
import com.ybelik.domain.usecase.settings.UpdateNotificationsUseCase
import com.ybelik.domain.usecase.settings.UpdateWifiOnlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateIntervalUseCase: UpdateIntervalUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateNotificationsUseCase: UpdateNotificationsUseCase,
    private val updateWifiOnlyUseCase: UpdateWifiOnlyUseCase
) : ViewModel() {

    private val defaultSettingsList = listOf(
        SettingsUIModel.MenuSettings(
            name = SelectableSettingsName.LANGUAGE,
            title = "Select language",
            subtitle = "Select language for news search",
            selectedItem = Settings.DEFAULT_LANGUAGE.name,
            items = Language.entries.map { it.name }
        ),
        SettingsUIModel.MenuSettings(
            name = SelectableSettingsName.INTERVAL,
            title = "Update interval",
            subtitle = "How often update news",
            selectedItem = Settings.DEFAULT_INTERVAL.name,
            items = Interval.entries
                .map { it.name }
                .toMutableList()
        ),
        SettingsUIModel.SwitchSettings(
            name = SwitchableSettingsName.NOTIFICATIONS,
            title = "Notifications",
            subtitle = "Show notifications about new articles",
            isChecked = false,

            ),
        SettingsUIModel.SwitchSettings(
            name = SwitchableSettingsName.WIFI,
            title = "Update only via Wi-Fi",
            subtitle = "Save mobile data",
            isChecked = false
        )
    )

    private val _state = MutableStateFlow(SettingsState(defaultSettingsList))
    val state = _state.asStateFlow()

    init {
        observeSettings()
    }

    fun observeSettings() {
        viewModelScope.launch {
            getSettingsUseCase().collect { settings ->
                _state.update { previousState ->
                    Log.d(TAG, "observeSettings: new settings collected $settings")
                    previousState.copy(settingsList = previousState.settingsList.map {
                        it.updateFrom(settings)
                    })
                }
            }
        }
    }

    fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.ChangeInterval -> {
                viewModelScope.launch {
                    Log.d(TAG, "handleIntent: interval updated, post new state with interval ${intent.interval}")
                    updateIntervalUseCase(interval = intent.interval.toInterval())
                }
            }

            is SettingsIntent.ChangeLanguage -> {
                viewModelScope.launch {
                    updateLanguageUseCase(language = intent.language.toLanguage())
                }
            }

            is SettingsIntent.ToggleIsWifiOnly -> {
                viewModelScope.launch {
                    updateWifiOnlyUseCase(isWifiOnly = intent.isWifiOnly)
                }
            }

            is SettingsIntent.ToggleNotificationsEnabled -> {
                viewModelScope.launch {
                    updateNotificationsUseCase(isEnabled = intent.isEnabled)
                }
            }
        }
    }
}