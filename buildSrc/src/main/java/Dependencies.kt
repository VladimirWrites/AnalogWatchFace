object Versions {
    const val kotlin = "1.5.30"

    const val wearable_support = "2.8.1"
    const val wearable_support_ui = "1.1.0"
    const val wearable = "2.8.1"

    const val androidXCoreKtx = "1.6.0"
    const val androidXAppCompat = "1.3.1"
    const val material = "1.4.0"
    const val constraintLayout = "2.1.0"

    const val junit = "4.13.2"
    const val mockito = "3.12.4"
    const val mockito_kotlin = "2.2.0"
    const val truth = "1.1.3"

    const val gradle_android = "7.0.1"

    const val jacoco = "0.8.7"

    const val min_sdk = 24
    const val target_sdk = 30
    const val compile_sdk = 30

    const val version_code_app = 21001
    const val version_code_wear = 21002    //Major + Minor + BugFix + 1 for app and 2 for wear
    const val version_name = "2.10.0"
}

object Deps {
    const val wearable_support = "com.google.android.support:wearable:${Versions.wearable_support}"
    const val wearable_support_ui = "androidx.wear:wear:${Versions.wearable_support_ui}"
    const val wearable = "com.google.android.wearable:wearable:${Versions.wearable}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito}"

    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val kotlin_test = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"

    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_android}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val androidXCoreKtx = "androidx.core:core-ktx:${Versions.androidXCoreKtx}"
    const val androidXAppCompat = "androidx.appcompat:appcompat:${Versions.androidXAppCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}
