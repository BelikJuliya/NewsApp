package com.ybelik.data.background

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ybelik.domain.NotificationHelper
import com.ybelik.domain.usecase.settings.GetSettingsUseCase
import com.ybelik.domain.usecase.subscriptions.UpdateSubscribedArticlesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val updateSubscribedArticlesUseCase: UpdateSubscribedArticlesUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val notificationsHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        Log.d("RefreshDataWorker", "doWork: Start")
        val updatedTopics = updateSubscribedArticlesUseCase()
        Log.d("RefreshDataWorker", "doWork: Finish")
        val settings = getSettingsUseCase().first()
        if (settings.isNotificationsEnabled) {
            notificationsHelper.showNewArticlesNotification(updatedTopics)
        }
        return Result.success()
    }
}