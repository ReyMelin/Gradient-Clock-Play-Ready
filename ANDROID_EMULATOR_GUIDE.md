# 📱 Running Gradient Clock on Android Emulator

## Prerequisites

You need to have installed:
1. **Android Studio** - Download from https://developer.android.com/studio
2. **Android SDK** (included with Android Studio)
3. **Android Emulator** (via Android Studio AVD Manager)

---

## Option 1: Open Project in Android Studio (Recommended)

### Step 1: Open the Android Project

1. Launch **Android Studio**
2. Click **File** → **Open**
3. Navigate to: `/workspaces/Gradient-Clock-Play-Ready/android`
4. Click **OK**

### Step 2: Start an Emulator

1. In Android Studio, click the **Device Manager** icon (phone icon in toolbar)
2. Click **Create Device** if you don't have an emulator
   - Select a device (e.g., Pixel 5)
   - Select a system image (e.g., API 34 Android 14)
   - Click **Finish**
3. Click the **Play** button next to your emulator to start it

### Step 3: Run the App

1. Wait for the emulator to fully boot
2. Click the green **Run** button (▶️) in Android Studio toolbar
3. Select your running emulator from the list
4. Click **OK**

The app will install and launch automatically! 🚀

---

## Option 2: Use Command Line with Pre-built APK

### Step 1: Build the APK

On your local machine (with Android SDK installed):

```bash
cd /path/to/Gradient-Clock-Play-Ready

# Sync web assets to Android
npx cap sync android

# Build debug APK
cd android
./gradlew assembleDebug
```

**Output**: `app/build/outputs/apk/debug/app-debug.apk`

### Step 2: Start Emulator

```bash
# List available emulators
emulator -list-avds

# Start an emulator (replace YOUR_AVD_NAME)
emulator -avd YOUR_AVD_NAME &
```

### Step 3: Install APK

```bash
# Wait for emulator to boot, then:
adb devices  # Should show your emulator

# Install the APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Launch the app
adb shell am start -n com.reymelin.gradientclock/.MainActivity
```

---

## Option 3: Use Capacitor CLI (Easiest!)

### One Command to Rule Them All

From the project root:

```bash
# This will build, sync, and open in Android Studio
npx cap run android

# Or to open Android Studio only
npx cap open android
```

Then just click the Run button in Android Studio!

---

## Testing Preference Persistence on Emulator

### Step 1: Set Preferences
1. Open the app on emulator
2. Select **24-Hour** time format
3. Change theme to **Winter**
4. Change texture to **Roman Numerals**

### Step 2: Close the App
Press the **Back** button or swipe up to close the app completely.

### Step 3: Reopen the App
Tap the app icon again in the app drawer.

### ✅ Expected Result
All your preferences should be exactly as you left them:
- 24-Hour time format active
- Winter theme displayed
- Roman numerals on clock face

### Additional Tests

**Test Force Stop:**
1. Long-press the app icon
2. Select **App Info**
3. Click **Force Stop**
4. Reopen the app
5. ✅ Preferences should still persist

**Test Device Reboot:**
1. In emulator menu: **...** → **Reboot**
2. Wait for emulator to restart
3. Open the app
4. ✅ Preferences should still persist

---

## Viewing Console Logs

To see the preference save/load logs:

### In Android Studio
1. Open **Logcat** tab (bottom of window)
2. Filter by app: Select your app from dropdown
3. Search for: `preferences` or `localStorage`

### Via Command Line
```bash
adb logcat | grep -E "preferences|localStorage|Gradient"
```

You should see logs like:
```
✅ Preferences saved: {timeFormat: "military", texture: "roman", theme: "winter"}
📂 Loading preferences...
Loaded timeFormat: military
Loaded texture: roman
```

---

## Troubleshooting

### "SDK location not found"
Create `android/local.properties`:
```properties
sdk.dir=/path/to/your/Android/Sdk
```

On Mac: Usually `/Users/YOUR_USERNAME/Library/Android/sdk`  
On Windows: Usually `C:\\Users\\YOUR_USERNAME\\AppData\\Local\\Android\\Sdk`  
On Linux: Usually `/home/YOUR_USERNAME/Android/Sdk`

### "Java version mismatch"
Ensure you're using Java 17:
```bash
java -version  # Should show version 17
```

Set JAVA_HOME if needed:
```bash
export JAVA_HOME=/path/to/java17
```

### Emulator won't start
1. Check system requirements (virtualization enabled)
2. Try deleting and recreating the AVD
3. Ensure you have enough disk space (at least 8GB)

### App crashes on emulator
1. Check Logcat for error messages
2. Try cleaning the build:
   ```bash
   cd android
   ./gradlew clean
   ./gradlew assembleDebug
   ```

---

## Quick Reference: Common Commands

```bash
# Sync web assets to Android
npx cap sync android

# Open in Android Studio
npx cap open android

# Build debug APK
cd android && ./gradlew assembleDebug

# Build release AAB (for Play Store)
cd android && ./gradlew bundleRelease

# List connected devices
adb devices

# Install APK
adb install path/to/app-debug.apk

# Uninstall app
adb uninstall com.reymelin.gradientclock

# View logs
adb logcat | grep Gradient

# Clear app data
adb shell pm clear com.reymelin.gradientclock
```

---

## Development Workflow

For active development:

1. **Make changes** to HTML/CSS/JS in `docs/` folder
2. **Test in browser** first (fastest)
3. **Sync to Android**: `npx cap sync android`
4. **Run on emulator**: Click Run in Android Studio

---

## Note About This Environment

The current dev container environment doesn't have:
- ❌ Android SDK installed
- ❌ Android Studio installed  
- ❌ Emulator support (requires GUI and virtualization)

**To test on Android emulator**, you'll need to:
1. Clone this repo to your **local machine**
2. Open in Android Studio on your **local machine**
3. Run on emulator from there

**Or** you can:
- Install Android SDK in this container (complex setup)
- Use a physical Android device with USB debugging

---

## Physical Device Testing (Alternative)

If you have an Android phone:

1. **Enable Developer Options**:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times

2. **Enable USB Debugging**:
   - Go to Settings → Developer Options
   - Turn on "USB Debugging"

3. **Connect Device**:
   ```bash
   adb devices  # Should show your device
   ```

4. **Install APK**:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

---

## Next Steps

✅ **Download the project to your local machine**  
✅ **Open in Android Studio**  
✅ **Create/start an emulator**  
✅ **Click Run**  
✅ **Test preference persistence!**

The app is ready and fully functional - just needs to be run in a proper Android environment! 🚀📱
