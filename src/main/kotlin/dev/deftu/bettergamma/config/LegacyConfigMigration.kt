package dev.deftu.bettergamma.config

import dev.deftu.bettergamma.BetterGamma
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/** Moves settings from the 1.x Vigilance config into the OneConfig config */
object LegacyConfigMigration {
    private val LINE = Regex("""^\s*"?([^"=]+?)"?\s*=\s*(.+?)\s*$""")

    fun run() {
        val toml = File(FabricLoader.getInstance().gameDir.toFile(), "Deftu/bettergamma.toml")
        if (!toml.isFile) return

        val values = runCatching {
            toml.readLines().mapNotNull { LINE.find(it) }.associate { it.groupValues[1].lowercase() to it.groupValues[2].trim('"') }
        }.onFailure { BetterGamma.LOGGER.error("Failed to read the old {} config", BetterGamma.NAME, it) }.getOrNull() ?: return

        // The old file has to be gone before anything is applied, or a failed rename would reapply it over newer settings on every launch
        val retired = runCatching {
            Files.move(toml.toPath(), File(toml.parentFile, "${toml.name}.migrated").toPath(), StandardCopyOption.REPLACE_EXISTING)
        }.onFailure { BetterGamma.LOGGER.error("Couldn't move the old {} config out of the way, so it wasn't migrated", BetterGamma.NAME, it) }
        if (retired.isFailure) return

        fun bool(key: String) = values[key]?.toBooleanStrictOrNull() ?: false

        // Night vision was its own toggle in 1.x and is now part of fullbright
        BetterGammaConfig.fullbright = bool("fullbright") || bool("night_vision")
        values["gamma"]?.toIntOrNull()?.let { BetterGammaConfig.brightness = it.coerceIn(0, BetterGamma.MAX_BRIGHTNESS).toFloat() }
        BetterGammaConfig.save()
    }
}
