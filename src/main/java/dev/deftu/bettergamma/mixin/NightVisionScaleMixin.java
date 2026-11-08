package dev.deftu.bettergamma.mixin;

import dev.deftu.bettergamma.BetterGamma;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=1.9 {
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
//?} else {
/*import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.living.LivingEntity;
*///?}

/**
 * Lets fullbright's night vision fade in and out. Shader packs read the same method, so they fade with it.
 */
@Mixin(GameRenderer.class)
public class NightVisionScaleMixin {
    //? if >=26.2 {
    @Inject(method = "nightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void bettergamma$fadeNightVision(LivingEntity entity, float partialTicks, CallbackInfoReturnable<Float> cir) {
    //?} else if >=1.9 {
    /*@Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void bettergamma$fadeNightVision(LivingEntity entity, float partialTicks, CallbackInfoReturnable<Float> cir) {
    *///?} else {
    /*@Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private void bettergamma$fadeNightVision(LivingEntity entity, float partialTicks, CallbackInfoReturnable<Float> cir) {
    *///?}
        Float scale = BetterGamma.nightVisionScale(entity);
        if (scale != null) cir.setReturnValue(scale);
    }
}
