package com.rashmi.birthdayreminder.domain.usecase

import com.rashmi.birthdayreminder.data.db.toUiModel
import com.rashmi.birthdayreminder.domain.util.nextBirthday
import com.rashmi.birthdayreminder.domain.model.BirthdayData
import com.rashmi.birthdayreminder.data.repository.IBirthdayRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedBirthdaysUseCase @Inject constructor(
    private val repo: IBirthdayRepo
) : IGetSortedBirthdayUseCase {
    override operator fun invoke(): Flow<List<BirthdayData>> {
        return repo.getBirthdays().map { birthdays ->
            birthdays.sortedBy { birthday ->
                birthday.date.nextBirthday()
            }.map { it.toUiModel() }
        }
    }
}