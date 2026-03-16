package com.rashmi.birthdayreminder.domain.usecase

import androidx.work.WorkManager
import javax.inject.Inject

class CancelBirthdayReminderUseCase @Inject constructor(
    private val workManager: WorkManager
) {

    operator fun invoke(id: Int) {
        workManager.cancelUniqueWork(
            "birthday_reminder_$id"
        )
    }
}