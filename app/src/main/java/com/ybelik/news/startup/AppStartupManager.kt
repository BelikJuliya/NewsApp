package com.ybelik.news.startup

import com.ybelik.domain.usecase.subscriptions.StartRefreshDataUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppStartupManager @Inject constructor(
    private val startRefreshDataUseCase: StartRefreshDataUseCase
) {
    @OptIn(DelicateCoroutinesApi::class)
    fun startRefreshData() {
        GlobalScope.launch {
            startRefreshDataUseCase()
        }
    }
}