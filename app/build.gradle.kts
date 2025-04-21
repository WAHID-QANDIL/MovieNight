import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.androidx.room)

}

android {
    namespace = "org.wahid.movienight"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.wahid.movienight"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val secrets = Properties().apply {
        File(rootDir, "secretes.properties")
            .takeIf { it.exists() }
            ?.inputStream()?.use { load(it) }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_URL",secrets.getProperty("API-URL",""))
            buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/w500/\"")
            buildConfigField(
                "String",
                "API_KEY",
                secrets.getProperty("TMDB-API-KEY","")
            )
        }
        debug {
            buildConfigField("String", "API_URL",secrets.getProperty("API-URL",""))
            buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/w500/\"")
            buildConfigField(
                "String",
                "API_KEY",
                secrets.getProperty("TMDB-API-KEY","")
            )
        }


    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    @Suppress("UnstableApiUsage")
    composeOptions {
        kotlinCompilerExtensionVersion =  "1.1.1"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization)

    //Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //Room Pagination Support
    implementation(libs.androidx.room.paging)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.retrofit.converter.kotinx.serialization)

    //Material3
    implementation(libs.material3)
    implementation(libs.androidx.material3.window.size.class1)
    implementation(libs.androidx.material3.adaptive.navigation.suite)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)






}