package net.kunmc.lab.bugmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.kunmc.lab.bugmod.command.BugCommand;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.MinecraftServer;

public class BugMod implements ModInitializer {
	public static MinecraftServer minecraftServerInstance;
	public static BlockEntityType<?> BUGMOD_BLOCK_ENTITY;

	@Override
	public void onInitialize() {
		ServerStartCallback.EVENT.register(server -> minecraftServerInstance = server);
		ServerNetworking.receiveLevel();
		//BUGMOD_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("bugmod", "path"), BlockEntityType.Builder.create(BugModBlockEntity::new, Blocks.STONE).build(null));

		BugCommand.register();

		System.out.println("Hello Fabric world!");
	}
}
