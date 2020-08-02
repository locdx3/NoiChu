package com.codedao.noichu.data.mapper

import com.codedao.noichu.data.api.entity.BaseDto
import com.codedao.noichu.domain.entity.BaseModel
import javax.inject.Inject

class BaseDtoMapper @Inject constructor() : Mapper<BaseDto, BaseModel>() {
    override fun map(from: BaseDto): BaseModel {
        return from.let {
            BaseModel(message = it.message, status = it.status, errors = it.errors)
        }
    }

    override fun reverse(to: BaseModel): BaseDto {
        return to.let {
            BaseDto(
                message = it.message,
                status = it.status,
                errors = it.errors
            )
        }
    }
}