package com.rashmi.birthdayreminder.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayDao {

    @Insert
    suspend fun insertBirthday(birthday: BirthdayEntity): Long

    @Query("SELECT * FROM birthdays")
    fun getBirthdays(): Flow<List<BirthdayEntity>>

    @Query("DELETE FROM birthdays WHERE id = :id")
    suspend fun deleteBirthday(id: Int)
}