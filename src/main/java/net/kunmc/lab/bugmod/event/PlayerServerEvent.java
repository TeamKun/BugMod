package net.kunmc.lab.bugmod.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.kunmc.lab.bugmod.block.BlockManager;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Random;

public class PlayerServerEvent {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, blockPos, blockState, blockEntity) -> {
            if (GameManager.runningMode != GameManager.GameMode.MODE_START || player.isCreative() || player.isSpectator()) return;

            Random rnd = new Random();
            int xzRange = 0;
            int yRange = 2;
            double changeProb = 0.0;
            switch (GameManager.breakTextureLevel) {
                case 1:
                    xzRange = 2;
                    changeProb = 0.2;
                    break;
                case 2:
                    xzRange = 3;
                    changeProb = 0.3;
                    break;
                case 3:
                    xzRange = 4;
                    changeProb = 0.4;
                    break;
                case 4:
                    xzRange = 5;
                    changeProb = 0.5;
                    break;
                case 5:
                    xzRange = 6;
                    changeProb = 0.6;
                    break;
            }
            int px = (int)player.getPos().x;
            int py = (int)player.getPos().y;
            int pz = (int)player.getPos().z;
            for (int x = xzRange * -1; xzRange * -1 <= x && x <= xzRange; x++ ) {
                for (int y = yRange * -1; yRange * -1 <= y && y <= yRange; y++ ) {
                    for (int z = xzRange * -1; xzRange * -1 <= z && z <= xzRange; z++ ) {
                        if (rnd.nextDouble()>changeProb) continue;

                        BlockPos bPos = new BlockPos(px + x,py + y,pz + z);

                        Block b = world.getBlockState(bPos).getBlock();
                        String bugBlockName = "";
                        if (b == Blocks.STONE) {
                            bugBlockName = BlockManager.bugStone;
                            //} else if (b == Blocks.DIRT){
                            //} else if (b == Blocks.COBBLESTONE){
                            //} else if (b == Blocks.END_STONE){
                            //} else if (b == Blocks.GRASS_BLOCK){
                            //} else if (b == Blocks.OBSIDIAN){
                            //} else if (b == Blocks.SAND){
                        } else {
                            continue;
                        }
                        Block newBlock = Registry.BLOCK.get(BlockManager.identifierFactory(bugBlockName));
                        world.removeBlock(bPos,false);
                        world.setBlockState(bPos, newBlock.getDefaultState());
                    }
                }
            }
            System.out.println(GameManager.breakTextureLevel);
            GameManager.updateLevel(GameManager.breakTextureName, GameManager.breakTextureLevel+1);
        });
    }
}
