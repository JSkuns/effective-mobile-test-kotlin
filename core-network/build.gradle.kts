plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.core_network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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

    kotlin {
        jvmToolchain(11)
    }
}

dependencies {
    // Сеть
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)

    // Корутины
    api(libs.kotlinx.coroutines.android)

    // Gson для парсинга JSON
    implementation(libs.material) // Иногда нужен для типов, но лучше добавить сам gson если нет
    implementation("com.google.code.gson:gson:2.10.1")
}