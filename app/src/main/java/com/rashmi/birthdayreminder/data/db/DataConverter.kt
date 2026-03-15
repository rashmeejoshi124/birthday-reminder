package com.rashmi.birthdayreminder.data.db

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(value: String): LocalDate {
        return LocalDate.parse(value)
    }
}