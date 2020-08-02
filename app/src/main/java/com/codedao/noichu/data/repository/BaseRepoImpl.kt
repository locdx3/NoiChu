package com.codedao.noichu.data.repository

import com.codedao.noichu.data.api.BaseApi
import com.codedao.noichu.data.api.entity.BaseDto
import com.codedao.noichu.data.mapper.Mapper
import com.codedao.noichu.domain.entity.BaseModel
import com.codedao.noichu.domain.repository.BaseRepo
import io.reactivex.Single
import javax.inject.Inject

class BaseRepoImpl @Inject constructor() : BaseRepo {
    @Inject
    lateinit var baseApi: BaseApi
    @Inject
    lateinit var mapperBaseDto: Mapper<BaseDto, BaseModel>

    override fun checkAppExpire(): Single<BaseModel> {
        return baseApi.getCheckAppExpire().map {
            return@map mapperBaseDto.map(it)
        }
    }

    override fun checkMaintenanceMode(): Single<BaseModel> {
        return baseApi.checkMaintenanceMode().map {
            return@map mapperBaseDto.map(it)
        }
    }
}