package com.reymelin.gradientclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@CapacitorPlugin(name = "ClockSnapshot")
public class ClockSnapshotPlugin extends Plugin {

  private static final String SNAPSHOT_FILENAME = "clock_snapshot.png";

  @PluginMethod
  public void saveSnapshot(PluginCall call) {
    String dataUrl = call.getString("dataUrl");
    
    if (dataUrl == null || !dataUrl.startsWith("data:image/png;base64,")) {
      call.reject("Invalid data URL");
      return;
    }

    try {
      // Remove "data:image/png;base64," prefix
      String base64Data = dataUrl.substring("data:image/png;base64,".length());
      byte[] decodedBytes = Base64.decode(base64Data, Base64.DEFAULT);
      
      Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
      if (bitmap == null) {
        call.reject("Failed to decode bitmap");
        return;
      }

      // Save to internal storage
      File file = new File(getContext().getFilesDir(), SNAPSHOT_FILENAME);
      FileOutputStream fos = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
      fos.close();
      bitmap.recycle();

      // Trigger widget update immediately after snapshot is saved
      com.reymelin.gradientclock.widget.GradientClockWidgetProvider.updateAll(getContext());

      JSObject result = new JSObject();
      result.put("path", file.getAbsolutePath());
      call.resolve(result);
    } catch (Exception e) {
      call.reject("Failed to save snapshot: " + e.getMessage());
    }
  }

  @PluginMethod
  public void getSnapshotPath(PluginCall call) {
    File file = new File(getContext().getFilesDir(), SNAPSHOT_FILENAME);
    JSObject result = new JSObject();
    result.put("path", file.getAbsolutePath());
    result.put("exists", file.exists());
    call.resolve(result);
  }

  public static String getSnapshotFilePath(Context context) {
    File file = new File(context.getFilesDir(), SNAPSHOT_FILENAME);
    return file.getAbsolutePath();
  }

  public static boolean snapshotExists(Context context) {
    File file = new File(context.getFilesDir(), SNAPSHOT_FILENAME);
    return file.exists();
  }
}
