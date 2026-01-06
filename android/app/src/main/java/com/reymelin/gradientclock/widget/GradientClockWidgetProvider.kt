package com.reymelin.gradientclock.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.reymelin.gradientclock.MainActivity
import com.reymelin.gradientclock.R

class GradientClockWidgetProvider : AppWidgetProvider() {

  override fun onUpdate(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray
  ) {
    for (appWidgetId in appWidgetIds) {
      updateOne(context, appWidgetManager, appWidgetId)
    }
    WidgetUpdateScheduler.ensureScheduled(context)
  }

  override fun onEnabled(context: Context) {
    super.onEnabled(context)
    WidgetUpdateScheduler.ensureScheduled(context)
  }

  override fun onDisabled(context: Context) {
    super.onDisabled(context)
    WidgetUpdateScheduler.cancel(context)
  }

  companion object {
    fun updateAll(context: Context) {
      val mgr = AppWidgetManager.getInstance(context)
      val ids = mgr.getAppWidgetIds(
        ComponentName(context, GradientClockWidgetProvider::class.java)
      )
      for (id in ids) updateOne(context, mgr, id)
    }

    private fun updateOne(
      context: Context,
      appWidgetManager: AppWidgetManager,
      appWidgetId: Int
    ) {
      val views = RemoteViews(context.packageName, R.layout.gradient_clock_widget)

      // Background gradient bitmap + current time
      val bmp = GradientRenderer.renderGradientBitmap(context)
      views.setImageViewBitmap(R.id.widget_bg, bmp)
      views.setTextViewText(R.id.widget_time, TimeFormatter.nowHHmm())

      // Tap opens app
      val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
      }
      val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        PendingIntent.FLAG_IMMUTABLE else 0

      val pi = PendingIntent.getActivity(context, 0, intent, flags)
      views.setOnClickPendingIntent(R.id.widget_bg, pi)
      views.setOnClickPendingIntent(R.id.widget_time, pi)

      appWidgetManager.updateAppWidget(appWidgetId, views)
    }
  }
}
