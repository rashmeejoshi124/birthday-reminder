package com.rashmi.birthdayreminder.domain.usecase

import com.rashmi.birthdayreminder.domain.model.BirthdayData
import kotlinx.coroutines.flow.Flow

interface IGetSortedBirthdayUseCase {
    operator fun invoke(): Flow<List<BirthdayData>>
}