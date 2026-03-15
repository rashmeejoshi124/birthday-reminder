package com.rashmi.birthdayreminder.di

import com.rashmi.birthdayreminder.data.repository.BirthdayRepo
import com.rashmi.birthdayreminder.data.repository.IBirthdayRepo
import com.rashmi.birthdayreminder.domain.usecase.ScheduleBirthdayReminderUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BootReceiverEntryPoint {

    fun repository(): IBirthdayRepo

    fun scheduleReminder(): ScheduleBirthdayReminderUseCase
}