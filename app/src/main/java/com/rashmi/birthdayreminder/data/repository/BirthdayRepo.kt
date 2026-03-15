package com.rashmi.birthdayreminder.data.repository

import com.rashmi.birthdayreminder.data.db.BirthdayDao
import com.rashmi.birthdayreminder.data.db.BirthdayEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class BirthdayRepo @Inject constructor(
    private val dao: BirthdayDao
) : IBirthdayRepo {

    override suspend fun insertBirthday(name: String, date: LocalDate): Long {
        return dao.insertBirthday(
            BirthdayEntity(
                name = name, date = date,
            )
        )
    }

    override fun getBirthdays(): Flow<List<BirthdayEntity>> {
        return dao.getBirthdays()
    }
}