plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.fabric)
    id(BuildPlugins.hilt)
}

android {
    applyDefaults()

    defaultConfig {
        applicationId = "dev.olog.msc"

        configField("AES_PASSWORD" to localProperties.aesPassword)
        configField("LAST_FM_KEY" to localProperties.lastFmKey)
        configField("LAST_FM_SECRET" to localProperties.lastFmSecret)
    }

    bundle {
        language.enableSplit = true
        density.enableSplit = true
        abi.enableSplit = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            multiDexEnabled = true
        }
    }

    flavorDimensions("scope")
    productFlavors {
        featureFlavors.forEach { (flavor, _) ->
            register(flavor) {
                dimension = "scope"
                if (flavor != "full"){
                    versionNameSuffix = ".$flavor"
                }
            }
        }
    }
    featureFlavors.forEach { (flavor, config) ->
        project.dependencies.add("${flavor}Implementation", project(config.entryModule))
    }

}

dependencies {
    lintChecks(project(":lint"))

    implementation(project(":domain"))

    implementation(project(":data:data-local"))

    // feature
    implementation(project(":navigation"))

    implementation(project(":shared:shared"))

    implementation(Libraries.kotlin)
    implementation(Libraries.coroutines.core)

    implementation(Libraries.dagger.core)
    kapt(Libraries.dagger.kapt)
    implementation(Libraries.dagger.hilt)
    kapt(Libraries.dagger.hiltCompiler)

    debugImplementation(Libraries.debug.leakCanary)
    implementation(Libraries.debug.timber)

    implementation(Libraries.firebase.analytics)
    implementation(Libraries.firebase.crashlytics)

    testImplementation(Libraries.test.junit)
    testImplementation(Libraries.test.mockito)
    testImplementation(Libraries.test.mockitoKotlin)
    testImplementation(Libraries.test.android)
    testImplementation(Libraries.test.robolectric)
    testImplementation(Libraries.coroutines.test)
}

// leave at bottom
apply(plugin = BuildPlugins.gms)
