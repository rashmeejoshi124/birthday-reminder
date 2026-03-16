package com.rashmi.birthdayreminder.di

import android.content.Context
import androidx.work.WorkManager
import com.rashmi.birthdayreminder.data.db.BirthdayDao
import com.rashmi.birthdayreminder.data.repository.BirthdayRepo
import com.rashmi.birthdayreminder.data.repository.IBirthdayRepo
import com.rashmi.birthdayreminder.domain.usecase.BirthdayUseCase
import com.rashmi.birthdayreminder.domain.usecase.CancelBirthdayReminderUseCase
import com.rashmi.birthdayreminder.domain.usecase.IBirthdayUseCase
import com.rashmi.birthdayreminder.domain.usecase.ScheduleBirthdayReminderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun bindsIBirthdayRepo(dao: BirthdayDao): IBirthdayRepo {
        return BirthdayRepo(dao)
    }

    @Provides
    fun providesSortedBirthdayUseCase(
        repo: IBirthdayRepo,
        scheduleBirthdayReminderUseCase: ScheduleBirthdayReminderUseCase,
        cancelBirthdayReminderUseCase: CancelBirthdayReminderUseCase
    ): IBirthdayUseCase {
        return BirthdayUseCase(repo, scheduleBirthdayReminderUseCase, cancelBirthdayReminderUseCase)
    }

    @Provides
    fun provideScheduleBirthdayReminderUseCase(workManager: WorkManager) =
        ScheduleBirthdayReminderUseCase(workManager)

    @Provides
    fun provideCancelBirthdayReminderUseCase(workManager: WorkManager) =
        CancelBirthdayReminderUseCase(workManager)

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }
}