package com.codedao.noichu.common.di.module

import com.codedao.noichu.data.repository.BaseRepoImpl
import com.codedao.noichu.domain.repository.BaseRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule() {

    @Provides
    @Singleton
    fun provideBaseRepo(repoImpl: BaseRepoImpl): BaseRepo {
        return repoImpl
    }

}
