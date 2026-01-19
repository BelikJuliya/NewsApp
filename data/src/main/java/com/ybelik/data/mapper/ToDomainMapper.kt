package com.ybelik.data.mapper

interface ToDomainMapper<Domain, Data> {

    fun toDomain(model: Data): Domain
}