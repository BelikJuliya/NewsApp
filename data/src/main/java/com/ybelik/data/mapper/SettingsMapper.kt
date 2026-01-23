package com.ybelik.data.mapper

import com.ybelik.data.R
import com.ybelik.domain.model.Interval
import com.ybelik.domain.model.Language
import com.ybelik.domain.model.RefreshConfig
import com.ybelik.domain.model.Settings
import androidx.compose.ui.res.stringResource

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


fun Language.toHumanReadable() {
    when(this) {
        Language.RUSSIAN -> stringResource(R.string.russian)
        Language.ENGLISH -> stringResource(R.string.english)
        Language.FRENCH -> stringResource(R.string.francais)
        Language.GERMAN -> stringResource(R.string.deutsch)
    }
}


fun Interval.toHumanReadable() {
    when(this) {
        Interval.MIN_15 -> stringResource(R.string._15_minute)
        Interval.MIN_30 -> stringResource(R.string._30_minute)
        Interval.HOUR_1 -> stringResource(R.string._1_hour)
        Interval.HOUR_2 -> stringResource(R.string._2_hours)
        Interval.HOUR_4 -> stringResource(R.string._4_hours)
        Interval.HOUR_8 -> stringResource(R.string._8_hours)
        Interval.HOUR_24 -> stringResource(R.string._24_hours)
    }
}