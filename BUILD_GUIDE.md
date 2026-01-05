# Quick Build & Deploy Guide

## Development Build

### Quick Build
```bash
cd android
./gradlew clean assembleDebug
```

### Install on Connected Device
```bash
./gradlew installDebug
```

### Build and Install (One Command)
```bash
./gradlew clean installDebug
```

## Release Build

### 1. Generate Release APK
```bash
./gradlew clean assembleRelease
```

Output: `android/app/build/outputs/apk/release/app-release-unsigned.apk`

### 2. Sign APK (Required for Distribution)

#### Create Keystore (First Time Only)
```bash
keytool -genkey -v -keystore gradient-clock.keystore \
  -alias gradient-clock -keyalg RSA -keysize 2048 -validity 10000
```

#### Sign APK
```bash
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore gradient-clock.keystore \
  app/build/outputs/apk/release/app-release-unsigned.apk gradient-clock
```

#### Align APK (Optimization)
```bash
zipalign -v 4 app/build/outputs/apk/release/app-release-unsigned.apk \
  app/build/outputs/apk/release/gradient-clock-release.apk
```

### 3. Or Use Android Studio
1. Build → Generate Signed Bundle / APK
2. Select APK
3. Choose keystore (create new or use existing)
4. Select release build variant
5. Finish

## Testing Different Components

### Test Main App
```bash
adb shell am start -n com.reymelin.GC/.MainActivity
```

### Test Widget
1. Install app: `./gradlew installDebug`
2. On device: Long-press home screen → Widgets → Gradient Clock

### Test Live Wallpaper
1. Install app: `./gradlew installDebug`
2. On device: Settings → Display → Wallpaper → Live Wallpapers → Gradient Clock

### Test on Wear OS
```bash
# List devices
adb devices

# Install on specific device (watch)
adb -s <watch-device-id> install app/build/outputs/apk/debug/app-debug.apk

# Or use Android Studio: Run → Select Wear OS emulator/device
```

## Debugging

### View Logs
```bash
# All logs
adb logcat

# Filter by app
adb logcat | grep "GradientClock"

# Clear logs first
adb logcat -c && adb logcat | grep "GradientClock"
```

### Debug WebView Content
```bash
# Enable WebView debugging in MainActivity if not already enabled
# Then use Chrome DevTools:
# Open chrome://inspect in Chrome
# Select your device/app
```

### Check Permissions
```bash
adb shell dumpsys package com.reymelin.GC | grep permission
```

## Performance Testing

### Monitor Memory
```bash
adb shell dumpsys meminfo com.reymelin.GC
```

### Monitor CPU
```bash
adb shell top | grep com.reymelin.GC
```

### Monitor Battery
```bash
adb shell dumpsys batterystats | grep com.reymelin.GC
```

## Clean Build

### Full Clean
```bash
./gradlew clean
rm -rf .gradle
rm -rf app/build
rm -rf build
```

### Rebuild
```bash
./gradlew clean build
```

## Useful ADB Commands

### Install/Uninstall
```bash
# Install
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Uninstall
adb uninstall com.reymelin.GC

# Reinstall (uninstall + install)
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Clear App Data
```bash
adb shell pm clear com.reymelin.GC
```

### Take Screenshot
```bash
adb shell screencap -p /sdcard/screen.png
adb pull /sdcard/screen.png
```

### Record Screen
```bash
adb shell screenrecord /sdcard/demo.mp4
# Stop with Ctrl+C
adb pull /sdcard/demo.mp4
```

## Android Studio Tips

### Sync Gradle Files
- File → Sync Project with Gradle Files

### Invalidate Caches
- File → Invalidate Caches / Restart

### Build Variants
- View → Tool Windows → Build Variants
- Switch between debug/release

### Analyze APK
- Build → Analyze APK
- Select built APK to see size breakdown

## Capacitor Commands

### Copy Web Assets
```bash
npx cap copy android
```

### Sync Project
```bash
npx cap sync android
```

### Update Capacitor
```bash
npm install @capacitor/android@latest
npx cap sync android
```

## Troubleshooting

### Gradle Sync Failed
```bash
cd android
./gradlew clean --refresh-dependencies
```

### WebView Not Loading
```bash
# Clear app data
adb shell pm clear com.reymelin.GC
# Reinstall
./gradlew installDebug
```

### Widget Not Appearing
```bash
# Check manifest
cat app/src/main/AndroidManifest.xml | grep widget
# Reinstall
adb uninstall com.reymelin.GC
./gradlew installDebug
```

### Wear OS Not Syncing
```bash
# Check paired devices
adb devices
# Manually install on watch
adb -s <watch-id> install app/build/outputs/apk/debug/app-debug.apk
```

## Quick Reference

| Task | Command |
|------|---------|
| Debug Build | `./gradlew assembleDebug` |
| Release Build | `./gradlew assembleRelease` |
| Install | `./gradlew installDebug` |
| Clean | `./gradlew clean` |
| View Logs | `adb logcat \| grep GradientClock` |
| Uninstall | `adb uninstall com.reymelin.GC` |
| Clear Data | `adb shell pm clear com.reymelin.GC` |

---

**Pro Tip:** Use Android Studio's run configurations for quick testing!
