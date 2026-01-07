package com.reymelin.gradientclock.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import java.util.Calendar;

public class GradientRenderer {

  public static Bitmap renderGradientBitmap() {
    int w = 600;
    int h = 300;

    int[] colors = colorsFromTime();
    int c1 = colors[0];
    int c2 = colors[1];

    Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bmp);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    LinearGradient shader = new LinearGradient(
      0f, 0f, 0f, (float) h,
      c1, c2,
      Shader.TileMode.CLAMP
    );
    paint.setShader(shader);
    canvas.drawRect(0f, 0f, (float) w, (float) h, paint);

    return bmp;
  }

  private static int[] colorsFromTime() {
    Calendar cal = Calendar.getInstance();
    int minutes = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
    float t = minutes / (24f * 60f); // 0..1

    float hue1 = (360f * t) % 360f;
    float hue2 = (hue1 + 60f) % 360f;

    int c1 = android.graphics.Color.HSVToColor(new float[]{hue1, 0.85f, 0.95f});
    int c2 = android.graphics.Color.HSVToColor(new float[]{hue2, 0.85f, 0.75f});
    return new int[]{c1, c2};
  }
}
