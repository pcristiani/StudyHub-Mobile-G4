/**
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
plugins {
   alias(libs.plugins.android.application)
   alias(libs.plugins.kotlin.android)
   id("com.google.gms.google-services")

}

android {
   compileSdk = 34
   namespace = "com.example.compose.studyhub"
   
   defaultConfig {
      applicationId = "com.example.compose.studyhub"
      minSdk = libs.versions.minSdk.get().toInt()
      targetSdk = libs.versions.targetSdk.get().toInt()
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
         proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      }
      create("customDebugType") {
         isDebuggable = true
      }
   }
   compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
   }
   kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
   }
   buildFeatures {
      compose = true
   }
   composeOptions {
      kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
   }
}

dependencies {
   implementation(libs.window)
   val composeBom = platform(libs.androidx.compose.bom)
   implementation(composeBom)
   androidTestImplementation(composeBom)
   
   implementation(libs.kotlin.stdlib)
   implementation(libs.kotlinx.coroutines.android)
   
   implementation(libs.androidx.core.ktx)
   implementation(libs.androidx.appcompat)
   
   implementation(libs.androidx.navigation.compose)
   
   implementation(libs.androidx.lifecycle.viewModelCompose)
   
   implementation(libs.androidx.activity.compose)
   
   implementation(libs.google.android.material)
   implementation(libs.androidx.compose.ui)
   implementation(libs.androidx.compose.ui.util)
   implementation(libs.androidx.compose.foundation.layout)
   implementation(libs.androidx.compose.material)
   
   implementation(libs.androidx.compose.material3)
   implementation(libs.androidx.compose.material.iconsExtended)
   
   implementation(libs.androidx.compose.ui.tooling.preview)
   implementation(libs.androidx.compose.runtime)
   debugImplementation(libs.androidx.compose.ui.tooling)
   
   implementation(libs.accompanist.permissions)
   implementation(libs.coil.kt.compose)
   
   testImplementation(libs.junit)
   testImplementation(libs.androidx.test.core)
   testImplementation(libs.androidx.test.ext.junit)
   testImplementation(libs.androidx.test.ext.truth)
   testImplementation(libs.robolectric)

   implementation ("com.squareup.retrofit2:retrofit:2.9.0")
   implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
   implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
   implementation ("com.auth0.android:jwtdecode:2.0.2")
   implementation ("androidx.navigation:navigation-compose:2.4.0-alpha10")

   implementation (platform("com.google.firebase:firebase-bom:33.1.0"))
   implementation("com.google.firebase:firebase-analytics")
   implementation("com.google.firebase:firebase-messaging")
   implementation("io.github.afreakyelf:Pdf-Viewer:2.1.1")




   // include for Common module

   implementation("org.jetbrains.kotlinx:kotlinx-html:0.11.0")

}
