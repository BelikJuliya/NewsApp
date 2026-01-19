package com.ybelik.domain.repoository

import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getAllSubscriptions(): Flow<List<String>>
}