package com.ybelik.domain.usecase.settings

import com.ybelik.domain.model.Interval
import com.ybelik.domain.model.Language
import com.ybelik.domain.model.Settings
import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.domain.repoository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class UpdateIntervalUseCase(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(interval: Interval) {
         repository.updateInterval(minutes = interval.minutes)
    }
}