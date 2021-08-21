package net.kunmc.lab.bugmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.impl.object.builder.FabricBlockInternals;
import net.kunmc.lab.bugmod.block.BugStoneBlock;
import net.kunmc.lab.bugmod.command.BugCommand;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BugMod implements ModInitializer {
	public static MinecraftServer minecraftServerInstance;


	@Override
	public void onInitialize() {
		ServerStartCallback.EVENT.register(server -> minecraftServerInstance = server);
		ServerNetworking.receiveLevel();
		//BUGMOD_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("bugmod", "path"), BlockEntityType.Builder.create(BugModBlockEntity::new, Blocks.STONE).build(null));
		BugStoneBlock bugStoneBlock = new BugStoneBlock(FabricBlockSettings.of(Material.STONE).hardness(0.4f));

		Registry.register(Registry.BLOCK, new Identifier("bugmod", "bug_stone"), bugStoneBlock);
		Registry.register(Registry.ITEM, new Identifier("bugmod", "bug_stone"), new BlockItem(bugStoneBlock, new Item.Settings().group(ItemGroup.MISC)));

		BugCommand.register();
		PlayerBlockBreakEvents.AFTER.register((world, player, blockPos, blockState, blockEntity) -> {
			if (blockState.getBlock() == Blocks.STONE && !player.isCreative()) {
				//world.removeBlock(player.getBlockPos(),false);
				//world.setBlockState(player.getBlockPos().add(0,-1,0), Blocks.STONE.getDefaultState());
			}
		});

		System.out.println("Hello Fabric world!");
	}
}
