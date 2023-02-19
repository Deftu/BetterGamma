plugins {
    java
    kotlin("jvm")
    id("xyz.deftu.gradle.multiversion")
    id("xyz.deftu.gradle.tools")
    id("xyz.deftu.gradle.tools.blossom")
    id("xyz.deftu.gradle.tools.minecraft.loom")
}

repositories {
    maven("https://maven.terraformersmc.com/")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    modImplementation("net.fabricmc.fabric-api:fabric-api:${when (mcData.version) {
        11902 -> "0.73.2+1.19.2"
        11802 -> "0.67.1+1.18.2"
        else -> throw IllegalStateException("Invalid MC version: ${mcData.version}")
    }}")

    modImplementation("net.fabricmc:fabric-language-kotlin:1.9.1+kotlin.1.8.10")
    modImplementation("xyz.deftu:DeftuLib-${mcData.versionStr}:1.5.3")

    modImplementation("com.terraformersmc:modmenu:${when (mcData.version) {
        11902 -> "4.0.6"
        11802 -> "3.2.3"
        else -> throw IllegalStateException("Invalid MC version: ${mcData.version}")
    }}")
}
