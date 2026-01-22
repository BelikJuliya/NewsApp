package com.ybelik.domain.usecase.subscriptions

import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.domain.repoository.SettingsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach

class StartRefreshDataUseCase(
    private val newsRepository: NewsRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke() {
        settingsRepository.getRefreshConfig()
            .distinctUntilChanged()
            .onEach { newsRepository.startBackgroundRefresh(it) }
            .collect()
    }
}