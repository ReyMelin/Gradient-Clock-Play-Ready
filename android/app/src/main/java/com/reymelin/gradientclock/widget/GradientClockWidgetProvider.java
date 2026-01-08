package com.reymelin.gradientclock.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.reymelin.gradientclock.MainActivity;
import com.reymelin.gradientclock.R;

public class GradientClockWidgetProvider extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int appWidgetId : appWidgetIds) {
      updateOne(context, appWidgetManager, appWidgetId);
    }
    WidgetUpdateScheduler.ensureScheduled(context);
  }

  @Override
  public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
    super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    updateOne(context, appWidgetManager, appWidgetId);
  }

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    WidgetUpdateScheduler.ensureScheduled(context);
  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    WidgetUpdateScheduler.cancel(context);
  }

  public static void updateAll(Context context) {
    AppWidgetManager mgr = AppWidgetManager.getInstance(context);
    int[] ids = mgr.getAppWidgetIds(new ComponentName(context, GradientClockWidgetProvider.class));
    for (int id : ids) updateOne(context, mgr, id);
  }

  private static void updateOne(Context context, AppWidgetManager mgr, int appWidgetId) {
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gradient_clock_widget);

    Bundle options = mgr.getAppWidgetOptions(appWidgetId);
    int minW = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
    int minH = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

    // Convert dp -> px
    float density = context.getResources().getDisplayMetrics().density;
    int pxW = (int) (minW * density);
    int pxH = (int) (minH * density);

    if (pxW == 0 || pxH == 0) {
        android.util.Log.d("Widget", "Still waiting for size, delaying update");
        return;
    }

    Bitmap bmp = GradientRenderer.renderWidgetBitmap(context, pxW, pxH);
    views.setImageViewBitmap(R.id.widget_bg, bmp);

    Intent intent = new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

    int flags = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) flags = PendingIntent.FLAG_IMMUTABLE;

    PendingIntent pi = PendingIntent.getActivity(context, 0, intent, flags);
    views.setOnClickPendingIntent(R.id.widget_bg, pi);

    mgr.updateAppWidget(appWidgetId, views);
  }
}
