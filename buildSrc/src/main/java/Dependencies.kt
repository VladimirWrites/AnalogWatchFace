object Versions {
    const val kotlin: String = "1.3.72"

    const val wearable_support: String = "2.7.0"
    const val wearable_support_ui: String = "1.0.0"
    const val wearable: String = "2.7.0"

    const val junit: String = "4.13"
    const val mockito_core: String = "3.3.3"
    const val mockito_kotlin: String = "2.2.0"
    const val truth: String = "1.0.1"

    const val gradle_android: String = "4.0.0"

    const val jacoco: String = "0.8.5"

    const val min_sdk: Int = 24
    const val target_sdk: Int = 29
    const val compile_sdk: Int = 29
    const val build_tools: String = "29.0.2"

    const val version_code_app: Int = 1500
    const val version_code_wear: Int = 1710
    const val version_name: String = "1.7.1"
}

object Deps {
    const val kotlin_stdlib: String = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val wearable_support: String = "com.google.android.support:wearable:${Versions.wearable_support}"
    const val wearable_support_ui: String = "androidx.wear:wear:${Versions.wearable_support_ui}"
    const val wearable: String = "com.google.android.wearable:wearable:${Versions.wearable}"

    const val junit: String = "junit:junit:${Versions.junit}"
    const val mockito_core: String = "org.mockito:mockito-core:${Versions.mockito_core}"
    const val mockito_kotlin: String = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    const val truth: String = "com.google.truth:truth:${Versions.truth}"
    const val kotlin_test: String = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"

    const val android_gradle_plugin: String = "com.android.tools.build:gradle:${Versions.gradle_android}"
    const val kotlin_gradle_plugin: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}
