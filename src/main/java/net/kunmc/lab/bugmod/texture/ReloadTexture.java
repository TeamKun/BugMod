package net.kunmc.lab.bugmod.texture;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kunmc.lab.bugmod.mixin.EntityTrackerAccessor;
import net.kunmc.lab.bugmod.mixin.ThreadedAnvilChunkStorageAccessor;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.ChunkManager;

import java.util.Objects;

public class ReloadTexture {
    /**
     * <p>
     * This method has been adapted from the Impersonate mod's <a href="https://github.com/Ladysnake/Impersonate/blob/1.16/src/main/java/io/github/ladysnake/impersonate/impl/ServerPlayerSkins.java">source code</a>
     * under GNU Lesser General Public License v3.0.
     * <p>
     * Reloads player's skin for all the players (including the one that has changed the skin)
     *
     * @author Pyrofab
     */
    public static void reload(ServerPlayerEntity player) {
        for (ServerPlayerEntity other : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()) {
            other.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.REMOVE_PLAYER, player));
            other.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, player));
        }

        ChunkManager manager = player.world.getChunkManager();
        assert manager instanceof ServerChunkManager;
        ThreadedAnvilChunkStorage storage = ((ServerChunkManager) manager).threadedAnvilChunkStorage;
        EntityTrackerAccessor trackerEntry = (EntityTrackerAccessor) ((ThreadedAnvilChunkStorageAccessor) storage).getEntityTrackers().get(player.getEntityId());


        for (ServerPlayerEntity tracking : PlayerLookup.tracking(player)) {
            trackerEntry.getEntry().startTracking(tracking);
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
