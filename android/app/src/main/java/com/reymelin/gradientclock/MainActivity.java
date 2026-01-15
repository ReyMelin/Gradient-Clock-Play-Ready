package com.reymelin.gradientclock;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.getcapacitor.BridgeActivity;

/**
 * Optimized MainActivity for Gradient Clock
 * Includes performance optimizations and fullscreen support
 */
public class MainActivity extends BridgeActivity {
    private static final String TAG = "GradientClock";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(ClockSnapshotPlugin.class);
        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainActivity onCreate");

        // Enable edge-to-edge display for Android 15+ compatibility
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        
        enableImmersiveMode();
        setupWindowInsets();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
     * Uses modern WindowInsetsController API for Android 15+ compatibility
     */
    private void enableImmersiveMode() {
        // Use the modern WindowInsetsController API
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
            .hide(WindowInsetsCompat.Type.systemBars());
        
        // Set behavior for immersive sticky mode
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
            .setSystemBarsBehavior(
                androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
    }

    /**
     * Setup window insets handling for edge-to-edge display
     * Ensures content is properly displayed on Android 15+
     */
    private void setupWindowInsets() {
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                
                // For a fullscreen clock, we want to draw behind system bars
                // but we log the insets in case we need to handle them differently
                Log.d(TAG, "Window insets - top: " + insets.top + ", bottom: " + insets.bottom + 
                          ", left: " + insets.left + ", right: " + insets.right);
                
                // Return the insets unchanged to allow drawing behind system bars
                return windowInsets;
            });
        }
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
            // Note: setDatabasePath() is deprecated since API 26 and not needed
            // The system handles database paths automatically

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
