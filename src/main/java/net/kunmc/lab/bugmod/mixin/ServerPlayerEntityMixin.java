package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.game.PlayerGameManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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