import groovy.lang.MissingPropertyException

pluginManagement {
    repositories {
        // Repositories
        maven("https://maven.deftu.xyz/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://server.bbkr.space/artifactory/libs-release/")
        maven("https://jitpack.io/")

        // Snapshots
        maven("https://maven.deftu.xyz/snapshots")
        mavenLocal()

        // Default repositories
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        val kotlin = "2.0.0"
        kotlin("jvm") version(kotlin)

        val dgt = "1.28.1"
        id("dev.deftu.gradle.multiversion-root") version(dgt)
    }
}

val projectName: String = extra["mod.name"]?.toString()
    ?: throw MissingPropertyException("mod.name has not been set.")
rootProject.name = projectName
rootProject.buildFileName = "root.gradle.kts"

listOf(
    "1.18.2-fabric",
    "1.19.2-fabric",
    "1.19.4-fabric",
    "1.20.1-fabric",
    "1.20.2-fabric",
    "1.20.4-fabric",
    "1.20.6-fabric",
    "1.21-fabric"
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../build.gradle.kts"
    }
}