package dev.deftu.gamma

import gg.essential.universal.ChatColor
import gg.essential.universal.UKeyboard
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Formatting
import dev.deftu.lib.client.ToggleKeyBinding
import dev.deftu.lib.events.InputAction
import dev.deftu.lib.events.InputEvent
import dev.deftu.lib.utils.ChatHelper
import dev.deftu.lib.utils.ChatPrefixType
import dev.deftu.lib.utils.TextHelper
import dev.deftu.lib.utils.prefix

object BetterGamma : ClientModInitializer {
    const val NAME = "@MOD_NAME@"
    private const val ID = "@MOD_ID@"

    private val chatPrefix = prefix {
        name = NAME
        color = ChatColor.BLUE
        brackets {
            type = ChatPrefixType.CARET
            bold = true
            color = ChatColor.WHITE
        }
    }

    private val toggleKeyBinding: ToggleKeyBinding by lazy {
        ToggleKeyBinding("key.$ID.toggle", UKeyboard.KEY_G, "key.categories.misc")
    }

    private val nightVisionKeyBinding: ToggleKeyBinding by lazy {
        ToggleKeyBinding("key.$ID.nightvision", UKeyboard.KEY_H, "key.categories.misc")
    }

    override fun onInitializeClient() {
        BetterGammaConfig.initialize()
        KeyBindingHelper.registerKeyBinding(toggleKeyBinding)
        KeyBindingHelper.registerKeyBinding(nightVisionKeyBinding)

        InputEvent.EVENT.register { _, button, action, _, scancode, _ ->
            val minecraft = MinecraftClient.getInstance()
            if (minecraft.world == null || minecraft.currentScreen != null || action != InputAction.RELEASE) return@register

            if (
                toggleKeyBinding.matchesKey(button, scancode) ||
                toggleKeyBinding.matchesMouse(button)
            ) {
                toggleKeyBinding.toggle()
                return@register
            }

            if (
                nightVisionKeyBinding.matchesKey(button, scancode) ||
                nightVisionKeyBinding.matchesMouse(button)
            ) {
                nightVisionKeyBinding.toggle()
            }
        }

        ClientTickEvents.END_CLIENT_TICK.register {
            val fullbrightToggle = toggleKeyBinding.toggle
            BetterGammaConfig.fullbright = if (fullbrightToggle) run {
                val message = TextHelper.createTranslatableText("$ID.fullbright")
                    .append(TextHelper.createLiteralText(" "))
                message.append(if (!BetterGammaConfig.fullbright)
                    TextHelper.createTranslatableText("$ID.enabled").formatted(Formatting.GREEN) else
                    TextHelper.createTranslatableText("$ID.disabled").formatted(Formatting.RED))
                ChatHelper.sendClientMessage(message, chatPrefix)

                !BetterGammaConfig.fullbright
            } else BetterGammaConfig.fullbright

            val nightVisionToggle = nightVisionKeyBinding.toggle
            BetterGammaConfig.nightVision = if (nightVisionToggle) run {
                val message = TextHelper.createTranslatableText("effect.minecraft.night_vision")
                    .append(TextHelper.createLiteralText(" "))
                message.append(if (!BetterGammaConfig.nightVision)
                    TextHelper.createTranslatableText("$ID.enabled").formatted(Formatting.GREEN) else
                    TextHelper.createTranslatableText("$ID.disabled").formatted(Formatting.RED))
                ChatHelper.sendClientMessage(message, chatPrefix)

                updateNightVision(!BetterGammaConfig.nightVision)
                !BetterGammaConfig.nightVision
            } else BetterGammaConfig.nightVision

            if (fullbrightToggle || nightVisionToggle) {
                BetterGammaConfig.markDirty()
                BetterGammaConfig.writeData()
                MinecraftClient.getInstance().worldRenderer.reload()
            }
        }

        ClientPlayConnectionEvents.JOIN.register { _, _, _ ->
            updateNightVision(BetterGammaConfig.nightVision)
        }
    }

    internal fun updateNightVision(newValue: Boolean) {
        val minecraft = MinecraftClient.getInstance()
        val player = minecraft.player ?: return

        if (newValue) {
            givePermanentEffect(player, StatusEffects.NIGHT_VISION)
        } else {
            player.removeStatusEffect(StatusEffects.NIGHT_VISION)
        }
    }

    private fun givePermanentEffect(
        player: PlayerEntity,
        @Suppress("SameParameterValue") effect: StatusEffect
    ) {
        val instance = StatusEffectInstance(effect, -1)
        player.addStatusEffect(instance)
    }
}
