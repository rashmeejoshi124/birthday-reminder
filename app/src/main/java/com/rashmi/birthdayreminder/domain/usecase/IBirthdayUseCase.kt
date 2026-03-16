package com.rashmi.birthdayreminder.domain.usecase

import com.rashmi.birthdayreminder.domain.model.BirthdayData
import kotlinx.coroutines.flow.Flow

interface IBirthdayUseCase {
    fun getSortedBirthdayList(): Flow<List<BirthdayData>>

    suspend fun addBirthday(birthdayData: BirthdayData)

    suspend fun deleteBirthday(id: Int)
}