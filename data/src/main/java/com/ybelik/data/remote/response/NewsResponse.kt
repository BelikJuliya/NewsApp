package com.ybelik.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("articles")
    val articleResponses: List<ArticleResponse> = listOf()
)