package com.ybelik.domain.usecase.settings

import com.ybelik.domain.repoository.SettingsRepository

class UpdateWifiOnlyUseCase(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(isWifiOnly: Boolean) {
         repository.updateWifiOnly(isWifiOnly)
    }
}