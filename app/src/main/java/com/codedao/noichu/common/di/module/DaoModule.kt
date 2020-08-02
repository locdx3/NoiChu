package com.codedao.noichu.common.di.module

import android.content.Context
import androidx.room.Room
import com.codedao.noichu.data.db.AppDatabase
import com.codedao.noichu.data.db.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(roomDatabase: AppDatabase): UserDao {
        return roomDatabase.userDao()
    }
}