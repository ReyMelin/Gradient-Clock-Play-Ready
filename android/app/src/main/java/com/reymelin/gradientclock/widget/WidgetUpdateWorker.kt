package com.reymelin.gradientclock.widget

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WidgetUpdateWorker(
  appContext: Context,
  params: WorkerParameters
) : Worker(appContext, params) {

  override fun doWork(): Result {
    GradientClockWidgetProvider.updateAll(applicationContext)
    return Result.success()
  }
}
