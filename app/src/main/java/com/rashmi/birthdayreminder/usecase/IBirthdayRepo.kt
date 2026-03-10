package com.rashmi.birthdayreminder.usecase

import com.rashmi.birthdayreminder.db.BirthdayEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface IBirthdayRepo {

    suspend fun insertBirthday(name: String, date: LocalDate)

    fun getBirthdays(): Flow<List<BirthdayEntity>>
}