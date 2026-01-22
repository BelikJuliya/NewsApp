package com.ybelik.domain.usecase.subscriptions

import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.domain.repoository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class AddSubscriptionUseCase(
    private val newsRepository: NewsRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(topic: String) {
        newsRepository.addSubscription(topic)
        // Запускаем обновление в отдельной корутине (=параллельно) с тем жеконтектом, то есть во viewModel scope
        CoroutineScope(coroutineContext).launch {
            val settings = settingsRepository.getSettings().first()
            newsRepository.updateArticlesForTopic(topic = topic, language = settings.language)
        }
    }
}