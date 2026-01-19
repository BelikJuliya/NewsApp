package com.ybelik.data.remote

import android.util.Log
import com.ybelik.data.remote.response.ArticleResponse
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: NewsApiService
) : RemoteDataSource {

    override suspend fun loadArticles(topic: String): List<ArticleResponse> {
        return try {
             apiService.loadArticles(topic = topic)
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            } else {
                Log.e("NewsRepository", e.stackTraceToString())
                emptyList()
            }
        }
    }
}