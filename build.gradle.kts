buildscript {
    val sqlDelightVersion: String by project
    val kotlinVersion = "1.5.31"
    val gradleVersion = "7.0.3"

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://dl.bintray.com/kodein-framework/kodein-dev")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}