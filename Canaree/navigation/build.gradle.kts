plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
}

android {
    applyDefaults()
}

dependencies {
    lintChecks(project(":lint"))

    implementation(project(":domain"))

    implementation(Libraries.kotlin)
    implementation(Libraries.dagger.core)

    implementation(Libraries.x.core)
    implementation(Libraries.x.fragments)
    implementation(Libraries.x.material)
    implementation(Libraries.x.preference)

    implementation(Libraries.debug.timber)

    testImplementation(Libraries.test.junit)
    testImplementation(Libraries.test.mockito)
    testImplementation(Libraries.test.mockitoKotlin)
    testImplementation(Libraries.test.android)
    testImplementation(Libraries.test.robolectric)
    testImplementation(Libraries.coroutines.test)
}
