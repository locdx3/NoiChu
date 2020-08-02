package com.codedao.noichu.common.di

import com.codedao.noichu.common.di.module.*
import com.codedao.noichu.presentation.main.detail.DetailFragment
import com.codedao.noichu.presentation.main.favorite.FavoriteFragment
import com.codedao.noichu.presentation.main.home.HomeFragment
import com.codedao.noichu.presentation.main.setting.SettingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DaoModule::class, CommonModule::class, MapperModule::class, RepoModule::class, ViewModelModule::class])
interface AppComponent {
    // inject fragment
    fun inject(fragment: HomeFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: SettingFragment)
    fun inject(fragment: DetailFragment)
}