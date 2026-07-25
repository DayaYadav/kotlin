plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.lbg"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.lbg"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // ... your other config (applicationId, minSdk, targetSdk)

        // Force Espresso to bypass reflection and use alternative event injection
        testInstrumentationRunnerArguments["espresso_injection_strategy"] = "androidx.test.espresso.base.LegacyEventInjectionStrategy"

        // Ensure your test runner is specified
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(libs.androidx.compose.ui.test.junit4)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
   // androidTestImplementation(libs.androidx.junit)
   // androidTestImplementation(libs.androidx.espresso.core)
   // androidTestImplementation(platform(libs.androidx.compose.bom))
   // androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dagger Core
    implementation("com.google.dagger:hilt-android:2.57.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("com.google.dagger:hilt-compiler:2.57.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
   // implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.1.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
// Use a valid version

  //  implementation(libs.jetbrains.kotlinx.coroutines.android)
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    //Mock
    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("app.cash.turbine:turbine:0.12.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Android test
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    //  Correct
    //testImplementation("org.mockito:mockito-inline:4.10.0")
// Remove the old mockito-inline line completely and use this:
    testImplementation("org.mockito:mockito-core:5.11.0")



    // Import the Jetpack Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2024.04.01") // Or your desired version
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Use Compose libraries WITHOUT hardcoded version numbers
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Use compatible test libraries
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

    // Core Robolectric Dependency
    testImplementation("org.robolectric:robolectric:4.16.1")

    // Core Testing Frameworks
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.3.0") // Recommended for AndroidX integration
}