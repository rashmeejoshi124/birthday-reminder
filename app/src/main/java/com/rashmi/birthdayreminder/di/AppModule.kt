package com.rashmi.birthdayreminder.di

import com.rashmi.birthdayreminder.db.BirthdayDao
import com.rashmi.birthdayreminder.usecase.BirthdayRepo
import com.rashmi.birthdayreminder.usecase.IBirthdayRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun bindsIBirthdayRepo(dao: BirthdayDao): BirthdayRepo {
        return BirthdayRepo(dao)
    }
}