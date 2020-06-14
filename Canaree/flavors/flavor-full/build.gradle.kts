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

    implementation(Libraries.kotlin)
    implementation(Libraries.dagger.core)

    api(project(":features:feature-entry"))
    api(project(":features:feature-home"))
    api(project(":features:feature-library"))
    api(project(":features:feature-search"))
    api(project(":features:feature-queue"))
}