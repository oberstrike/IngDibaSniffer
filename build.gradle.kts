import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.util.*

buildscript {
    repositories {
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
        jcenter {
            setUrl("https://jcenter.bintray.com/")
        }

    }
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:5.2.0")
    }

}

plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.71"
    id("com.github.johnrengelman.shadow") version ("5.2.0")
    application
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    // Add the tornadofx dependency
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.12.0")
    implementation("io.kesselring.sukejura:Sukejura:1.0.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")
    implementation("com.beust:klaxon:5.2")

    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.2")
    runtime("org.junit.jupiter:junit-jupiter-engine:5.3.2")
}
repositories {
    mavenCentral()
    jcenter()
}


// Set the jvmTarget to 1.8 to support inlining
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }


tasks {
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes(mapOf("Main-Class" to "com.main.Main"))
        }
    }
}

application {
    mainClassName = "com.main.MainKt"
}
