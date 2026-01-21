package com.ybelik.domain.usecase.subscriptions

import com.ybelik.domain.repoository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class AddSubscriptionUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(topic: String) {
        repository.addSubscription(topic)
        // Запускаем обновление в отдельной корутине (=параллельно) с тем жеконтектом, то есть во viewModel scope
        CoroutineScope(coroutineContext).launch {
            repository.updateArticlesForTopic(topic)
        }
    }
}