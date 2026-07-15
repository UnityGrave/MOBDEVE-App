# Senate Election Info Application

An **Android app** that helps voters get informed before an election — browse candidate profiles, scan candidate QR codes, and build a sample ballot. Built natively in **Kotlin** with an **MVVM** architecture and a **Firebase** backend.

## Overview

The app centralises Philippine senate-election information into a mobile experience: users register/log in, view senator profiles, jump to a candidate via QR scanning, and assemble a personal sample ballot to plan their vote.

## Features

- **Authentication** — register and log in (Firebase Authentication)
- **Candidate profiles** — structured senator information (`HomeFragment`, `ProfileFragment`)
- **QR scanner** — scan candidate QR codes for quick profile access (`ScannerFragment`, ZXing; uses the camera)
- **Sample ballot** — select candidates to build a mock ballot (`BallotFragment`)
- **Profile management** — edit your profile with an avatar (`EditProfileFragment`, CircleImageView)
- **Settings & notifications** — app settings and push-style notifications (`POST_NOTIFICATIONS`)
- **Splash flow** with an MVVM splash view-model

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | **Kotlin** |
| Architecture | MVVM (AndroidX `ViewModel` + Lifecycle) |
| Backend | **Firebase Authentication** + **Cloud Firestore** |
| QR scanning | ZXing (`journeyapps:zxing-android-embedded`) |
| UI | Material Components, ConstraintLayout, CircleImageView |
| IDE | Android Studio |

## Architecture

```
app/src/main/java/…/
  MainActivity.kt, SplashActivity.kt
  SplashViewModel.kt, SplashModelViewFactory.kt   # MVVM
  HomeFragment · ProfileFragment · EditProfileFragment
  ScannerFragment · BallotFragment · SettingsFragment
  AuthValidator.kt                                # input/auth validation
  Senator.kt · User.kt                            # data models
```

Permissions used: `CAMERA` (QR scanning), `POST_NOTIFICATIONS`.

## Getting Started

### Prerequisites
- Android Studio (latest) + Android SDK
- A Firebase project (Authentication + Firestore enabled)

### Setup
```bash
git clone https://github.com/UnityGrave/MOBDEVE-App.git
```
1. Open the project in **Android Studio** and let Gradle sync.
2. Add your Firebase **`google-services.json`** to `app/`.
3. Enable **Authentication** and **Cloud Firestore** in the Firebase console.
4. Run on an emulator or device (▶).

## Developed At

Mobile Device Programming (MOBDEVE) major course output at **De La Salle University**.

## License

MIT — see [`LICENSE`](LICENSE). 
