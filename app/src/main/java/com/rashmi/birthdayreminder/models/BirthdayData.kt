package com.rashmi.birthdayreminder.models

import java.time.LocalDate

data class BirthdayData(
    val name: String = "",
    val dateDigits: String = "",
    val date: LocalDate? = null
)