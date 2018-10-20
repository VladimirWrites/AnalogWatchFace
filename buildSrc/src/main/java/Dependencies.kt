/*
 * Copyright 2017 Vladimir Jovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

object Versions {
    val kotlin = "1.2.71"

    val wearable_support = "2.3.0"
    val wearable = "2.3.0"

    val junit = "4.12"
    val mockito_core = "2.23.0"
    val mockito_kotlin = "2.0.0-RC3"
    val truth = "0.42"

    val gradle_android = "3.2.1"

    val jacoco = "0.8.2"

    val min_sdk = 24
    val target_sdk = 28
    val compile_sdk = 28
    val build_tools = "28.0.3"

    val version_code = 100
    val version_name = "1.0.0"
}

object Deps {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    val wearable_support = "com.google.android.support:wearable:${Versions.wearable_support}"
    val wearable = "com.google.android.wearable:wearable:${Versions.wearable}"

    val junit = "junit:junit:${Versions.junit}"
    val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"
    val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    val truth = "com.google.truth:truth:${Versions.truth}"
    val kotlin_test = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"

    val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_android}"
    val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}
