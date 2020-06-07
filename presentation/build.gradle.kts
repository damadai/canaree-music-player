plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroidExtensions)
}

android {
    applyDefaults()
}

dependencies {
    lintChecks(project(":lint"))

    implementation(project(":core"))

    implementation(project(":navigation"))
    implementation(project(":features:feature-entry"))
    implementation(project(":features:feature-presentation-base"))

    // TODO temp
//    implementation(project(":feature-edit"))

    implementation(project(":libraries:lib-analytics"))
    implementation(project(":domain"))
    implementation(project(":libraries:lib-image-loader"))
    implementation(project(":shared-android"))
    implementation(project(":prefs-keys"))
    implementation(project(":libraries:lib-media"))
    implementation(project(":libraries:lib-offline-lyrics"))
    implementation(project(":shared"))
    implementation(project(":libraries:lib-equalizer"))
    implementation(project(":libraries:lib-audio-tagger"))

    implementation(Libraries.kotlin)
    implementation(Libraries.Coroutines.core)

    implementation(Libraries.Dagger.core)
    kapt(Libraries.Dagger.kapt)
    implementation(Libraries.Dagger.android)
    implementation(Libraries.Dagger.androidSupport)
    kapt(Libraries.Dagger.androidKapt)

    implementation(Libraries.X.appcompat)
    implementation(Libraries.X.material)
    implementation(Libraries.X.core)
    implementation(Libraries.X.constraintLayout)
    implementation(Libraries.X.palette)
    implementation(Libraries.X.media)
    implementation(Libraries.X.browser)
    implementation(Libraries.X.preference)
    implementation(Libraries.X.coordinatorLayout)
    implementation(Libraries.X.fragments)

    implementation(Libraries.X.Lifecycle.viewmodel)
    implementation(Libraries.X.Lifecycle.java8)

    implementation(Libraries.UX.lottie)
    implementation(Libraries.UX.tapTargetView)
    implementation(Libraries.UX.dialogs)
    implementation(Libraries.UX.blurKit)
    implementation(Libraries.UX.customTabs)
    implementation(Libraries.UX.glide)

    implementation(Libraries.Utils.scrollHelper)
    implementation(Libraries.Utils.colorDesaturation)
    implementation(Libraries.Utils.lastFmBinding)
    implementation(Libraries.Utils.fuzzy)

    implementation(Libraries.Debug.timber)

    testImplementation(Libraries.Test.junit)
    testImplementation(Libraries.Test.mockito)
    testImplementation(Libraries.Test.mockitoKotlin)
    testImplementation(Libraries.Test.android)
    testImplementation(Libraries.Test.robolectric)
    testImplementation(Libraries.Coroutines.test)
}
