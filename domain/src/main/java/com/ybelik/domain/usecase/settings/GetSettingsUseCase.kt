package com.ybelik.domain.usecase.settings

import com.ybelik.domain.model.Settings
import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.domain.repoository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class GetSettingsUseCase(
    private val repository: SettingsRepository
) {

    operator fun invoke(): Flow<Settings> {
        return repository.getSettings()
    }
}