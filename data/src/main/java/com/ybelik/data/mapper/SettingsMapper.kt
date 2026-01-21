package com.ybelik.data.mapper

import com.ybelik.domain.model.Interval

fun Int.toInterval(): Interval {
    return Interval.entries.first { it.minutes == this }
}