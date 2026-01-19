package com.ybelik.data.mapper

interface RemoteToDbMapper<Remote, DataBase> {

    fun toEntity(remoteModel: Remote): DataBase
}