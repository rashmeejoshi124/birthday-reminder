package com.rashmi.birthdayreminder.domain.usecase

import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.rashmi.birthdayreminder.domain.util.nextBirthday
import com.rashmi.birthdayreminder.worker.BirthdayReminderWorker
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleBirthdayReminderUseCase @Inject constructor(
    private val workManager: WorkManager
) {
    operator fun invoke(
        id: Int,
        name: String,
        birthday: LocalDate
    ) {
        val nextBirthday = birthday.nextBirthday()

        val delay = maxOf(
            Duration.ZERO,
            Duration.between(LocalDateTime.now(), nextBirthday.atStartOfDay())
        )
        // val tempDelay = Duration.ofSeconds(10)

        Log.d("BirthdayWorker", "inside invoke")

        val inputData = workDataOf(
            BirthdayReminderWorker.KEY_NAME to name,
            BirthdayReminderWorker.KEY_DATE to birthday.toString(),
            BirthdayReminderWorker.KEY_ID to id
        )

        val request = OneTimeWorkRequestBuilder<BirthdayReminderWorker>()
            .setInputData(inputData)
            .setInitialDelay(delay.toMillis(), TimeUnit.MILLISECONDS)
            .build()
        Log.d("BirthdayWorker", "Worker enqueued $id - $name - $nextBirthday")
        workManager.enqueueUniqueWork(
            uniqueWorkName = uniqueWorkName(id),
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun uniqueWorkName(id: Int) = "birthday_reminder_$id"
}