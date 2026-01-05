package com.reymelin.gradientclock;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import com.getcapacitor.BridgeActivity;

/**
 * Optimized MainActivity for Gradient Clock
 * Includes performance optimizations and fullscreen support
 */
public class MainActivity extends BridgeActivity {
    private static final String TAG = "GradientClock";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "MainActivity onCreate");
        
        // Enable immersive fullscreen mode
        enableImmersiveMode();
        
        // Keep screen on (optional, can be controlled by user preference)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        // Allow brightness control
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        getWindow().setAttributes(params);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Optimize WebView after it's been initialized by Capacitor
        optimizeWebView();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableImmersiveMode();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableImmersiveMode();
        }
    }

    /**
     * Enable immersive fullscreen mode for distraction-free clock display
     */
    private void enableImmersiveMode() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    /**
     * Optimize WebView for smooth animation performance and persistent storage
     */
    private void optimizeWebView() {
        WebView bridge = getBridge().getWebView();
        if (bridge != null) {
            Log.d(TAG, "Configuring WebView settings");
            
            WebSettings settings = bridge.getSettings();
            
            // Enable hardware acceleration
            bridge.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            
            // Enable JavaScript (should already be enabled by Capacitor)
            settings.setJavaScriptEnabled(true);
            
            // Optimize rendering
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            
            // Optimize JavaScript execution
            settings.setJavaScriptCanOpenWindowsAutomatically(false);
            
            // Disable unnecessary features for performance
            settings.setGeolocationEnabled(false);
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(false);
            settings.setDisplayZoomControls(false);
            
            // ===== CRITICAL FOR LOCALSTORAGE PERSISTENCE =====
            // Enable DOM storage for local preferences
            settings.setDomStorageEnabled(true);
            Log.d(TAG, "DOM Storage enabled: " + settings.getDomStorageEnabled());
            
            // Enable database storage for localStorage persistence
            settings.setDatabaseEnabled(true);
            
            // Set database path for persistent storage
            String databasePath = getApplicationContext().getDir("databases", MODE_PRIVATE).getPath();
            settings.setDatabasePath(databasePath);
            Log.d(TAG, "Database path set to: " + databasePath);
            
            // Enable App Cache for offline support
            settings.setAppCacheEnabled(true);
            String cachePath = getApplicationContext().getCacheDir().getAbsolutePath();
            settings.setAppCachePath(cachePath);
            settings.setAppCacheMaxSize(10 * 1024 * 1024); // 10MB
            Log.d(TAG, "App cache path set to: " + cachePath);
            
            // Enable console logging for debugging
            bridge.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onConsoleMessage(ConsoleMessage cm) {
                    Log.d(TAG, String.format("[JS Console] %s @ %s:%d",
                        cm.message(), cm.sourceId(), cm.lineNumber()));
                    return true;
                }
            });
            
            Log.d(TAG, "WebView configuration complete");
        } else {
            Log.e(TAG, "WebView bridge is null - cannot configure settings");
        }
    }
}
