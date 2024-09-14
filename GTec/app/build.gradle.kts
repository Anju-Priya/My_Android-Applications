plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.gtechyderabad.gtec"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gtechyderabad.gtec"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

// Define version variables
val coreKtxVersion by extra("1.12.0")
val appCompatVersion by extra("1.6.1")
val materialVersion by extra("1.10.0")  // Updated to latest stable version
val activityKtxVersion by extra("1.8.1")  // Updated to latest stable version
val circleImageViewVersion by extra("3.1.0")
val constraintLayoutVersion by extra("2.1.4")
val recyclerViewVersion by extra("1.3.2")  // Updated to latest stable version
val fragmentKtxVersion by extra("1.6.2")  // Updated to latest stable version

dependencies {
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.activity:activity-ktx:$activityKtxVersion")
    implementation("de.hdodenhof:circleimageview:$circleImageViewVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.recyclerview:recyclerview:$recyclerViewVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentKtxVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

