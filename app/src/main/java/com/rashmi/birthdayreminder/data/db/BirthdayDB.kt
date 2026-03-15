package com.rashmi.birthdayreminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BirthdayEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class BirthdayDB: RoomDatabase() {

    abstract fun birthdayDao(): BirthdayDao

}