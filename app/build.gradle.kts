plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.travel"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.travel"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation( "androidx.core:core-ktx:1.9.0")
    implementation( "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation( "androidx.activity:activity-compose:1.5.1")
    implementation( platform("androidx.compose:compose-bom:2022.10.00"))
    implementation( "androidx.compose.ui:ui")
    implementation( "androidx.compose.ui:ui-graphics")
    implementation( "androidx.compose.ui:ui-tooling-preview")
    implementation( "androidx.compose.material3:material3")
    implementation( "androidx.core:core-splashscreen:1.0.0")
    implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation( "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation( "androidx.profileinstaller:profileinstaller:1.3.0-alpha02")
    implementation( "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    implementation( "androidx.navigation:navigation-compose:2.4.0-alpha08")
    implementation( "com.google.accompanist:accompanist-flowlayout:0.17.0")
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    implementation( "io.coil-kt:coil-compose:1.4.0")
    implementation ("io.github.boguszpawlowski.composecalendar:composecalendar:1.1.0")
    implementation ("io.github.boguszpawlowski.composecalendar:kotlinx-datetime:1.1.0")
    implementation( "com.squareup.retrofit2:converter-gson:2.9.0"           )
    implementation ("com.google.maps.android:maps-compose:2.15.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.firebase:firebase-auth-ktx:21.1.0")
    implementation ("com.google.android.gms:play-services-auth:20.4.1")
    implementation ("androidx.core:core-splashscreen:1.0.0")
    implementation( "com.google.dagger:hilt-android:2.44")
    kapt ("com.google.dagger:hilt-android-compiler:2.44")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01" )
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.google.accompanist:accompanist-pager:0.20.0")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha03")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")
}