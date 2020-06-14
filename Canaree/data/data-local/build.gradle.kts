plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
}

android {

    applyDefaults()

    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                )
            }
        }

        configField("LAST_FM_KEY" to localProperties.lastFmKey)
        configField("LAST_FM_SECRET" to localProperties.lastFmSecret)

    }

    sourceSets {
        getByName("test").assets.srcDir("$projectDir/schemas")
        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }

}

dependencies {
    lintChecks(project(":lint"))

    implementation(project(":domain"))
    implementation(project(":shared:shared"))

    implementation(Libraries.kotlin)
    implementation(Libraries.coroutines.core)

    implementation(Libraries.dagger.core)
    kapt(Libraries.dagger.kapt)

    implementation(Libraries.x.core)
    implementation(Libraries.x.preference)

    implementation(Libraries.x.room.core)
    implementation(Libraries.x.room.coroutines)
    kapt(Libraries.x.room.kapt)

    implementation(Libraries.utils.sqlContentResolver)

    implementation(Libraries.debug.timber)

    testImplementation(Libraries.test.junit)
    testImplementation(Libraries.test.mockito)
    testImplementation(Libraries.test.mockitoKotlin)
    testImplementation(Libraries.test.android)
    testImplementation(Libraries.test.robolectric)
    testImplementation(Libraries.coroutines.test)

}
