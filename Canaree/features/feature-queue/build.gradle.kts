plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.hilt)
}

android {
    applyDefaults()
}

dependencies {
    lintChecks(project(":lint"))

    implementation(project(":domain"))

    implementation(project(":navigation"))

    implementation(project(":shared:shared"))

    implementation(Libraries.kotlin)
    implementation(Libraries.coroutines.core)

    implementation(Libraries.dagger.core)
    kapt(Libraries.dagger.kapt)
    implementation(Libraries.dagger.hilt)
    kapt(Libraries.dagger.hiltCompiler)

    implementation(Libraries.x.core)
    implementation(Libraries.x.appcompat)
    implementation(Libraries.x.fragments)
    implementation(Libraries.x.recyclerView)
    implementation(Libraries.x.constraintLayout)
    implementation(Libraries.x.preference)
    implementation(Libraries.x.material)
    implementation(Libraries.x.media)

    implementation(Libraries.x.lifecycle.java8)

    implementation(Libraries.debug.timber)

    testImplementation(Libraries.test.junit)
    testImplementation(Libraries.test.mockito)
    testImplementation(Libraries.test.mockitoKotlin)
    testImplementation(Libraries.test.android)
    testImplementation(Libraries.test.robolectric)
    testImplementation(Libraries.coroutines.test)
}
