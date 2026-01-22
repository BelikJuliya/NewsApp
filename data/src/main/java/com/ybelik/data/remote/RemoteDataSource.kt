package com.ybelik.data.remote

import com.ybelik.data.remote.response.ArticleResponse
import com.ybelik.domain.model.Language

interface RemoteDataSource {

    suspend fun loadArticles(topic: String, language: Language): List<ArticleResponse>
}