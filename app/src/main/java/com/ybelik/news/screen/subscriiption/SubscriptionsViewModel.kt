package com.ybelik.news.screen.subscriiption

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ybelik.domain.usecase.subscriptions.AddSubscriptionUseCase
import com.ybelik.domain.usecase.subscriptions.ClearAllArticlesUseCase
import com.ybelik.domain.usecase.subscriptions.GetAllSubscriptionUseCase
import com.ybelik.domain.usecase.subscriptions.GetArticlesByTopicUseCase
import com.ybelik.domain.usecase.subscriptions.RemoveSubscriptionUseCase
import com.ybelik.domain.usecase.subscriptions.UpdateSubscribedArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val clearAllArticlesUseCase: ClearAllArticlesUseCase,
    private val getAllSubscriptionUseCase: GetAllSubscriptionUseCase,
    private val getArticlesByTopicUseCase: GetArticlesByTopicUseCase,
    private val removeSubscriptionUseCase: RemoveSubscriptionUseCase,
    private val updateSubscribedArticlesUseCase: UpdateSubscribedArticlesUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SubscriptionState())
    val state = _state.asStateFlow()

    init {
        observeSubscriptions()
        observeSelectedTopics()
    }

    private fun observeSubscriptions() {
        getAllSubscriptionUseCase().onEach { subscriptions ->
            _state.update { previousState ->
                val updatedTopics = subscriptions.associateWith { topic ->
                    previousState.subscriptions[topic] ?: true
                }
                previousState.copy(subscriptions = updatedTopics)
            }
        }.launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSelectedTopics() {
        _state
            .map { it.selectedTopics }
            .distinctUntilChanged()
            .flatMapLatest { topicsList ->
                getArticlesByTopicUseCase(topics = topicsList)
            }
            .onEach { newArticles ->
                _state.update { previousState ->
                    previousState.copy(articles = newArticles)
                }
            }
            .launchIn(viewModelScope)
    }

    fun handleIntent(intent: SubscriptionsIntent) {
        when(intent) {
            SubscriptionsIntent.ClearArticles -> viewModelScope.launch {
                val selectedTopics = _state.value.selectedTopics
                clearAllArticlesUseCase(selectedTopics)
            }
            SubscriptionsIntent.ClickSubscribe -> viewModelScope.launch {
                _state.update { previousState ->
                    val topic = previousState.query.trim()
                    addSubscriptionUseCase(topic)
                    previousState.copy(query = "")
                }
            }
            is SubscriptionsIntent.InputTopic -> viewModelScope.launch {
                _state.update { previousState ->
                    previousState.copy(query = intent.topic)
                }
            }
            SubscriptionsIntent.RefreshData ->  viewModelScope.launch {
               updateSubscribedArticlesUseCase()
            }
            is SubscriptionsIntent.RemoveSubscription -> viewModelScope.launch {
                removeSubscriptionUseCase(intent.topic)
            }
            is SubscriptionsIntent.ToggleTopicSelection -> _state.update { previousState ->
                val newSubscriptions = previousState.subscriptions.toMutableMap()
                val isSelected = newSubscriptions[intent.topic] ?: false
                newSubscriptions[intent.topic] = !isSelected
                previousState.copy(subscriptions = newSubscriptions)
            }
        }
    }
}