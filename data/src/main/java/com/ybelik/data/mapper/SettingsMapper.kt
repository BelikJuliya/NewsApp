package com.ybelik.data.mapper

import com.ybelik.domain.model.Interval
import com.ybelik.domain.model.RefreshConfig
import com.ybelik.domain.model.Settings

fun Int.toInterval(): Interval {
    return Interval.entries.first { it.minutes == this }
}

fun Settings.toRefreshConfig() = RefreshConfig(
    language = language,
    interval = interval,
    isWifiOnly = isWifiOnly
)