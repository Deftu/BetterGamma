package dev.deftu.bettergamma.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.deftu.bettergamma.BetterGamma;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.1 {
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import org.spongepowered.asm.mixin.injection.Slice;
//?} else if >=1.9 {
/*import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.injection.Slice;
*///?} else {
/*import net.minecraft.client.render.GameRenderer;
*///?}

/**
 * Overrides the gamma the lightmap reads, so the player's own video settings are never written.
 */
//? if >=26.1 {
@Mixin(LightmapRenderStateExtractor.class)
//?} else if >=1.9 {
/*@Mixin(LightTexture.class)
*///?} else {
/*@Mixin(GameRenderer.class)
*///?}
public class LightmapMixin {
    //? if >=1.9 {
    //? if >=26.1 {
    private static final String UPDATE = "extract";
    private static final String DIRTY = "Lnet/minecraft/client/renderer/LightmapRenderStateExtractor;needsUpdate:Z";
    //?} else {
    /*private static final String UPDATE = "updateLightTexture";
    private static final String DIRTY = "Lnet/minecraft/client/renderer/LightTexture;updateLightTexture:Z";
    *///?}

    // The lightmap normally only rebuilds once per tick, which would make a fade visibly step
    @ModifyExpressionValue(method = UPDATE, at = @At(value = "FIELD", target = DIRTY, opcode = Opcodes.GETFIELD))
    private boolean bettergamma$refreshWhileFading(boolean original) {
        return original || BetterGamma.isFading();
    }

    @ModifyExpressionValue(
        method = UPDATE,
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;", ordinal = 0),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;gamma()Lnet/minecraft/client/OptionInstance;"))
    )
    private Object bettergamma$overrideGamma(Object original) {
        return BetterGamma.gamma((Double) original);
    }
    //?} else {
    /*@ModifyExpressionValue(method = "updateLightMap", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;lightMapDirty:Z", opcode = Opcodes.GETFIELD))
    private boolean bettergamma$refreshWhileFading(boolean original) {
        return original || BetterGamma.isFading();
    }

    @ModifyExpressionValue(method = "updateLightMap", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;gamma:F"))
    private float bettergamma$overrideGamma(float original) {
        return (float) BetterGamma.gamma(original);
    }
    *///?}
}
