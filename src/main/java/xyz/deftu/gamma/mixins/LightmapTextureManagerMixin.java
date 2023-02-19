package xyz.deftu.gamma.mixins;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.deftu.gamma.BetterGammaConfig;

@Mixin({LightmapTextureManager.class})
public class LightmapTextureManagerMixin {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F"))
    private float update(Double value) {
        if (BetterGammaConfig.getFullbright()) return BetterGammaConfig.getGamma() / 100;

        return value.floatValue();
    }
}
