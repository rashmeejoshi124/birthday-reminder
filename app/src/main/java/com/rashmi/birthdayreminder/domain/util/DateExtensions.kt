package com.rashmi.birthdayreminder.domain.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun LocalDate.nextBirthday(today: LocalDate = LocalDate.now()): LocalDate {

    var next = this.withYear(today.year)

    if (next.isBefore(today)) {
        next = next.plusYears(1)
    }

    return next
}

fun LocalDate.daysUntilNextBirthday(today: LocalDate = LocalDate.now()): Long {
    return ChronoUnit.DAYS.between(today, nextBirthday())
}

fun LocalDate.birthdayLabel(): String {
    val days = daysUntilNextBirthday()

    return when(days) {
        0L -> "Today \uD83C\uDF89"
        1L -> "Tomorrow \uD83C\uDF82"
        else -> "$days days left"
    }
}