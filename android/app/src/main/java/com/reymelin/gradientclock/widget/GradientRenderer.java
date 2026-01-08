package com.reymelin.gradientclock.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;

import java.io.File;
import java.util.Calendar;

public class GradientRenderer {

  private static final String SNAPSHOT_FILENAME = "clock_snapshot.png";

  /**
   * Render bitmap for widget.
   * Tries to load the clock snapshot from the web app first.
   * Falls back to gradient if snapshot not available.
   */
  public static Bitmap renderWidgetBitmap(Context context, int width, int height) {
      File snapshotFile = new File(context.getFilesDir(), SNAPSHOT_FILENAME);

      android.util.Log.d("Widget", "Looking for snapshot: " + snapshotFile.getAbsolutePath()
              + " exists=" + snapshotFile.exists()
              + " size=" + snapshotFile.length());

      if (snapshotFile.exists()) {
          Bitmap snapshot = BitmapFactory.decodeFile(snapshotFile.getAbsolutePath());

          if (snapshot == null) {
              android.util.Log.e("Widget", "Snapshot exists but decodeFile returned null (invalid/empty PNG?)");
          } else {
              int size = Math.min(width, height);

              // add internal padding so the circle doesn't touch edges
              int padded = (int)(size * 0.92f); // 8% padding
              Bitmap scaled = Bitmap.createScaledBitmap(snapshot, padded, padded, true);

              // put scaled bitmap onto a transparent square canvas (size x size)
              Bitmap out = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
              Canvas c = new Canvas(out);
              c.drawColor(android.graphics.Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
              int left = (size - padded) / 2;
              int top = (size - padded) / 2;
              c.drawBitmap(scaled, left, top, null);

              if (scaled != snapshot) scaled.recycle();
              snapshot.recycle();

              return out;
          }
      }

      android.util.Log.d("Widget", "Using fallback gradient.");
      return renderGradientBitmap(width, height);
  }


    /**
   * Legacy gradient renderer (fallback)
   */
  public static Bitmap renderGradientBitmap() {
    return renderGradientBitmap(600, 300);
  }

    private static Bitmap renderGradientBitmap(int w, int h) {
        int[] colors = colorsFromTime();
        int c1 = colors[0];
        int c2 = colors[1];

        int size = Math.min(w, h);

        Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        // Clear to transparent
        canvas.drawColor(android.graphics.Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient shader = new LinearGradient(
                0f, 0f, 0f, (float) size,
                c1, c2,
                Shader.TileMode.CLAMP
        );
        paint.setShader(shader);

        // 🔑 Draw a CIRCLE instead of a square
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

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
