object Versions {
    const val kotlin = "1.3.70"

    const val wearable_support = "2.5.0"
    const val wearable_support_ui = "1.0.0"
    const val wearable = "2.5.0"

    const val junit = "4.13"
    const val mockito_core = "3.3.0"
    const val mockito_kotlin = "2.2.0"
    const val truth = "1.0.1"

    const val gradle_android = "3.6.1"

    const val jacoco = "0.8.5"

    const val min_sdk = 24
    const val target_sdk = 29
    const val compile_sdk = 29
    const val build_tools = "29.0.2"

    const val version_code_app = 1500
    const val version_code_wear = 1600
    const val version_name = "1.6.0"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val wearable_support = "com.google.android.support:wearable:${Versions.wearable_support}"
    const val wearable_support_ui = "androidx.wear:wear:${Versions.wearable_support_ui}"
    const val wearable = "com.google.android.wearable:wearable:${Versions.wearable}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val kotlin_test = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"

    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_android}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}
