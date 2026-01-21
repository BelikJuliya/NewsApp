package com.ybelik.domain.usecase.subscriptions

import com.ybelik.domain.model.Article
import com.ybelik.domain.repoository.NewsRepository
import kotlinx.coroutines.flow.Flow

class ClearAllArticlesUseCase(
    private val repository: NewsRepository
) {

    operator fun invoke(topics: List<String>): Flow<List<Article>> {
        return repository.getArticlesByTopics(topics)
    }
}