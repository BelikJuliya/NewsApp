package com.ybelik.domain.repoository

import com.ybelik.domain.model.Language
import com.ybelik.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<Settings>

    suspend fun updateLanguage(language: Language)

    suspend fun updateInterval(minutes: Int)

    suspend fun updateNotificationsEnabled(isEnabled: Boolean)

    suspend fun updateWifiOnly(isWifiOnly: Boolean)
}