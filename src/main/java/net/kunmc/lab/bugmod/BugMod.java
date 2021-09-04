package net.kunmc.lab.bugmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.kunmc.lab.bugmod.block.BlockManager;
import net.kunmc.lab.bugmod.command.BugCommand;
import net.kunmc.lab.bugmod.event.PlayerServerEvent;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.kunmc.lab.bugmod.sound.BugSoundManager;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BugMod implements ModInitializer {
	public static MinecraftServer minecraftServerInstance;
	public static final String MODID = "bugmod";


	@Override
	public void onInitialize() {
		ServerStartCallback.EVENT.register(server -> minecraftServerInstance = server);
		ServerNetworking.receiveLevel();
		ServerNetworking.syncServerAndClientEveryTick();
		PlayerServerEvent.register();
		BlockManager.register();
		BugCommand.register();
		ShaderManager.register();
		BugSoundManager.register();
	}
}
