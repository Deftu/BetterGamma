package dev.deftu.bettergamma.config

import dev.deftu.bettergamma.BetterGamma
import org.polyfrost.oneconfig.api.config.v1.KtConfig
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.ui.v1.keybind.KeybindHelper

//? if <1.9 {
/*private const val KEY_G = 34
*///?} else {
private const val KEY_G = 71
//?}

object BetterGammaConfig : KtConfig("bettergamma.json", BetterGamma.NAME, Category.VISUALS, BetterGamma.ICON) {
    private const val GENERAL = "General"

    var enabled by masterToggle()

    var fullbright by switch(
        name = "Fullbright",
        def = false,
        description = "Lights up the world with your own brightness, and with a hidden night vision effect that shader packs react to. Your video settings and effects stay as they are.",
        category = GENERAL,
        subcategory = "Brightness",
    )

    var brightness by slider(
        name = "Brightness",
        def = BetterGamma.MAX_BRIGHTNESS.toFloat(),
        description = "How bright fullbright makes the world. 100% matches the game's Bright setting.",
        category = GENERAL,
        subcategory = "Brightness",
        unit = "%",
        min = 0f,
        max = BetterGamma.MAX_BRIGHTNESS.toFloat(),
        step = 5f,
    )

    var smoothFade by switch(
        name = "Smooth fade",
        def = true,
        description = "Fades fullbright in and out instead of switching instantly.",
        category = GENERAL,
        subcategory = "Fade",
    )

    var fadeDuration by slider(
        name = "Duration",
        def = 0.6f,
        description = "How long fullbright takes to fade in or out.",
        category = GENERAL,
        subcategory = "Fade",
        unit = "s",
        min = 0.1f,
        max = 3f,
        step = 0.1f,
    )

    var fadeEasing by dropdown(
        name = "Easing",
        defaultOption = FadeEasing.SMOOTH,
        options = FadeEasing.entries.toTypedArray(),
        description = "How the fade speeds up and slows down.",
        category = GENERAL,
        subcategory = "Fade",
        stringTransformer = FadeEasing::label,
    )

    var toggleFullbrightKey by keybind(
        name = "Toggle fullbright",
        def = KeybindHelper.builder().key(KEY_G).action { pressed: Boolean -> if (pressed) BetterGamma.toggleFullbright(); true }.build(),
        description = "Turns fullbright on or off.",
        category = GENERAL,
        subcategory = "Keybinds",
    )

    var notifyToggles by switch(
        name = "Show toggle notifications",
        def = true,
        description = "Shows a notification when a keybind or command turns fullbright on or off.",
        category = GENERAL,
        subcategory = "Keybinds",
    )

    init {
        hideIf(::fadeDuration) { !smoothFade }
        hideIf(::fadeEasing) { !smoothFade }
        addCallback(::smoothFade) { refreshHidden(); false }
    }

    private fun refreshHidden() {
        tree?.map?.values?.forEach { (it as? Property<*>)?.revaluateDisplay() }
    }
}
