package com.ybelik.news.screen.subscriiption

sealed interface SubscriptionsIntent {

    data class InputTopic(val topic: String) : SubscriptionsIntent

    data object ClickSubscribe: SubscriptionsIntent

    data object RefreshData: SubscriptionsIntent

    data class ToggleTopicSelection(
        val topic: String
    ): SubscriptionsIntent

    data object ClearArticles: SubscriptionsIntent

    data class RemoveSubscription(
        val topic: String
    ):SubscriptionsIntent
}