package net.kunmc.lab.bugmod.texture;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.biome.source.BiomeAccess;

import java.util.Objects;

public class ReloadTexture {
    public static void reload(ServerPlayerEntity player) {
        for(ServerPlayerEntity other : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()) {
            other.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.REMOVE_PLAYER, player));
            other.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, player));
        }

        ServerWorld targetWorld = (ServerWorld) player.world;
        player.networkHandler.sendPacket(new PlayerRespawnS2CPacket(targetWorld.getDimension(), targetWorld.getRegistryKey(), BiomeAccess.hashSeed(targetWorld.getSeed()), player.interactionManager.getGameMode(), player.interactionManager.getPreviousGameMode(), targetWorld.isDebugWorld(), targetWorld.isFlat(), true));
        player.networkHandler.requestTeleport(player.getX(), player.getY(), player.getZ(), player.yaw, player.pitch);
        player.server.getPlayerManager().sendCommandTree(player);
        player.networkHandler.sendPacket(new ExperienceBarUpdateS2CPacket(player.experienceProgress, player.totalExperience, player.experienceLevel));
        player.networkHandler.sendPacket(new HealthUpdateS2CPacket(player.getHealth(), player.getHungerManager().getFoodLevel(), player.getHungerManager().getSaturationLevel()));
        for (StatusEffectInstance statusEffect : player.getStatusEffects()) {
            player.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(player.getEntityId(), statusEffect));
        }
        player.sendAbilitiesUpdate();
        player.server.getPlayerManager().sendWorldInfo(player, targetWorld);
        player.server.getPlayerManager().sendPlayerStatus(player);
    }
}
