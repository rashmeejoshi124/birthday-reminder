package com.rashmi.birthdayreminder.di

import com.rashmi.birthdayreminder.data.db.BirthdayDao
import com.rashmi.birthdayreminder.domain.usecase.GetSortedBirthdaysUseCase
import com.rashmi.birthdayreminder.domain.usecase.IGetSortedBirthdayUseCase
import com.rashmi.birthdayreminder.data.repository.BirthdayRepo
import com.rashmi.birthdayreminder.data.repository.IBirthdayRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun bindsIBirthdayRepo(dao: BirthdayDao): IBirthdayRepo {
        return BirthdayRepo(dao)
    }

    @Provides
    fun providesSortedBirthdayUseCase(repo: IBirthdayRepo): IGetSortedBirthdayUseCase {
        return GetSortedBirthdaysUseCase(repo)
    }
}