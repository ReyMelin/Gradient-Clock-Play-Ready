# Gradient Clock Shell

<img src="https://img.shields.io/badge/Android-Optimized-green" alt="Android Optimized" />
<img src="https://img.shields.io/badge/Wear%20OS-Supported-blue" alt="Wear OS" />
<img src="https://img.shields.io/badge/Widget-Ready-orange" alt="Widget Ready" />

An animated gradient clock that displays time through color. Now optimized for Android Studio with support for home screen widgets, live wallpapers, and Wear OS smartwatches!

## ✨ Features

### 🎨 Multiple Display Modes
- **Full App** - Interactive gradient clock with themes and controls
- **Home Screen Widget** - Add to your home screen for quick time viewing
- **Live Wallpaper** - Set as your phone's animated background
- **Wear OS Watch Face** - Use on your smartwatch

### 🌈 9 Beautiful Themes
1. **Metallic** - Silver/chrome gradients
2. **Winter** - Cool blue/white tones
3. **Fall** - Warm orange/brown tones
4. **Space** - Deep purple/blue cosmos
5. **Dark** - Minimal dark theme
6. **Christmas** - Red and green festive
7. **Halloween** - Orange and purple spooky
8. **Flower Power** - Pink and purple vibrant
9. **RGB** - Full spectrum colors

### ⚡ Performance Optimizations
- Hardware-accelerated rendering
- 60fps smooth animations
- Battery-efficient wake lock management
- Optimized WebView configuration
- Immersive fullscreen mode

### 🎯 Time Display Options
- Clock only (no digital display)
- 24-hour format
- 12-hour AM/PM format

## 📱 Installation

### Building from Source
```bash
# Clone the repository
git clone https://github.com/ReyMelin/Gradient-Clock-Shell.git
cd Gradient-Clock-Shell

# Install dependencies
npm install

# Build for Android
npm run build
cd android
./gradlew assembleDebug
```

### Installing on Device
```bash
# Install the APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 🚀 Usage

### Main App
1. Launch "Gradient Clock" from your app drawer
2. Select theme from dropdown
3. Choose time format (24h/AM-PM) or keep clock-only
4. Enjoy the mesmerizing animated gradient!

### Widget Setup
1. Long-press on home screen
2. Tap "Widgets"
3. Find "Gradient Clock Widget"
4. Drag to desired location
5. Configure theme and time format
6. Tap outside to confirm

### Live Wallpaper Setup
1. Go to Settings → Display → Wallpaper
2. Select "Live Wallpapers"
3. Choose "Gradient Clock Wallpaper"
4. Tap settings icon to customize
5. Set as home screen or lock screen wallpaper

### Wear OS Watch Face
1. Install app on phone (automatically syncs to watch)
2. On watch: Long-press current watch face
3. Swipe to find "Gradient Clock"
4. Tap to activate

## 🛠️ Technical Details

### Built With
- **Capacitor** - Native wrapper for web content
- **HTML5/CSS3** - Gradient animations and UI
- **JavaScript** - Clock logic and theming
- **Android SDK** - Native widget, wallpaper, and watch face implementations

### Architecture
- `MainActivity.java` - Optimized main activity with WebView enhancements
- `GradientClockWidget.java` - Home screen widget provider
- `GradientClockWallpaperService.java` - Live wallpaper service
- `GradientClockWatchFaceService.java` - Wear OS watch face
- Configuration activities for widget and wallpaper customization

### Requirements
- **Android 5.0+** (API 21+)
- **Wear OS 2.0+** for watch face support
- Hardware acceleration support recommended

## 📚 Documentation

For detailed information about Android Studio optimizations, see:
- [Android Optimization Guide](ANDROID_OPTIMIZATION_GUIDE.md)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 🐛 Known Issues

See [GitHub Issues](https://github.com/ReyMelin/Gradient-Clock-Shell/issues) for current bugs and feature requests.

## 📄 License

This project is licensed under the ISC License - see the LICENSE file for details.

## 👤 Author

**ReyMelin**
- GitHub: [@ReyMelin](https://github.com/ReyMelin)

## 🙏 Acknowledgments

- Inspired by the beauty of gradient colors
- Thanks to the Capacitor team for the excellent framework
- Wear OS documentation and examples

---

**Made with ❤️ and lots of gradients** 🌈
