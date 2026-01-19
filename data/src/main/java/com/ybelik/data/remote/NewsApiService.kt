package com.ybelik.data.remote

import com.ybelik.data.remote.response.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun loadArticles(
        @Query("q") topic: String
    ): List<ArticleResponse>
}