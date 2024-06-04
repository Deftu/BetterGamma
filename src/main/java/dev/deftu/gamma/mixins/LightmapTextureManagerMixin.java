package dev.deftu.gamma.mixins;

import dev.deftu.gamma.BetterGammaConfig;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({LightmapTextureManager.class})
public class LightmapTextureManagerMixin {

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F"))
    private float update(Double value) {
        if (BetterGammaConfig.getFullbright()) return (float) BetterGammaConfig.getGamma() / 100;

        return value.floatValue();
    }

}
