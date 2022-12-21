
plugins {
    kotlin("jvm") version "1.8.0-Beta"
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
    implementation("org.mariuszgromada.math:MathParser.org-mXparser:5.1.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "19"
        }
    }
    wrapper {
        version = "7.6"
    }
}