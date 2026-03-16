package com.rashmi.birthdayreminder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rashmi.birthdayreminder.domain.model.BirthdayData
import java.time.LocalDate

@Entity(tableName = "birthdays")
data class BirthdayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val date: LocalDate
)

fun BirthdayEntity.toUiModel(): BirthdayData {
    return BirthdayData(id = id, name = name, date =  date)
}