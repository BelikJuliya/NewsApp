package com.ybelik.news

import android.icu.text.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

object DateFormatter {

    private val millisInHour = TimeUnit.HOURS.toMillis(1)
    private val millisInDay = TimeUnit.DAYS.toMillis(1)
    private val formatter = SimpleDateFormat.getDateInstance(DateFormat.SHORT)

    @Composable
    fun formatDateToString(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        return when {

            // Прошло меньше часа
            diff < millisInHour -> stringResource(R.string.just_now)

            // Прошло меньше суток
            diff < millisInDay -> {
                val hours = TimeUnit.MICROSECONDS.toHours(diff)
                stringResource(R.string.hours_ago, hours)
            }

            // Прошло больше суток
            else -> formatter.format(timestamp)
        }
    }

    fun formatCurrentDate(): String {
        return formatter.format(System.currentTimeMillis())
    }
}