plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")



}

android {
    namespace = "com.example.laundry"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.laundry"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
    implementation(libs.firebase.auth.ktx)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)


    // Correct Credential Manager dependencies
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth.v150rc01)
    implementation(libs.androidx.credentials.v150rc01)
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.credentials.v120alpha04)
    implementation(libs.googleid)

   // Ensure this version or newer is used
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Required for viewModel()
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation(libs.material3)
    implementation(libs.play.services.auth.v2070)
}