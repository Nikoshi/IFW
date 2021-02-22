import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.0.0"
    application
}

group = "net.informatiger"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sksamuel.hoplite:hoplite-core:1.4.0")
    implementation("com.sksamuel.hoplite:hoplite-hocon:1.4.0")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "net.informatiger.ifw.MainKt"
}

val fatJar = task("fatJar", type = Jar::class) {
    manifest {
        attributes["Implementation-Title"] = "IFW"
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = "net.informatiger.ifw.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

afterEvaluate {
    tasks["ktlintMainSourceSetCheck"].dependsOn(tasks["ktlintMainSourceSetFormat"])
    tasks["ktlintTestSourceSetCheck"].dependsOn(tasks["ktlintTestSourceSetFormat"])
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}
