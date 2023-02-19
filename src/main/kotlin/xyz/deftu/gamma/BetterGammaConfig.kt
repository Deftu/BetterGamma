package xyz.deftu.gamma

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import java.io.File

private val deftuDir by lazy {
    val deftuDir = File(FabricLoader.getInstance().gameDir.toFile(), "Deftu")
    if (!deftuDir.exists() && !deftuDir.mkdirs())
        throw IllegalStateException("Could not create Deftu directory!")

    deftuDir
}

private val configFile by lazy {
    File(deftuDir, "@MOD_ID@.toml")
}

object BetterGammaConfig : Vigilant(
    file = configFile,
    guiTitle = BetterGamma.NAME
) {
    @Property(
        type = PropertyType.SWITCH,
        name = "Fullbright",
        description = "Enable fullbright",
        category = "General"
    ) @JvmStatic var fullbright = false

    @Property(
        type = PropertyType.SLIDER,
        name = "Gamma",
        description = "Set custom gamma",
        category = "General",
        max = 1500,
        min = 0
    ) @JvmStatic var gamma = 1500

    @Property(
        type = PropertyType.SWITCH,
        name = "Night Vision",
        description = "Enable night vision",
        category = "General"
    ) @JvmStatic var nightVision = false

    init {
        registerListener<Boolean>("nightVision") { value ->
            BetterGamma.updateNightVision(value)
            MinecraftClient.getInstance().worldRenderer?.reload()
        }
    }
}
