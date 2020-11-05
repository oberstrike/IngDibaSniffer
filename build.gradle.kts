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
    id("com.jfrog.bintray") version "1.8.5"
    id("org.jetbrains.dokka") version "1.4.0"
    // We need the javafx plugin since java 11
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
    implementation("org.linguafranca.pwdb:KeePassJava2:2.1.4")

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

val myGroupId = "com.maju.container"
val myArtifactId = "ingDibaSniffer"
val myVersion = "1.0.0"

val dokkaJavadocJar by tasks.creating(Jar::class) {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.get().outputDirectory.get())
    archiveClassifier.set("javadoc")
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("LICENCE.md") {
        into("META-INF")
    }
}


val pomUrl = "https://github.com/oberstrike/IngDibaSniffer"
val pomScmUrl = "https://github.com/oberstrike/IngDibaSniffer"
val pomIssueUrl = "https://github.com/oberstrike/IngDibaSniffer/issues"
val pomDesc = "https://github.com/oberstrike/IngDibaSniffer"

val pomLicenseName = "MIT"
val pomLicenseUrl = "https://opensource.org/licenses/mit-license.php"

val pomDeveloperId = "oberstrike"
val pomDeveloperName = "Markus Jürgens"


publishing {
    publications {
        create<MavenPublication>("testcontainer") {
            groupId = myGroupId
            artifactId = myArtifactId
            version = myVersion
            from(components["java"])
            artifact(sourcesJar)
            artifact(dokkaJavadocJar)

            pom {
                packaging = "jar"
                name.set(project.name)
                description.set("A test library")
                url.set(pomUrl)
                scm {
                    url.set(pomScmUrl)
                }
                issueManagement {
                    url.set(pomIssueUrl)
                }
                licenses {
                    license {
                        name.set(pomLicenseName)
                        url.set(pomLicenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(pomDeveloperId)
                        name.set(pomDeveloperName)
                    }
                }
            }
        }
    }
}

bintray {
    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    publish = !project.version.toString().endsWith("SNAPSHOT")

    setPublications("testcontainer")

    pkg.apply {
        repo = "maven"
        name = myArtifactId
        userOrg = "oberstrike"
        githubRepo = githubRepo
        vcsUrl = pomScmUrl
        description = "Sniffer for Ing-Diba."
        setLabels("kotlin")
        setLicenses("MIT")
        desc = description
        websiteUrl = pomUrl
        issueTrackerUrl = pomIssueUrl
        githubReleaseNotesFile = "README.md"

        version.apply {
            //      name = myArtifactId
            desc = pomDesc
            released = Date().toString()
            vcsTag = "v$myVersion"
        }
    }


}
