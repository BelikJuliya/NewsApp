package com.ybelik.domain.usecase.subscriptions

import com.ybelik.domain.repoository.NewsRepository

class RemoveSubscriptionUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(topic: String) {
        repository.removeSubscription(topic)
    }
}