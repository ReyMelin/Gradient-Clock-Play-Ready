package com.reymelin.gradientclock.widget;

import android.content.Context;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class WidgetUpdateScheduler {
  private static final String UNIQUE_WORK_NAME = "GradientClockWidgetUpdateWork";

  public static void ensureScheduled(Context context) {
    PeriodicWorkRequest request =
      new PeriodicWorkRequest.Builder(WidgetUpdateWorker.class, 15, TimeUnit.MINUTES).build();

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
      UNIQUE_WORK_NAME,
      ExistingPeriodicWorkPolicy.UPDATE,
      request
    );
  }

  public static void cancel(Context context) {
    WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_WORK_NAME);
  }
}
