package net.kunmc.lab.bugmod.texture;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.biome.source.BiomeAccess;

public class ReloadTexture {
    public static void reload(ServerPlayerEntity player) {
        // ユーザ再構築
        PlayerManager playerManager = player.getServer().getPlayerManager();
        playerManager.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.REMOVE_PLAYER, player));
        playerManager.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, player));
        ServerWorld targetWorld = player.getServerWorld();
        player.networkHandler.sendPacket(new PlayerRespawnS2CPacket(targetWorld.getDimension(), targetWorld.getRegistryKey(), BiomeAccess.hashSeed(targetWorld.getSeed()), player.interactionManager.getGameMode(), player.interactionManager.getPreviousGameMode(), targetWorld.isDebugWorld(), targetWorld.isFlat(), true));
        player.networkHandler.requestTeleport(player.getX(), player.getY(), player.getZ(), player.getYaw(0), player.getPitch(0));
        player.server.getPlayerManager().sendCommandTree(player);
        player.networkHandler.sendPacket(new ExperienceBarUpdateS2CPacket(player.experienceProgress, player.totalExperience, player.experienceLevel));
        player.networkHandler.sendPacket(new HealthUpdateS2CPacket(player.getHealth(), player.getHungerManager().getFoodLevel(), player.getHungerManager().getSaturationLevel()));
        for (StatusEffectInstance statusEffect : player.getStatusEffects()) {
            player.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(player.getEntityId(), statusEffect));
        }
        player.sendAbilitiesUpdate();
        playerManager.sendWorldInfo(player, targetWorld);
        playerManager.sendPlayerStatus(player);
    }
}
