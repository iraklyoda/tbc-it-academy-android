# User Data Storage (Assignment-21)

<div align="center">  

[![Kotlin](https://img.shields.io/badge/Kotlin-v2.1.0-1F425F?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org)  
[![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=flat&logo=android-studio&logoColor=white)](https://developer.android.com/studio)

</div>  

## Overview

A simple Android app where users enter their **first name, last name, and email**, and the data is stored using **Proto DataStore** for persistence. The saved data is then displayed on the screen.

## Note
Currently App is not using DI, so context is passed in dataRepository trough Application class.
This project is for practice only

## Features

- **User Input**: Enter first name, last name, and email.
- **Data Persistence**: Uses Proto DataStore for saving and retrieving user data.

## Libraries Used

- **Proto DataStore** – Efficient and structured data storage.
- **Kotlin Coroutines** – For asynchronous data operations.

## How It Works

1. **User enters data** → First name, last name, and email.
2. **Data is saved** → Stored securely in Proto DataStore.
3. **Displayed on screen** → User data is shown when "Read" is clicked

## Screenshots

<p align="center">
    <img src="docs/images/screenshot_01.png" width="250" alt="User Input Screen">
    <img src="docs/images/screenshot_02.png" width="250" alt="User Data Display">
</p>  

## Notes

- Proto DataStore ensures structured and efficient data storage.
- Data persists even after app restarts.  