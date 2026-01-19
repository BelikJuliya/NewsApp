package com.ybelik.domain.usecase

import com.ybelik.domain.repoository.NewsRepository

class UpdateSubscribedArticlesUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke() {
        repository.updateArticlesForAllSubscription()
    }
}