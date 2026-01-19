package com.ybelik.data.remote

import com.ybelik.data.remote.response.ArticleResponse

interface RemoteDataSource {

    suspend fun loadArticles(topic: String): List<ArticleResponse>
}