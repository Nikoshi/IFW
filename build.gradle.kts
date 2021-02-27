import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.0.0"
    id("com.github.johnrengelman.shadow") version "4.0.4"
    application
}

group = "net.informatiger"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("com.sksamuel.hoplite:hoplite-core:1.4.0")
    implementation("com.sksamuel.hoplite:hoplite-hocon:1.4.0")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "net.informatiger.ifw.MainKt"
}

afterEvaluate {
    tasks["ktlintMainSourceSetCheck"].dependsOn(tasks["ktlintMainSourceSetFormat"])
    tasks["ktlintTestSourceSetCheck"].dependsOn(tasks["ktlintTestSourceSetFormat"])
}
