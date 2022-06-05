import java.net.URL

group = "dev.nickmatt"
version = "0.0.1"

plugins {
    kotlin("jvm") version "1.6.21"
    `java-library`
    id("org.jetbrains.dokka") version "1.6.21"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

repositories {
    mavenCentral()
}

// Opt-in required for named capture-groups on regular expressions
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("ParseKnife")
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL(
                    "https://github.com/pickeloe5/parseknife/tree/master" +
                            "/src/main/kotlin"
                ))
                remoteLineSuffix.set("#L")
            }
        }
    }
}