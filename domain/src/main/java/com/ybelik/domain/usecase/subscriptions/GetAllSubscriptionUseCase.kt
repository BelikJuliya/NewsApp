package com.ybelik.domain.usecase.subscriptions

import com.ybelik.domain.repoository.NewsRepository

class GetAllSubscriptionUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke() = repository.getAllSubscriptions()
}