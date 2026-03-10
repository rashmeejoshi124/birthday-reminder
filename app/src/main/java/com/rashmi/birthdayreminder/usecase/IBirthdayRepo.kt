package com.rashmi.birthdayreminder.usecase

import java.time.LocalDate

interface IBirthdayRepo {

    suspend fun insertBirthday(name: String, date: LocalDate)
}