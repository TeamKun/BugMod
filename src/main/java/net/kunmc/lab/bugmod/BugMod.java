package net.kunmc.lab.bugmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.bugmod.command.BugCommand;
import net.kunmc.lab.bugmod.event.PlayerEvent;
import net.minecraft.util.Identifier;

public class BugMod implements ModInitializer {
	public static final String ID = "bugmod";

	public static Identifier identifier(String path) {
		return new Identifier(ID, path);
	}

	@Override
	public void onInitialize() {
        BugCommand.register();
		PlayerEvent.register();

		System.out.println("Hello Fabric world!");
	}
}
