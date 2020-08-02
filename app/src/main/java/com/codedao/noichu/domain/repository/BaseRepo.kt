package com.codedao.noichu.domain.repository

import com.codedao.noichu.domain.entity.BaseModel
import io.reactivex.Single

interface BaseRepo {
    fun checkAppExpire(): Single<BaseModel>
    fun checkMaintenanceMode(): Single<BaseModel>
}