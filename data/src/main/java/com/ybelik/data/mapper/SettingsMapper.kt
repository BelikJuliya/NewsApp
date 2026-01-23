package com.ybelik.data.mapper

import com.ybelik.domain.model.Interval
import com.ybelik.domain.model.Language
import com.ybelik.domain.model.RefreshConfig
import com.ybelik.domain.model.Settings

fun Int.toInterval(): Interval {
    return Interval.entries.first { it.minutes == this }
}

fun String.toInterval(): Interval {
    return Interval.entries.first { it.name.equals(this, ignoreCase = true) }
}

fun String.toLanguage(): Language {
    return Language.entries.first { it.name.equals(this, ignoreCase = true) }
}

fun Settings.toRefreshConfig() = RefreshConfig(
    language = language,
    interval = interval,
    isWifiOnly = isWifiOnly
)