package com.codedao.noichu.common.di.module

import com.codedao.noichu.data.api.entity.BaseDto
import com.codedao.noichu.data.mapper.BaseDtoMapper
import com.codedao.noichu.data.mapper.Mapper
import com.codedao.noichu.domain.entity.BaseModel
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @Binds
    abstract fun provideBaseDto(baseDtoMapper: BaseDtoMapper): Mapper<BaseDto, BaseModel>
}