plugins {
//    alias(libs.plugins.android.application)
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.priyanshparekh.core.ui"
    compileSdk = 35

    defaultConfig {
//        applicationId = "com.priyanshparekh.core.ui"
        minSdk = 24
        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"

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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(project(":core-model"))
    implementation(project(":core-resources"))
    implementation(project(":core-utils"))
}