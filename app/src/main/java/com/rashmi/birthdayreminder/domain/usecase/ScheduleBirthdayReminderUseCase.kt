package com.rashmi.birthdayreminder.domain.usecase

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.rashmi.birthdayreminder.domain.util.nextBirthday
import com.rashmi.birthdayreminder.worker.BirthdayReminderWorker
import java.time.Duration
import java.time.LocalDate
import javax.inject.Inject

class ScheduleBirthdayReminderUseCase @Inject constructor(
    private val workManager: WorkManager
) {
    operator fun invoke(
        id: Int,
        name: String,
        birthday: LocalDate
    ) {
        val today = LocalDate.now()
        val nextBirthday = birthday.nextBirthday()

        val delay = Duration.between(today, nextBirthday.atStartOfDay())

        val inputData = workDataOf(
            BirthdayReminderWorker.KEY_NAME to name,
            BirthdayReminderWorker.KEY_DATE to birthday.toString(),
            BirthdayReminderWorker.KEY_ID to id
        )

        val request = OneTimeWorkRequestBuilder<BirthdayReminderWorker>()
            .setInputData(inputData)
            .setInitialDelay(delay)
            .build()

        workManager.enqueueUniqueWork(
            uniqueWorkName = uniqueWorkName(id),
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun uniqueWorkName(id: Int) = "birthday_reminder_$id"
}