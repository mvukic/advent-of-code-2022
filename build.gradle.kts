import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "me.matija"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("MainKt")
}


dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "18"
        }
    }
    wrapper {
        version = "7.6"
    }
}