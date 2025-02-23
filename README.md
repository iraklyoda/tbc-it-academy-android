# Assignment 24 - Travel Social App

<div align="center">

[![Kotlin](https://img.shields.io/badge/Kotlin-v2.1.0-1F425F?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org)  
[![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=flat&logo=android-studio&logoColor=white)](https://developer.android.com/studio)

</div>

## Overview

This Android project is a simple **Travel Social App** that displays a screen with two RecyclerViews: one for stories representing different locations and another for a timeline of posts. The app fetches data from a mock API using **Retrofit** and utilizes **Glide** for image loading.

## Features

- **Visual representation of locations** in the top RecyclerView.
- **Posts timeline** displayed in a separate RecyclerView below the stories.
- **Data fetching** from a mock API using Retrofit.
- **Image loading** using Glide for smooth visual experience.
- **Dagger Hilt** for dependency injection and clean architecture.
- **Repository pattern** implemented for efficient data handling.

## How It Works

1. **Fetch locations and posts** from a mock API.
2. **Display stories** in a horizontal RecyclerView.
3. **Display posts** in a vertical RecyclerView below the stories.
4. **Handle data updates** and ensure smooth user experience with Glide.

## Screenshots

<p align="center">
    <img src="docs/images/screenshot_01.png" width="200" alt="Travel Social App Screenshot">
</p>

## Technologies Used

- **Kotlin**
- **Retrofit**
- **Glide**
- **Dagger Hilt**
- **ViewModel & Repository Pattern**
- **Coroutines**