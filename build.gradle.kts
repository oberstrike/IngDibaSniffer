buildscript {
    repositories {
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.openjfx:javafx-plugin:0.0.8")
    }
}
apply(plugin = "org.openjfx.javafxplugin")

plugins {
    application
    kotlin("jvm") version "1.3.71"
    // We need the javafx plugin since java 11
    id("org.openjfx.javafxplugin") version "0.0.8"

}
dependencies {
    compile(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-javafx
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.3.6")

    // Add the tornadofx dependency
    compile("no.tornado:tornadofx:1.7.17")
    compile("org.jire.arrowhead:arrowhead:1.3.3")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")
}
repositories {
    mavenCentral()
}

javafx {
    // Declare the javafx modules we need to use
    modules("javafx.controls")
}

application {

    // Declare the main class for the application plugin
    mainClassName = "com.main.MainKt"
}

// Set the jvmTarget to 1.8 to support inlining
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

