package com.rashmi.birthdayreminder.data.repository

import com.rashmi.birthdayreminder.data.db.BirthdayEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface IBirthdayRepo {

    suspend fun insertBirthday(name: String, date: LocalDate)

    fun getBirthdays(): Flow<List<BirthdayEntity>>
}