package com.reymelin.gradientclock.widget

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WidgetUpdateScheduler {
  private const val UNIQUE_WORK_NAME = "GradientClockWidgetUpdateWork"

  fun ensureScheduled(context: Context) {
    val request = PeriodicWorkRequestBuilder<WidgetUpdateWorker>(15, TimeUnit.MINUTES)
      .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
      UNIQUE_WORK_NAME,
      ExistingPeriodicWorkPolicy.UPDATE,
      request
    )
  }

  fun cancel(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_WORK_NAME)
  }
}
