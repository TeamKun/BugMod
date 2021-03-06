package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.game.PlayerGameManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends Entity {

    public ServerPlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("TAIL"), method = "onDeath", cancellable = true)
    public void hookDeath(CallbackInfo info) {
        GameManager.updateLevelDispatcher(GameManager.garbledCharName, PlayerGameManager.playersBugLevel.get(this.getEntityName()).get(GameManager.garbledCharName) + 1, this.getEntityName());
    }
}