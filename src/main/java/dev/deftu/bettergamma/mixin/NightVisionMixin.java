package dev.deftu.bettergamma.mixin;

import dev.deftu.bettergamma.BetterGamma;
import dev.deftu.bettergamma.RealNightVision;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

//? if >=1.9 {
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
//?} else {
/*import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.effect.StatusEffect;
import net.minecraft.entity.living.effect.StatusEffectInstance;
*///?}

/**
 * Makes the client's own player report a hidden, never-ending night vision effect without adding one.
 * Anything that asks about night vision sees it, including shader packs, while servers can never clear it.
 */
@Mixin(LivingEntity.class)
public class NightVisionMixin implements RealNightVision {
    //? if >=1.9 {
    @Shadow
    @Final
    private Map<Holder<MobEffect>, MobEffectInstance> activeEffects;

    @Override
    public boolean bettergamma$hasRealNightVision() {
        return activeEffects.containsKey(MobEffects.NIGHT_VISION);
    }

    @Unique
    private static MobEffectInstance bettergamma$nightVision;

    @Inject(method = "hasEffect", at = @At("HEAD"), cancellable = true)
    private void bettergamma$hasNightVision(Holder<MobEffect> effect, CallbackInfoReturnable<Boolean> cir) {
        if (effect == MobEffects.NIGHT_VISION && BetterGamma.hasVirtualNightVision(this)) cir.setReturnValue(true);
    }

    @Inject(method = "getEffect", at = @At("HEAD"), cancellable = true)
    private void bettergamma$getNightVision(Holder<MobEffect> effect, CallbackInfoReturnable<MobEffectInstance> cir) {
        if (effect == MobEffects.NIGHT_VISION && BetterGamma.hasVirtualNightVision(this)) {
            if (bettergamma$nightVision == null) {
                bettergamma$nightVision = new MobEffectInstance(MobEffects.NIGHT_VISION, MobEffectInstance.INFINITE_DURATION, 0, false, false, false);
            }

            cir.setReturnValue(bettergamma$nightVision);
        }
    }
    //?} else {
    /*@Shadow
    @Final
    private Map<Integer, StatusEffectInstance> statusEffects;

    @Override
    public boolean bettergamma$hasRealNightVision() {
        return statusEffects.containsKey(StatusEffect.NIGHTVISION.id);
    }

    @Unique
    private static StatusEffectInstance bettergamma$nightVision;

    @Inject(method = "hasStatusEffect(I)Z", at = @At("HEAD"), cancellable = true)
    private void bettergamma$hasNightVisionId(int id, CallbackInfoReturnable<Boolean> cir) {
        if (id == StatusEffect.NIGHTVISION.id && BetterGamma.hasVirtualNightVision(this)) cir.setReturnValue(true);
    }

    @Inject(method = "hasStatusEffect(Lnet/minecraft/entity/living/effect/StatusEffect;)Z", at = @At("HEAD"), cancellable = true)
    private void bettergamma$hasNightVision(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
        if (effect == StatusEffect.NIGHTVISION && BetterGamma.hasVirtualNightVision(this)) cir.setReturnValue(true);
    }

    @Inject(method = "getEffectInstance", at = @At("HEAD"), cancellable = true)
    private void bettergamma$getNightVision(StatusEffect effect, CallbackInfoReturnable<StatusEffectInstance> cir) {
        if (effect == StatusEffect.NIGHTVISION && BetterGamma.hasVirtualNightVision(this)) {
            if (bettergamma$nightVision == null) {
                bettergamma$nightVision = new StatusEffectInstance(StatusEffect.NIGHTVISION.id, Integer.MAX_VALUE, 0, false, false);
            }

            cir.setReturnValue(bettergamma$nightVision);
        }
    }
    *///?}
}
