plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")
    `maven-publish`
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.blossom")
    id("dev.deftu.gradle.tools.minecraft.loom")
    id("dev.deftu.gradle.tools.minecraft.releases")
}

toolkit.useDevAuth()

repositories {
    maven("https://maven.terraformersmc.com/")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    modImplementation("net.fabricmc.fabric-api:fabric-api:${mcData.fabricApiVersion}")
    modImplementation(mcData.modMenuDependency)
    modImplementation("net.fabricmc:fabric-language-kotlin:1.7.4+kotlin.1.6.21")

    modImplementation("dev.deftu:DeftuLib-${mcData.versionStr}-${mcData.loader.name}:1.8.0")
}
