package net.kunmc.lab.bugmod.mixin;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.SERVER)
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("HEAD"), method = "onDeath", cancellable = true)
    public void hookDeath(DamageSource source, CallbackInfo info) {
        if ((LivingEntity)(Object)this instanceof MobEntity && source.getAttacker() instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) source.getAttacker();
            GameManager.updateLevel(GameManager.breakMobTextureName, GameManager.breakMobTextureLevel + 1, p.getGameProfile().getName());
        }
    }
}