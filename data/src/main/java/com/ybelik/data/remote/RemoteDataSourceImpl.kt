package com.ybelik.data.remote

import android.util.Log
import com.ybelik.data.remote.response.ArticleResponse
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: NewsApiService
) : RemoteDataSource {

    val TAG = "NewsRepository"

    override suspend fun loadArticles(topic: String): List<ArticleResponse> { //CAT
        Log.d(TAG, "loadArticles: for topic = $topic")
        return try {
             val list = apiService.loadArticles(topic = topic).articles
            Log.d(TAG, "loadArticles: $list")
            list
        } catch (e: Exception) {
            if (e is CancellationException) {
                Log.e("NewsRepository", "CancellationException")
                throw e
            } else {
                Log.e("NewsRepository", e.stackTraceToString())
                emptyList()
            }
        }
    }
}