package com.reymelin.gradientclock.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import java.util.Calendar
import kotlin.math.abs

object GradientRenderer {

  fun renderGradientBitmap(context: Context, w: Int = 600, h: Int = 300): Bitmap {
    val (c1, c2) = colorsFromTime()

    val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val shader = LinearGradient(
      0f, 0f, 0f, h.toFloat(),
      c1, c2,
      Shader.TileMode.CLAMP
    )
    paint.shader = shader
    canvas.drawRect(0f, 0f, w.toFloat(), h.toFloat(), paint)

    return bmp
  }

  private fun colorsFromTime(): Pair<Int, Int> {
    val cal = Calendar.getInstance()
    val minutes = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)
    val t = minutes / (24f * 60f) // 0..1

    // Simple pleasing gradient based on HSV:
    val hue1 = (360f * t) % 360f
    val hue2 = (hue1 + 60f) % 360f

    val c1 = hsvToColor(hue1, 0.85f, 0.95f)
    val c2 = hsvToColor(hue2, 0.85f, 0.75f)
    return Pair(c1, c2)
  }

  private fun hsvToColor(h: Float, s: Float, v: Float): Int {
    // Android Color.HSVToColor exists, but we keep it explicit:
    val hsv = floatArrayOf(h, s, v)
    return android.graphics.Color.HSVToColor(hsv)
  }
}
