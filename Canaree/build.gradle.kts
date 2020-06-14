import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    repositories {
        google()
        jcenter()
        maven {
            setUrl("https://maven.fabric.io/public")
        }
    }

    dependencies {
        classpath(BuildPlugins.classpath.androidGradlePlugin)
        classpath(BuildPlugins.classpath.kotlinGradlePlugin)
        classpath(BuildPlugins.classpath.gms)
        classpath(BuildPlugins.classpath.fabric)
        classpath(BuildPlugins.classpath.hilt)
    }

}

allprojects {

    repositories {
        google()
        jcenter()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://dl.bintray.com/ijabz/maven") } // jaudiotagger
    }

    gradle.projectsEvaluated { // TODO remove after coroutines became stable
        tasks.withType(KotlinCompile::class).all {
            kotlinOptions.freeCompilerArgs = listOf(
                "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
            kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

}

tasks.register("clean", Delete::class) {
    delete(buildDir)
}