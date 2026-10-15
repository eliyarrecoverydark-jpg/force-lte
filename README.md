# Engineering R.V — Android Studio starter project

This is an original, minimal Android app with three buttons named METHOD1, METHOD2 and METHOD3. It opens public mobile/network settings where possible and tries a legacy phone-info screen for METHOD3.

## Build
1. Open this folder in Android Studio (recent version).
2. Let Gradle sync.
3. Connect an Android phone and press Run, or use Build > Build APK(s).

## Important limitations
- This is a starter implementation, not a byte-for-byte copy of Force LTE Only.
- Android does not provide ordinary third-party apps with a public API to force the modem into LTE-only mode. Hidden intents may be blocked or absent depending on Android version, manufacturer, carrier, and SIM.
- The exact behavior of the original app's METHOD1/2/3 cannot be duplicated from the Play Store URL alone. This project provides three plausible system-settings routes, not a verified reproduction of the original methods.
