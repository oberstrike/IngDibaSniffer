import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.cli.jvm.main

buildscript {
    repositories {
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
        jcenter{
            setUrl("https://jcenter.bintray.com/")
        }
    }
    dependencies {
        classpath("org.openjfx:javafx-plugin:0.0.8")
        classpath("com.github.jengelman.gradle.plugins:shadow:5.2.0")
    }
}
apply(plugin = "org.openjfx.javafxplugin")
//apply(plugin = "com.github.johnrengelman.shadow")

plugins {

    kotlin("jvm") version "1.3.71"
    // We need the javafx plugin since java 11
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("com.github.johnrengelman.shadow")  version ("5.2.0")
    application

}
dependencies {
    compile(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-javafx
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.3.6")

    // Add the tornadofx dependency
    compile("no.tornado:tornadofx:1.7.17")
    compile("org.jire.arrowhead:arrowhead:1.3.3")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")
    compile("com.beust:klaxon:5.2")

    compile("org.java-websocket:Java-WebSocket:1.5.1")
}
repositories {
    mavenCentral()
    jcenter()
}

javafx {
    version = "11"
    // Declare the javafx modules we need to use
    modules("javafx.controls")
}


// Set the jvmTarget to 1.8 to support inlining
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }


tasks {
    named<ShadowJar>("shadowJar"){
        manifest {
            attributes(mapOf("Main-Class" to "com.main.Main"))
        }
    }
}

application {
    mainClassName = "com.main.MainKt"
}