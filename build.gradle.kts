import java.net.URL

plugins {
    kotlin("js") version "1.6.21"
}

kotlin {
    js() {
        nodejs {}
        browser {
//            webpackTask {
//                output.libraryTarget = "window"
//            }
        }
    }
}
//tasks.named<org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile>("compileKotlinJs").configure {
//    kotlinOptions.moduleKind = "plain"
//}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js:1.6.21")
}

// Opt-in required for named capture-groups on regular expressions
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}