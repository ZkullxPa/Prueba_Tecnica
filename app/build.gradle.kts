plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.prueba_tecnica"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.prueba_tecnica"
        minSdk = 24
        targetSdk = 36
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
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")
    implementation(libs.material3)
    implementation(libs.androidbrowserhelper)
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.socket:socket.io-client:2.0.0")
    implementation ("androidx.room:room-runtime:2.5.0")
    ksp ("androidx.room:room-compiler:2.5.0")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation ("androidx.room:room-ktx:2.5.0")
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:1.2.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.0")
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("androidx.navigation:navigation-compose:2.9.0")
    implementation ("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.compose.material3:material3:1.4.0-alpha15")
    implementation ("androidx.compose.material3:material3-window-size-class:1.1.0")
    implementation ("androidx.compose.foundation:foundation:1.6.8")
    implementation ("com.google.accompanist:accompanist-pager:0.34.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.maps.android:maps-compose:4.3.3")
    testImplementation("junit:junit:4.+")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))
}
configurations.all {
    exclude(group = "org.junit.jupiter")
    exclude(group = "org.junit.platform")
}