package com.ybelik.news.screen.subscriiption

import com.ybelik.domain.model.Article

data class SubscriptionState(
    val query: String = "",
    val subscriptions: Map<String, Boolean> = emptyMap(),
    val articles: List<Article> = emptyList()
) {
    val selectedTopics: List<String>
        get() = subscriptions.filter { it.value }.map { it.key }

    val subscribeBtnEnabled: Boolean
        get() = query.isNotBlank()
}
