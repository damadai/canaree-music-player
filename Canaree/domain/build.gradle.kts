plugins {
    id(BuildPlugins.javaLibrary)
    id(BuildPlugins.kotlin)
    id(BuildPlugins.kotlinKapt)
}

dependencies {
    implementation(project(":shared:shared"))

    implementation(Libraries.kotlin)
    implementation(Libraries.coroutines.core)

    implementation(Libraries.dagger.core)
    kapt(Libraries.dagger.kapt)

    testImplementation(Libraries.test.junit)
    testImplementation(Libraries.test.mockito)
    testImplementation(Libraries.test.mockitoKotlin)
    testImplementation(Libraries.coroutines.test)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
