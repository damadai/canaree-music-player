plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
}

android {
    applyDefaults()
}

dependencies {
    lintChecks(project(":lint"))

    implementation(project(":shared:shared"))

    implementation(Libraries.kotlin)
    implementation(Libraries.coroutines.core)

    implementation(Libraries.dagger.core)
    kapt(Libraries.dagger.kapt)

    implementation(Libraries.x.material)
    implementation(Libraries.x.core)
    implementation(Libraries.x.palette)
    implementation(Libraries.x.constraintLayout)
    implementation(Libraries.x.preference)
    implementation(Libraries.x.fragments)
    implementation(Libraries.x.media)
    implementation(Libraries.x.webview)

    implementation(Libraries.debug.timber)

    testImplementation(Libraries.test.junit)
    testImplementation(Libraries.test.mockito)
    testImplementation(Libraries.test.mockitoKotlin)
    testImplementation(Libraries.test.android)
    testImplementation(Libraries.test.robolectric)
    testImplementation(Libraries.coroutines.test)
}
