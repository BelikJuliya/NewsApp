package com.ybelik.domain.usecase.subscriptions

import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.domain.repoository.SettingsRepository
import kotlinx.coroutines.flow.first

class UpdateSubscribedArticlesUseCase(
    private val newsRepository: NewsRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): List<String> {
        val settings = settingsRepository.getSettings().first()
        return newsRepository.updateArticlesForAllSubscription(language = settings.language)
    }
}