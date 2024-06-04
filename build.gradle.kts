import com.modrinth.minotaur.dependencies.DependencyType
import com.modrinth.minotaur.dependencies.ModDependency
import dev.deftu.gradle.tools.minecraft.CurseRelation
import dev.deftu.gradle.tools.minecraft.CurseRelationType

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

toolkitReleases {
    modrinth {
        projectId.set("GbTwnB48")
        dependencies.set(listOf(
            ModDependency("P7dR8mSH", DependencyType.REQUIRED),                     // Fabric API
            ModDependency("Ha28R6CL", DependencyType.REQUIRED),                     // Fabric Language Kotlin
            ModDependency("mOgUt4GM", DependencyType.OPTIONAL),                     // Mod Menu
            ModDependency("WfhjX9sQ", DependencyType.REQUIRED)                      // DeftuLib
        ))
    }

    curseforge {
        projectId.set("828667")
        relations.set(listOf(
            CurseRelation("fabric-api", CurseRelationType.REQUIRED),                // Fabric API
            CurseRelation("fabric-language-kotlin", CurseRelationType.REQUIRED),    // Fabric Language Kotlin
            CurseRelation("modmenu", CurseRelationType.OPTIONAL),                   // Mod Menu
            CurseRelation("deftulib", CurseRelationType.REQUIRED)                   // DeftuLib
        ))
    }
}
