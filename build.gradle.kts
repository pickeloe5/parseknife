import java.net.URL

val pkArtifact = Pair("com.github.pickeloe5", "0.0.1")

group = pkArtifact.first
version = pkArtifact.second

plugins {
    `java-library`
    kotlin("jvm") version "1.6.21"
    `maven-publish`
    id("org.jetbrains.dokka") version "1.6.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = pkArtifact.first
            artifactId = "parseknife"
            version = pkArtifact.second

            from(components["java"])

            pom {
                name.set("ParseKnife")
                description.set("Extensible Kotlin parsing library")
                url.set("https://parseknife.nickmatt.dev")
                licenses { license {
                    name.set("MIT License")
                    url.set("https://www.mit.edu/~amini/LICENSE.md")
                } }
                developers { developer {
                    id.set("pickeloe5")
                    name.set("Nick Mattiacci")
                    email.set("aboutthepickle@gmail.com")
                } }
                scm {
                    url.set("https://github.com/pickeloe5/parseknife")
                    connection.set("scm:git:git://github.com:pickeloe5/parseknife.git")
                    developerConnection.set("scm:git:git@github.com:kevinsawicki/github-maven-example.git")
                }
            }
        }
    }
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