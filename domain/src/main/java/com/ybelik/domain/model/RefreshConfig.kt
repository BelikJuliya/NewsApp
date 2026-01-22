package com.ybelik.domain.model

data class RefreshConfig(
    val language: Language,
    val interval: Interval,
    val isWifiOnly: Boolean
)
