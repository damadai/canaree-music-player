@file:Suppress("ClassName")

import BuildPlugins.Versions.buildToolsVersion
import Libraries.Versions.aes_crypto
import Libraries.Versions.android_x_annotations
import Libraries.Versions.android_x_appcompat
import Libraries.Versions.android_x_browser
import Libraries.Versions.android_x_coordinator
import Libraries.Versions.android_x_core
import Libraries.Versions.android_x_fragments
import Libraries.Versions.android_x_material
import Libraries.Versions.android_x_media
import Libraries.Versions.android_x_palette
import Libraries.Versions.android_x_preference
import Libraries.Versions.android_x_recycler
import Libraries.Versions.android_x_test_core
import Libraries.Versions.android_x_webview
import Libraries.Versions.blur_kit
import Libraries.Versions.color_desaturation
import Libraries.Versions.constraint_layout
import Libraries.Versions.custom_tabs
import Libraries.Versions.firebase_analytics
import Libraries.Versions.firebase_core
import Libraries.Versions.firebase_crashlytics
import Libraries.Versions.fuzzywuzzy
import Libraries.Versions.leak_canary
import Libraries.Versions.material_dialogs
import Libraries.Versions.scroll_helper
import Libraries.Versions.sql_content_resolver
import Libraries.Versions.tap_target_view

const val kotlinVersion = "1.3.72"

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "4.0.0"
        const val gms = "4.3.3"
        const val fabric = "1.31.2"
        const val hilt = "${Libraries.Versions.dagger}-alpha"
    }

    object classpath {
        const val androidGradlePlugin = "com.android.tools.build:gradle:$buildToolsVersion"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val gms = "com.google.gms:google-services:${Versions.gms}"
        const val fabric = "io.fabric.tools:gradle:${Versions.fabric}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val javaLibrary = "java-library"
    const val kotlin = "kotlin"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val fabric = "io.fabric"
    const val gms = "com.google.gms.google-services"
    const val hilt = "dagger.hilt.android.plugin"

}

object AndroidSdk {

    const val min = 21
    const val target = 29
    const val compile = target

    const val versionCode = 999_29_4_0_00
    const val versionName = "4.0.0"

}

object Libraries {

    internal object Versions {

        //    core
        internal const val coroutines = "1.3.7"
        internal const val dagger = "2.28"
        internal const val hilt = "2.28-alpha"

        //    android x
        internal const val android_x_core = "1.3.0"
        internal const val android_x_appcompat = "1.2.0-rc01"
        internal const val android_x_media = "1.1.0"
        internal const val android_x_media_2 = "1.0.3"
        internal const val android_x_recycler = "1.1.0"
        internal const val android_x_browser = "1.2.0"
        internal const val android_x_material = "1.2.0-beta01"
        internal const val android_x_preference = "1.1.1"
        internal const val android_x_palette = "1.0.0"
        internal const val android_x_annotations = "1.1.0"
        internal const val android_x_coordinator = "1.1.0"
        internal const val android_x_fragments = "1.2.5"
        internal const val constraint_layout = "2.0.0-beta4"
        internal const val lifecycle = "2.2.0"
        internal const val android_x_webview = "1.2.0"

        //    ui
        internal const val glide = "4.11.0"
        internal const val lottie = "3.4.0"
        internal const val custom_tabs = "3.0.1"
        internal const val material_dialogs = "3.3.0"
        internal const val scroll_helper = "2.0.0-beta04"
        internal const val blur_kit = "1.0.0"
        internal const val color_desaturation = "1.0.2"
        internal const val jaudiotagger = "2.2.5"
        internal const val tap_target_view = "2.0.0"

        //    data
        internal const val room = "2.2.5"
        internal const val sql_content_resolver = "1.2.3"

        //    network
        internal const val ok_http = "4.7.2"
        internal const val retrofit = "2.7.2"
        internal const val gson = "2.8.6"

        //    utils
        internal const val aes_crypto = "1.1.0"
        internal const val fuzzywuzzy = "1.2.0"

        //    debug
        internal const val leak_canary = "2.4"
        internal const val timber = "4.7.1"
        internal const val chucker = "3.2.0"

        //    firebase
        internal const val firebase_core = "17.2.3"
        internal const val firebase_analytics = "17.2.3"
        internal const val firebase_crashlytics = "2.10.1"
//    firebase_perf = "19.0.5"

        // test
        internal const val junit = "4.13"
        internal const val mockito = "3.2.4"
        internal const val mockitoKotlin = "2.2.0"
        internal const val robolectric = "4.3.1"
        internal const val android_x_test_core = "1.2.0"

        // 23.0.0 + {buildToolsVersion}
        internal val lint: String
            get() {
                val toolsVersion = buildToolsVersion
                val major = toolsVersion.take(1).toInt()
                return "${23 + major}${toolsVersion.drop(1)}"
            }

    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"

    object dagger {
        const val core = "com.google.dagger:dagger:${Versions.dagger}"
        const val kapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object x {
        const val core = "androidx.core:core-ktx:$android_x_core"
        const val appcompat = "androidx.appcompat:appcompat:$android_x_appcompat"
        const val media = "androidx.media:media:$android_x_media"
        const val recyclerView = "androidx.recyclerview:recyclerview:$android_x_recycler"
        const val browser = "androidx.browser:browser:$android_x_browser"
        const val material = "com.google.android.material:material:$android_x_material"
        const val preference = "androidx.preference:preference-ktx:$android_x_preference"
        const val palette = "androidx.palette:palette:$android_x_palette"
        const val annotations = "androidx.annotation:annotation:$android_x_annotations"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${constraint_layout}"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:$android_x_coordinator"
        const val fragments = "androidx.fragment:fragment-ktx:$android_x_fragments"
        const val webview = "androidx.webkit:webkit:$android_x_webview"

        object lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
            const val java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
            const val service = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"
            const val process = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
        }

        object room {
            const val core = "androidx.room:room-runtime:${Versions.room}"
            const val coroutines = "androidx.room:room-ktx:${Versions.room}"
            const val kapt = "androidx.room:room-compiler:${Versions.room}"
            const val test = "androidx.room:room-testing:${Versions.room}"
        }

    }

    // testing
    object test {
        const val junit = "junit:junit:${Versions.junit}"
        const val mockito = "org.mockito:mockito-inline:${Versions.mockito}"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
        const val android = "androidx.test:core:$android_x_test_core"
    }

    object firebase {
        const val core = "com.google.firebase:firebase-core:$firebase_core"
        const val analytics = "com.google.firebase:firebase-analytics:$firebase_analytics"
        const val crashlytics = "com.crashlytics.sdk.android:crashlytics:$firebase_crashlytics"
        //            perf       = "com.google.firebase:firebase-perf:${Versions.firebase_perf}"
    }

    object network {
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.ok_http}"
        const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.ok_http}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        // TODO migrate to moshi??
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
    }

    object utils {
        const val sqlContentResolver = "com.github.ologe:android-content-resolver-SQL:$sql_content_resolver"
        const val aesCrypto = "com.github.tozny:java-aes-crypto:$aes_crypto"
        const val fuzzy = "me.xdrop:fuzzywuzzy:$fuzzywuzzy"
        const val scrollHelper = "com.github.ologe:scroll-helper:$scroll_helper"
        const val colorDesaturation = "com.github.ologe:color-desaturation:$color_desaturation"
        const val jaudiotagger = "net.jthink:jaudiotagger:${Versions.jaudiotagger}"
    }

    object debug {
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:$leak_canary"
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
        const val chucker = "com.github.ChuckerTeam.Chucker:library:${Versions.chucker}"
    }

    object ux {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glideKapt = "com.github.bumptech.glide:compiler:${Versions.glide}"
        const val blurKit = "io.alterac.blurkit:blurkit:$blur_kit"
        const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
        const val customTabs = "saschpe.android:customtabs:$custom_tabs"
        const val dialogs = "com.afollestad.material-dialogs:color:$material_dialogs"
        const val tapTargetView = "com.github.ologe:taptargetview:$tap_target_view"
    }

    object lint {
        val core = "com.android.tools.lint:lint-api:${Versions.lint}"
        val checks = "com.android.tools.lint:lint-checks:${Versions.lint}"
    }

}
