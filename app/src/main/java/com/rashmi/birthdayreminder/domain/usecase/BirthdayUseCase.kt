package com.rashmi.birthdayreminder.domain.usecase

import com.rashmi.birthdayreminder.data.db.toUiModel
import com.rashmi.birthdayreminder.data.repository.IBirthdayRepo
import com.rashmi.birthdayreminder.domain.model.BirthdayData
import com.rashmi.birthdayreminder.domain.util.nextBirthday
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BirthdayUseCase @Inject constructor(
    private val repo: IBirthdayRepo,
    private val scheduleReminder: ScheduleBirthdayReminderUseCase,
    private val cancelReminder: CancelBirthdayReminderUseCase
) : IBirthdayUseCase {

    override fun getSortedBirthdayList(): Flow<List<BirthdayData>> {
        return repo.getBirthdays().map { birthdays ->
            birthdays.sortedBy { birthday ->
                birthday.date.nextBirthday()
            }.map { it.toUiModel() }
        }
    }

    override suspend fun addBirthday(birthdayData: BirthdayData) {
        birthdayData.date?.let {
            val id = repo.insertBirthday(
                name = birthdayData.name,
                date = birthdayData.date
            )
            scheduleReminder(id.toInt(), birthdayData.name, birthdayData.date)
        }
    }

    override suspend fun deleteBirthday(id: Int) {
        repo.deleteBirthday(id)
        cancelReminder(id)
    }
}