package com.ybelik.domain.usecase

import com.ybelik.domain.repoository.NewsRepository

class AddSubscriptionUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(topic: String) {
        repository.addSubscription(topic)
        repository.updateArticlesForTopic(topic)
    }
}