package com.rashmi.birthdayreminder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import com.rashmi.birthdayreminder.di.BootReceiverEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//receives callback when device reboots - reschedule workers again as WorkManager tasks are cleared after reboot.
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.let { context ->

                val workManager = WorkManager.getInstance(context)
                val entryPoint = EntryPointAccessors.fromApplication(
                    context,
                    BootReceiverEntryPoint::class.java
                )
                val repository = entryPoint.repository()
                val scheduleReminder = entryPoint.scheduleReminder()

                CoroutineScope(Dispatchers.IO).launch {
                    repository
                        .getBirthdays()
                        .first()
                        .forEach {
                            scheduleReminder(
                                it.id,
                                it.name,
                                it.date
                            )
                        }
                }
            }
        }
    }
}