package com.ybelik.data.mapper

import com.ybelik.data.remote.response.SourceResponse

object SourceMapper : ToDomainMapper<String, SourceResponse> {

    override fun toDomain(model: SourceResponse): String {
        return model.name
    }
}