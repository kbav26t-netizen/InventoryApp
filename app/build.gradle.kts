plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

android {
    namespace = "com.inventory.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.inventory.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 10
        versionName = "1.9"
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

// Правильная нумерация версий для каждого ABI
ext.abiCodes = ["armeabi-v7a": 1, "arm64-v8a": 2, "x86": 3, "x86_64": 4]

android.applicationVariants.all { variant ->
    variant.outputs.each { output ->
        def abiName = output.getFilter(com.android.build.OutputFile.ABI)
        if (abiName != null) {
            output.versionCodeOverride = project.ext.abiCodes.get(abiName, 0) * 1000 + android.defaultConfig.versionCode
        }
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.8.0")
    
    implementation("androidx.navigation:navigation-compose:2.7.4")
    
    val cameraxVersion = "1.3.0"
    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")
    
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")
    implementation("com.google.mediapipe:tasks-vision:0.10.14")
    
    val roomVersion = "2.6.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
