package com.rashmi.birthdayreminder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [BirthdayEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class BirthdayDB: RoomDatabase() {

    abstract fun birthdayDao(): BirthdayDao

}