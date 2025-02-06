# Security Pin App (Assignment-22)

<div align="center">  

[![Kotlin](https://img.shields.io/badge/Kotlin-v2.1.0-1F425F?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org)  
[![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=flat&logo=android-studio&logoColor=white)](https://developer.android.com/studio)

</div>  

## Overview

A simple Android app that allows users to input a 4-digit PIN using buttons 0-9, a return button, and a fingerprint option. The hardcoded PIN is "0934." The app provides feedback through Toast messages, indicating success or failure based on user input.

## Note
This project is for practice only. Currently, the app does not implement Dependency Injection (DI).

## Features

- **User Input**: Enter PIN using buttons 0-9.
- **Feedback System**: Toast messages indicate success or failure of PIN entry.
- **User Suspension**: After 5 incorrect attempts, the user is temporarily suspended for 30 or 60 seconds, depending on the chosen duration.

## Libraries Used

- **Preferences DataStore** – For storing user suspension data and ensuring persistence across app restarts.

## How It Works

1. **User enters PIN** → Inputs 0-9 to form a 4-digit PIN.
2. **Validation** → If the entered PIN is "0934," a "Success" Toast message appears.
3. **Feedback on attempts** → If incorrect, a message is shown based on the number of attempts.
4. **Suspension** → After 5 incorrect attempts, the user is suspended for either 30 or 60 seconds.

## Screenshots

<p align="center">
    <img src="docs/images/screenshot_01.png" width="250" alt="PIN Input Screen">
    <img src="docs/images/screenshot_02.png" width="250" alt="Suspension message">
</p>  

## Notes

- The app utilizes Preferences DataStore for storing suspension data, ensuring it persists across app restarts.
- User experience is enhanced with immediate feedback through Toast messages.
