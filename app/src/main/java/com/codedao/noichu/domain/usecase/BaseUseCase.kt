package com.codedao.noichu.domain.usecase

import com.codedao.noichu.domain.entity.BaseModel
import com.codedao.noichu.domain.repository.BaseRepo
import io.reactivex.Single
import javax.inject.Inject

class BaseUseCase @Inject constructor() {
    @Inject
    lateinit var baseRepo: BaseRepo

    fun checkAppExpire(): Single<BaseModel> {
        return baseRepo.checkAppExpire()
    }

    fun checkMaintenanceMode(): Single<BaseModel> {
        return baseRepo.checkMaintenanceMode()
    }
}