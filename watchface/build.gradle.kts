plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.trianguloy.watchface"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.trianguloy.watchface"
        minSdk = 33
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    enableKotlin = false
}

dependencies {
}