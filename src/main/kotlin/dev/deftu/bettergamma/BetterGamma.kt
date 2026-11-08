package dev.deftu.bettergamma

import com.mojang.brigadier.arguments.IntegerArgumentType
import dev.deftu.bettergamma.config.BetterGammaConfig
import dev.deftu.bettergamma.config.LegacyConfigMigration
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import org.polyfrost.oneconfig.api.ui.v1.ExperimentalOneConfigUiApi
import org.polyfrost.oneconfig.api.ui.v1.OneConfigUI
import org.polyfrost.oneconfig.api.ui.v1.page.OneConfigPages

object BetterGamma : ClientModInitializer {
    const val NAME = "@MOD_NAME@"
    const val ID = "@MOD_ID@"
    const val ICON = "/assets/bettergamma/bettergamma.svg"
    const val MAX_BRIGHTNESS = 1500

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(NAME)

    override fun onInitializeClient() {
        BetterGammaConfig.preload()
        LegacyConfigMigration.run()
        registerCommands()
    }

    /** The gamma the lightmap should use, given the one from the player's video settings */
    @JvmStatic
    fun gamma(vanilla: Double): Double {
        val fade = FullbrightFade.value
        if (fade <= 0f) return vanilla
        // Most of the lightmap is already fully lit a small way past vanilla's range, so a linear ramp would look instant
        return vanilla + (BetterGammaConfig.brightness / 100.0 - vanilla) * fade * fade * fade
    }

    /** Whether [entity] is the client's own player and should report night vision while fullbright is on or fading */
    @JvmStatic
    fun hasVirtualNightVision(entity: Any): Boolean = entity === Minecraft.getInstance().player && FullbrightFade.value > 0f

    /** The night vision strength for [entity] while fullbright is on or fading, or null to leave it to the game */
    @JvmStatic
    fun nightVisionScale(entity: Any): Float? {
        if (entity !== Minecraft.getInstance().player || (entity as RealNightVision).`bettergamma$hasRealNightVision`()) return null
        return FullbrightFade.value.takeIf { it > 0f }
    }

    @JvmStatic
    fun isFading(): Boolean = FullbrightFade.isFading

    fun toggleFullbright() {
        BetterGammaConfig.fullbright = !BetterGammaConfig.fullbright
        BetterGammaConfig.save()
        notifyChange(if (BetterGammaConfig.fullbright) "Your video settings are left untouched." else "Back to your video settings.")
    }

    private fun setBrightness(percent: Int) {
        BetterGammaConfig.brightness = percent.toFloat()
        BetterGammaConfig.fullbright = true
        BetterGammaConfig.save()
        notifyChange("Brightness set to $percent%.")
    }

    private fun notifyChange(message: String) {
        if (!BetterGammaConfig.notifyToggles) return
        val title = "Fullbright ${if (BetterGammaConfig.fullbright) "on" else "off"}"
        when {
            !BetterGammaConfig.enabled -> Notifications.info(title, "$NAME is turned off, so this has no effect until you turn it back on.")
            BetterGammaConfig.fullbright -> Notifications.success(title, message)
            else -> Notifications.info(title, message)
        }
    }

    @OptIn(ExperimentalOneConfigUiApi::class)
    private fun registerCommands() {
        CommandManager.register(
            CommandManager.literal(ID)
                .executes { OneConfigUI.open(OneConfigPages.modConfig(BetterGammaConfig.id)); 1 }
                .then(CommandManager.literal("toggle").executes { toggleFullbright(); 1 })
                .then(CommandManager.literal("brightness")
                    .then(CommandManager.argument("percent", IntegerArgumentType.integer(0, MAX_BRIGHTNESS)).executes { ctx ->
                        setBrightness(IntegerArgumentType.getInteger(ctx, "percent"))
                        1
                    }))
        )
    }
}
