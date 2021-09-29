package net.kunmc.lab.bugmod.mixin;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.game.PlayerGameManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.SERVER)
@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At("HEAD"), method = "onDeath", cancellable = true)
    public void hookDeath(DamageSource source, CallbackInfo info) {
        if ((LivingEntity) (Object) this instanceof MobEntity && source.getAttacker() instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) source.getAttacker();
            GameManager.updateLevelDispatcher(GameManager.breakMobTextureName, PlayerGameManager.playersBugLevel.get(GameManager.commonPlayerName).get(GameManager.breakMobTextureName) + 1, p.getGameProfile().getName());
        }
    }
}