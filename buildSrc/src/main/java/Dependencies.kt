object Versions {
    const val kotlin = "1.7.0"
    const val coroutines = "1.6.4"

    const val wearable = "2.9.0"
    const val watchface = "1.2.0-alpha03"
    const val wear = "1.3.0-alpha03"
    const val wear_remote_interactions = "1.0.0"

    const val androidx_core_ktx = "1.9.0"
    const val androidx_app_compat = "1.6.0-rc01"
    const val material = "1.7.0-rc01"
    const val constraintLayout = "2.1.4"
    const val lifecycle_runtime_ktx = "2.5.1"
    const val recycler = "1.2.1"

    const val hilt = "2.44"

    const val junit = "4.13.2"
    const val mockito = "4.8.0"
    const val mockito_kotlin = "4.0.0"
    const val truth = "1.1.3"

    const val gradle_android = "7.3.1"

    const val min_sdk = 26
    const val target_sdk = 33
    const val compile_sdk = 33

    const val version_code_app = 30001
    const val version_code_wear = 30002    //Major + Minor + BugFix + 1 for app and 2 for wear
    const val version_name = "3.0.0"

}

object Deps {
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_runtime_ktx}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recycler}"

    const val wearable = "com.google.android.wearable:wearable:${Versions.wearable}"

    const val wear_remote_interactions = "androidx.wear:wear-remote-interactions:${Versions.wear_remote_interactions}"
    const val wear = "androidx.wear:wear:${Versions.wear}"

    const val watchface = "androidx.wear.watchface:watchface:${Versions.watchface}"
    const val watchface_client = "androidx.wear.watchface:watchface-client:${Versions.watchface}"
    const val watchface_complications_rendering = "androidx.wear.watchface:watchface-complications-rendering:${Versions.watchface}"
    const val watchface_data = "androidx.wear.watchface:watchface-data:${Versions.watchface}"
    const val watchface_editor = "androidx.wear.watchface:watchface-editor:${Versions.watchface}"
    const val watchface_style = "androidx.wear.watchface:watchface-style:${Versions.watchface}"

    const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hilt_android_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hilt_test = "com.google.dagger:hilt-android-testing:${Versions.hilt}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockito_kotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito_kotlin}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito}"

    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val kotlin_test = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"

    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_android}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt_android_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    const val androidx_core_ktx = "androidx.core:core-ktx:${Versions.androidx_core_ktx}"
    const val androidx_app_compat = "androidx.appcompat:appcompat:${Versions.androidx_app_compat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}
