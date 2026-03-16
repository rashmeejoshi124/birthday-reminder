package com.rashmi.birthdayreminder.domain.model

import java.time.LocalDate

data class BirthdayData(
    val name: String = "",
    val dateDigits: String = "",
    val date: LocalDate? = null,
    val id: Int = 0
)