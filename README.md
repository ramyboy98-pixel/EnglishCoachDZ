# English Coach DZ

English Coach DZ is an Android-native English learning app for Arabic speakers, with an English-only interface.

## First MVP

This first version includes:

- English-only UI.
- IDARA DZ-inspired clean design: calm blue, white cards, soft gray background, rounded corners, and light shadows.
- Home screen.
- Placement test placeholder.
- Daily lesson screen.
- Practice screen.
- Simple rule-based correction.
- Basic progress screen.
- GitHub Actions workflow to build APK.

## Build APK with GitHub Actions

1. Create a GitHub repository named `EnglishCoachDZ`.
2. Upload all project files.
3. Open the `Actions` tab.
4. Run `Build Android APK`.
5. Download the artifact named `EnglishCoachDZ-debug-apk`.
6. Install `app-debug.apk` on your Android phone.

## Main code path

```text
app/src/main/java/com/englishcoachdz/app/MainActivity.kt
```

## Next Development Steps

- Add Room database.
- Save user progress permanently.
- Add full placement test scoring.
- Add lesson engine from A0 to C2.
- Add mistake review system.
- Add AI correction later through an API.
