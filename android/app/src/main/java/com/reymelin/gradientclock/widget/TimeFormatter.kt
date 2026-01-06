package com.reymelin.gradientclock.widget

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormatter {
  private val fmt = SimpleDateFormat("h:mm", Locale.getDefault())
  fun nowHHmm(): String = fmt.format(Date())
}
