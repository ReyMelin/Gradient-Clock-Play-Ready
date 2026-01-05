# 🚀 Quick Start Guide

Get up and running with your optimized Gradient Clock in 5 minutes!

## ⚡ TL;DR - Fast Track

```bash
# 1. Build the app
cd /workspaces/Gradient-Clock-Shell/android
./gradlew clean assembleDebug

# 2. Install on device
./gradlew installDebug

# 3. Test features
# - Launch app from drawer
# - Add widget: Long-press home screen → Widgets → Gradient Clock
# - Set wallpaper: Settings → Display → Wallpaper → Live Wallpapers
# - Watch face: Auto-syncs to paired Wear OS device
```

Done! 🎉

---

## 📋 Detailed Quick Start

### Step 1: Verify Setup ✅

```bash
# Check you're in the right directory
cd /workspaces/Gradient-Clock-Shell

# Verify Android folder exists
ls android/

# Check Gradle wrapper
ls android/gradlew
```

### Step 2: Build the App 🔨

```bash
# Navigate to Android project
cd android

# Clean previous builds
./gradlew clean

# Build debug APK
./gradlew assembleDebug
```

**Expected output:**
```
BUILD SUCCESSFUL in 30s
```

**APK location:**
`android/app/build/outputs/apk/debug/app-debug.apk`

### Step 3: Install on Device 📱

**Option A: Using Gradle (Easiest)**
```bash
# Connect device via USB (enable USB debugging)
./gradlew installDebug
```

**Option B: Using ADB**
```bash
# Check device is connected
adb devices

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Option C: Manual Install**
1. Copy `app-debug.apk` to your phone
2. Open file and tap "Install"
3. Enable "Unknown sources" if prompted

### Step 4: Test Main App 🎨

1. **Open app drawer** on your phone
2. **Tap "Gradient Clock"**
3. **Try features:**
   - Change theme (dropdown at top)
   - Toggle 24-hour format
   - Toggle AM/PM format
   - Watch the smooth animations

**Expected:** Fullscreen animated gradient clock with smooth transitions

### Step 5: Test Widget 🎯

1. **Long-press** on home screen
2. **Tap "Widgets"**
3. **Find** "Gradient Clock Widget"
4. **Drag** to home screen
5. **Configure:**
   - Select theme (e.g., "Space")
   - Select time format (e.g., "24-Hour")
   - Tap "Confirm"

**Expected:** Widget appears on home screen with selected theme, updates every second

### Step 6: Test Live Wallpaper 🖼️

1. **Go to** Settings → Display → Wallpaper
2. **Select** "Live Wallpapers"
3. **Choose** "Gradient Clock Wallpaper"
4. **Tap** settings icon (⚙️)
5. **Configure:**
   - Select theme (e.g., "Christmas")
   - Select time format
   - Tap "Save"
6. **Set** as wallpaper

**Expected:** Animated gradient clock as your home/lock screen background

### Step 7: Test on Wear OS (Optional) ⌚

**Prerequisites:**
- Wear OS watch paired with phone
- Bluetooth connection active

**Steps:**
1. **Install app** on phone (Step 3)
2. **Wait** for auto-sync to watch (2-5 minutes)
3. **On watch:**
   - Long-press current watch face
   - Swipe to find "Gradient Clock"
   - Tap to activate

**Expected:** Gradient clock appears as watch face with ambient mode support

---

## 🔍 Verification Checklist

After installation, verify:
- [ ] App opens without errors
- [ ] Clock animation is smooth
- [ ] Theme switching works
- [ ] Time displays correctly
- [ ] Widget appears in widget list
- [ ] Widget can be added to home screen
- [ ] Widget updates every second
- [ ] Wallpaper appears in live wallpaper list
- [ ] Wallpaper can be set
- [ ] Watch face syncs to watch (if applicable)

---

## ⚠️ Troubleshooting

### App Won't Build

```bash
# Clear Gradle cache
cd android
./gradlew clean --refresh-dependencies
./gradlew build
```

### APK Won't Install

```bash
# Uninstall old version
adb uninstall com.reymelin.GC

# Reinstall
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Widget Not Appearing

```bash
# Clear app data
adb shell pm clear com.reymelin.GC

# Reinstall
./gradlew installDebug
```

### Wallpaper Not Smooth

- Check if device supports hardware acceleration
- Try selecting "Dark" theme (lighter on resources)
- Ensure device has sufficient free memory

### Watch Face Not Syncing

```bash
# List connected devices
adb devices

# Check if watch is connected
adb -s <watch-id> shell pm list packages | grep gradient

# If not found, manually install
adb -s <watch-id> install app/build/outputs/apk/debug/app-debug.apk
```

---

## 🎯 Next Steps

Once everything works:

### For Development
1. **Read** [ANDROID_OPTIMIZATION_GUIDE.md](ANDROID_OPTIMIZATION_GUIDE.md)
2. **Check** [BUILD_GUIDE.md](BUILD_GUIDE.md) for advanced commands
3. **Review** [OPTIMIZATION_SUMMARY.md](OPTIMIZATION_SUMMARY.md)

### For Testing
1. **Follow** [PRE_RELEASE_CHECKLIST.md](PRE_RELEASE_CHECKLIST.md)
2. **Test** on different devices
3. **Monitor** battery usage
4. **Check** performance

### For Release
1. **Build** release APK:
   ```bash
   ./gradlew assembleRelease
   ```
2. **Sign** with keystore
3. **Test** release build thoroughly
4. **Submit** to Play Store

---

## 📚 Documentation Quick Reference

| File | Purpose | When to Use |
|------|---------|-------------|
| [README.md](README.md) | Overview & features | First read |
| **QUICK_START.md** | This file! | Getting started |
| [ANDROID_OPTIMIZATION_GUIDE.md](ANDROID_OPTIMIZATION_GUIDE.md) | Deep dive | Understanding optimizations |
| [BUILD_GUIDE.md](BUILD_GUIDE.md) | Build commands | Building & deploying |
| [OPTIMIZATION_SUMMARY.md](OPTIMIZATION_SUMMARY.md) | What changed | Reviewing changes |
| [PRE_RELEASE_CHECKLIST.md](PRE_RELEASE_CHECKLIST.md) | Testing guide | Before release |
| [FILE_STRUCTURE.md](FILE_STRUCTURE.md) | File organization | Understanding structure |

---

## 🎓 Learning Path

**Beginner:**
1. Read this Quick Start
2. Build and install app
3. Test all features
4. Read README.md

**Intermediate:**
1. Read ANDROID_OPTIMIZATION_GUIDE.md
2. Review code structure
3. Experiment with themes
4. Try customizations

**Advanced:**
1. Study BUILD_GUIDE.md
2. Review all Java classes
3. Optimize ProGuard rules
4. Build release version

---

## 💡 Pro Tips

### Development
- Use Android Studio for better debugging
- Enable WebView debugging for clock HTML
- Monitor logcat for issues: `adb logcat | grep GradientClock`

### Performance
- Test on older devices (Android 5.0+)
- Monitor memory: `adb shell dumpsys meminfo com.reymelin.GC`
- Check battery: `adb shell dumpsys batterystats | grep GradientClock`

### Distribution
- Test on multiple screen sizes
- Test on different Android versions
- Consider tablet optimization
- Prepare Play Store assets

---

## 🆘 Need Help?

1. **Check documentation** in this folder
2. **Search existing** [GitHub Issues](https://github.com/ReyMelin/Gradient-Clock-Shell/issues)
3. **Create new issue** with:
   - Device model
   - Android version
   - Steps to reproduce
   - Expected vs actual behavior
   - Logs (if applicable)

---

## ✅ You're All Set!

Your Gradient Clock is ready to use with:
- ✅ Home screen widget
- ✅ Live wallpaper
- ✅ Wear OS watch face
- ✅ Performance optimizations

**Enjoy your gradient time experience!** 🌈⏰

---

**Quick Start completed in ~5 minutes** ⚡

[← Back to README](README.md) | [View Optimization Guide →](ANDROID_OPTIMIZATION_GUIDE.md)
