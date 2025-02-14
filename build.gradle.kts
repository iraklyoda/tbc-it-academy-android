// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.androidx.navigation.safeargs.kotlin) apply false
    id("com.google.dagger.hilt.android") version "2.55" apply false
    alias(libs.plugins.kotlin.kapt) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0" apply false
}