package com.rashmi.birthdayreminder.worker

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rashmi.birthdayreminder.R
import com.rashmi.birthdayreminder.domain.usecase.ScheduleBirthdayReminderUseCase
import com.rashmi.birthdayreminder.notifications.NotificationConstants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

@HiltWorker
class BirthdayReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val scheduleReminder: ScheduleBirthdayReminderUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val name = inputData.getString(KEY_NAME) ?: return Result.failure()
        val dateString = inputData.getString(KEY_DATE) ?: return Result.failure()
        val id = inputData.getInt(KEY_ID, -1)

        val birthday = LocalDate.parse(dateString)
        scheduleReminder(id, name, birthday)
        showNotification(name)

        return Result.success()
    }

    private fun showNotification(name: String) {
        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(
            context,
            NotificationConstants.CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Birthday Reminder 🎉")
            .setContentText("Don't forget to wish $name!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        manager.notify(name.hashCode(), notification)
    }

    companion object {
        const val KEY_NAME = "name"
        const val KEY_DATE = "birthday_date"
        const val KEY_ID = "birthday_id"
    }

}