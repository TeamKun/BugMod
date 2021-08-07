package net.kunmc.lab.bugmod;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.kunmc.lab.bugmod.command.BugCommand;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.server.command.ServerCommandSource;

public class BugMod implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        BugCommand.register();

		System.out.println("Hello Fabric world!");
	}
}
