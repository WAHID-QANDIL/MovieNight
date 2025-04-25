// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
    alias(libs.plugins.androidx.room) version "2.7.0" apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}