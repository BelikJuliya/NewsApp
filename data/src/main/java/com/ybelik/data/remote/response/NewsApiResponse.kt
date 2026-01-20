package com.ybelik.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsApiResponse(
    @SerialName("status")
    val status: String = "",
    @SerialName("totalResults")
    val totalResults: Int = 0,
    @SerialName("articles")
    val articles: List<ArticleResponse> = emptyList()
)
