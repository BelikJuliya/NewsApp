package com.ybelik.domain.usecase.settings

import com.ybelik.domain.repoository.SettingsRepository

class UpdateNotificationsUseCase(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(isEnabled: Boolean) {
         repository.updateNotificationsEnabled(isEnabled)
    }
}