package com.rashmi.birthdayreminder.data.repository

import com.rashmi.birthdayreminder.data.db.BirthdayEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface IBirthdayRepo {

    fun getBirthdays(): Flow<List<BirthdayEntity>>

    suspend fun insertBirthday(name: String, date: LocalDate): Long

    suspend fun deleteBirthday(id: Int)
}