package com.ybelik.news

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.ybelik.data.R
import com.ybelik.domain.NotificationHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.jvm.javaClass

class NotificationHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) : NotificationHelper {

    init {
        createNotification()
    }

    private fun createNotification() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.new_articles),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    override fun showNewArticlesNotification(topics: List<String>) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(com.ybelik.news.R.drawable.ic_breaking_news)
            .setContentTitle(context.getString(R.string.new_articles_title))
            .setContentText(
                context.getString(
                    R.string.topics_updated, topics.size, topics.joinToString(", ")
                )
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {

        const val CHANNEL_ID = "new_articles"
        const val NOTIFICATION_ID = 1
        const val PENDING_INTENT_REQUEST_CODE = 2
    }
}