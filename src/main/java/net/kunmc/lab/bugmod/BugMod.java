package net.kunmc.lab.bugmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.kunmc.lab.bugmod.block.BlockManager;
import net.kunmc.lab.bugmod.command.BugCommand;
import net.kunmc.lab.bugmod.event.PlayerServerEvent;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Random;

public class BugMod implements ModInitializer {
	public static MinecraftServer minecraftServerInstance;
	public static final String MODID = "bugmod";

	@Override
	public void onInitialize() {
		ServerStartCallback.EVENT.register(server -> minecraftServerInstance = server);
		ServerNetworking.receiveLevel();
		ServerNetworking.sendLevelEveryTick();
		PlayerServerEvent.register();
		BlockManager.register();
		BugCommand.register();
	}
}
