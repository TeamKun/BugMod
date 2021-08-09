package net.kunmc.lab.bugmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.bugmod.command.BugCommand;
import net.kunmc.lab.bugmod.event.PlayerEvent;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public class BugMod implements ModInitializer {
	public static MinecraftServer minecraftServerInstance;

	@Override
	public void onInitialize() {
		ServerStartCallback.EVENT.register(server -> minecraftServerInstance = server);
		ServerNetworking.receiveRedScreenLevel();

		BugCommand.register();

		System.out.println("Hello Fabric world!");
	}
}
