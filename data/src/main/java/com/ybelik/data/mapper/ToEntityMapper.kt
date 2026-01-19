package com.ybelik.data.mapper

interface ToEntityMapper<Domain, Data> {

    fun toEnt(domainModel: Domain): Data
}