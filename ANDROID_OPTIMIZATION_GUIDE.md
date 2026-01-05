# Gradient Clock Shell - Android Studio Optimization Guide

## Overview
The Gradient Clock app has been optimized for Android Studio with support for:
- ✅ **Home Screen Widget** - Add the clock to your home screen
- ✅ **Live Wallpaper** - Set the clock as your phone background
- ✅ **Wear OS Watch Face** - Use on smartwatches
- ✅ **Performance Optimizations** - Hardware acceleration, wake locks, and WebView optimizations
- ✅ **Fullscreen Mode** - Immersive display for the main app

## New Features

### 1. Home Screen Widget
**Location:** `GradientClockWidget.java`

Users can add the Gradient Clock as a widget to their home screen:
- Long-press on home screen → Widgets → Gradient Clock Widget
- Choose theme and time format during configuration
- Widget updates every second for smooth animation
- Tap widget to open the full app

**Configuration:** `WidgetConfigActivity.java` provides a dialog for customizing:
- Theme selection (9 themes available)
- Time format (none, 24-hour, AM/PM)

### 2. Live Wallpaper
**Location:** `GradientClockWallpaperService.java`

Set the Gradient Clock as your phone's live wallpaper:
- Settings → Wallpaper → Live Wallpapers → Gradient Clock Wallpaper
- Configure theme and time format via settings
- Touch events supported (can interact with the clock)
- Optimized rendering at 60fps

**Configuration:** `WallpaperSettingsActivity.java` provides settings for:
- Theme customization
- Time display preferences

### 3. Wear OS Watch Face
**Location:** `GradientClockWatchFaceService.java`

Use Gradient Clock on Wear OS smartwatches:
- Install app on paired watch
- Long-press watch face → Select Gradient Clock
- Automatically adapts to ambient mode for battery savings
- Shows digital time in center with gradient rings

**Features:**
- Smooth 60fps in interactive mode
- Battery-efficient ambient mode
- Burn-in protection support
- Low-bit ambient mode support

### 4. Performance Optimizations
**Location:** Enhanced `MainActivity.java`

The main app now includes:

#### WebView Optimizations
- Hardware acceleration enabled
- High rendering priority
- Optimized caching
- DOM storage for preferences
- Disabled unnecessary features (zoom, geolocation)

#### Display Enhancements
- Immersive fullscreen mode (hides navigation/status bars)
- Keep screen on while app is active
- Brightness control support
- Auto-hide UI controls

#### Power Management
- Partial wake lock for smooth animations
- Auto-release wake lock on pause
- 10-minute wake lock timeout for safety

## Project Structure

```
android/app/src/main/
├── java/com/reymelin/GC/
│   ├── MainActivity.java                    // Optimized main activity
│   ├── GradientClockWidget.java            // Home screen widget
│   ├── GradientClockWallpaperService.java  // Live wallpaper
│   ├── GradientClockWatchFaceService.java  // Wear OS watch face
│   ├── WidgetConfigActivity.java           // Widget settings
│   └── WallpaperSettingsActivity.java      // Wallpaper settings
│
├── res/
│   ├── layout/
│   │   ├── gradient_clock_widget.xml       // Widget layout
│   │   ├── widget_config.xml               // Widget config UI
│   │   └── wallpaper_settings.xml          // Wallpaper settings UI
│   │
│   ├── xml/
│   │   ├── gradient_clock_widget_info.xml  // Widget metadata
│   │   ├── gradient_clock_wallpaper.xml    // Wallpaper metadata
│   │   └── watch_face.xml                  // Watch face metadata
│   │
│   └── values/
│       ├── strings.xml                      // All text resources
│       └── colors.xml                       // Color definitions
│
└── AndroidManifest.xml                      // All declarations
```

## Building and Testing

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 21+ (minSdk)
- Wear OS SDK for watch face testing

### Build Instructions

1. **Open in Android Studio:**
   ```bash
   cd android
   ./gradlew clean
   ./gradlew build
   ```

2. **Run on Device/Emulator:**
   - Main App: Select "app" configuration and run
   - Widget: Install app, then add widget from home screen
   - Wallpaper: Install app, then select from wallpaper settings
   - Watch Face: Deploy to Wear OS device/emulator

### Testing Widget
1. Install app on device
2. Long-press home screen
3. Select "Widgets"
4. Find "Gradient Clock Widget"
5. Drag to home screen
6. Configure theme and time format

### Testing Live Wallpaper
1. Install app on device
2. Go to Settings → Display → Wallpaper
3. Select "Live Wallpapers"
4. Choose "Gradient Clock Wallpaper"
5. Configure and set as wallpaper

### Testing Wear OS Watch Face
1. Pair Wear OS watch with phone
2. Install app (automatically installs on watch)
3. On watch: Long-press watch face
4. Swipe to find "Gradient Clock"
5. Tap to activate

## Permissions

The app requires these permissions (all declared in AndroidManifest.xml):

- `INTERNET` - Load web content
- `WAKE_LOCK` - Keep CPU active for animations
- `SET_WALLPAPER` - Allow live wallpaper functionality
- `PROVIDE_BACKGROUND` - Live wallpaper service
- `RECEIVE_COMPLICATION_DATA` - Wear OS complications (future feature)

## Configuration Options

### Themes (9 Available)
1. **Metallic** - Silver/chrome gradients
2. **Winter** - Cool blue/white tones
3. **Fall** - Warm orange/brown tones
4. **Space** - Deep purple/blue cosmos
5. **Dark** - Minimal dark theme
6. **Christmas** - Red and green festive
7. **Halloween** - Orange and purple spooky
8. **Flower Power** - Pink and purple vibrant
9. **RGB** - Full spectrum colors

### Time Display Options
1. **Clock Only** - No digital time display
2. **24-Hour** - Military time format (00:00:00)
3. **12-Hour AM/PM** - Standard format (12:00:00 AM)

## Troubleshooting

### Widget Not Updating
- Check battery optimization settings
- Ensure app has background execution permission
- Try removing and re-adding widget

### Wallpaper Performance Issues
- Reduce animation quality in settings
- Check if device supports hardware acceleration
- Ensure sufficient free memory

### Watch Face Not Appearing
- Verify Wear OS app is updated
- Check watch is properly paired
- Reinstall app on both phone and watch

### Black Screen in Widget/Wallpaper
- WebView requires proper asset loading
- Check `android_asset/public/index.html` exists
- Verify hardware acceleration is supported

## Performance Tips

1. **Battery Optimization:**
   - Wake lock auto-releases after 10 minutes
   - Ambient mode on watch reduces power usage
   - Widget updates can be throttled if needed

2. **Memory Management:**
   - WebView caching enabled
   - Hardware acceleration reduces CPU load
   - Proper lifecycle management prevents leaks

3. **Smooth Animation:**
   - Hardware layer type for WebView
   - 60fps target in interactive mode
   - Throttled updates for minute/hour rings

## Future Enhancements

Potential additions:
- [ ] Wear OS complications (date, battery, etc.)
- [ ] More customization options (size, transparency)
- [ ] Multiple widget sizes (2x2, 4x4, etc.)
- [ ] Sound/haptic feedback options
- [ ] Synchronized themes across devices
- [ ] Custom color picker for themes
- [ ] Animation speed controls
- [ ] Always-on display support

## Additional Optimizations Included

### ProGuard/R8 Configuration
The `proguard-rules.pro` file can be updated to optimize the APK size:
- Keep WebView classes
- Optimize unused Capacitor features
- Strip debug code in release builds

### Gradle Optimizations
- Hardware acceleration enabled in manifest
- Latest Wear library dependencies
- WebKit support library added

### Code Quality
- Proper null checks
- Resource cleanup in lifecycle methods
- Efficient update scheduling
- Thread-safe implementations

## Support

For issues or questions:
- GitHub Issues: https://github.com/ReyMelin/Gradient-Clock-Shell/issues
- Check existing issues before creating new ones
- Include device model and Android version in bug reports

## License

See project LICENSE file for details.

---

**Optimized for Android Studio by GitHub Copilot** ✨
