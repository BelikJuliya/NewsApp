package com.ybelik.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ybelik.data.mapper.toInterval
import com.ybelik.data.mapper.toRefreshConfig
import com.ybelik.domain.model.Interval
import com.ybelik.domain.model.Language
import com.ybelik.domain.model.RefreshConfig
import com.ybelik.domain.model.Settings
import com.ybelik.domain.repoository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val languageKey = stringPreferencesKey("language")
    private val intervalKey = intPreferencesKey("interval")
    private val notificationsKey = booleanPreferencesKey("notifications")
    private val wifiOnlyKey = booleanPreferencesKey("wifi")

    override fun getSettings(): Flow<Settings> {
        return context.dataStore.data.map { pref ->
            val language: String = pref[languageKey] ?: Settings.DEFAULT_LANGUAGE.name
            val interval: Interval = pref[intervalKey]?.toInterval() ?: Settings.DEFAULT_INTERVAL
            val notificationsEnabled: Boolean = pref[notificationsKey] ?: Settings.DEFAULT_NOTIFICATIONS_ENABLED
            val wifiOnly: Boolean = pref[wifiOnlyKey] ?: Settings.DEFAULT_WIFI_ONLY
            Settings(
                language = Language.valueOf(language),
                interval = interval,
                isNotificationsEnabled = notificationsEnabled,
                isWifiOnly = wifiOnly
            )
        }
    }

    override fun getRefreshConfig(): Flow<RefreshConfig> {
        return getSettings().map { it.toRefreshConfig() }
    }

    override suspend fun updateLanguage(language: Language) {
        context.dataStore.edit { preferences ->
            preferences[languageKey] = language.name
        }
    }

    override suspend fun updateInterval(minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[intervalKey] = minutes
        }
    }

    override suspend fun updateNotificationsEnabled(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[notificationsKey] = isEnabled
        }
    }

    override suspend fun updateWifiOnly(isWifiOnly: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[wifiOnlyKey] = isWifiOnly
        }
    }
}