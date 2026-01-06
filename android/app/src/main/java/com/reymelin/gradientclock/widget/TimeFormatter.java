package com.reymelin.gradientclock.widget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeFormatter {
  private static final SimpleDateFormat fmt = new SimpleDateFormat("h:mm", Locale.getDefault());
  public static String now() {
    return fmt.format(new Date());
  }
}
