package xyz.deftu.gamma.utils

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import xyz.deftu.gamma.BetterGammaConfig

object ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory { parent ->
        BetterGammaConfig.gui() ?: parent
    }
}
