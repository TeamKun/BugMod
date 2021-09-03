package net.kunmc.lab.bugmod.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.kunmc.lab.bugmod.block.BlockManager;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.block.*;
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
                    xzRange = 4;
                    changeProb = 0.5;
                    break;
                case 5:
                    xzRange = 4;
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
                        if (b == Blocks.AIR || b instanceof BedBlock || b instanceof SignBlock || b instanceof DoorBlock ||
                                b == Blocks.LAVA || b == Blocks.WATER || b == Blocks.OBSIDIAN ||
                                b == Blocks.END_GATEWAY || b == Blocks.END_PORTAL || b == Blocks.END_PORTAL_FRAME ||
                                b == Blocks.COAL_ORE || b == Blocks.IRON_ORE || b == Blocks.DIAMOND_ORE || b == Blocks.SPAWNER)
                            continue;
                        String bugBlockName = "";

                        switch (rnd.nextInt(3)){
                            case 0:
                                bugBlockName = BlockManager.bugBlock1Name;
                                break;
                            case 1:
                                bugBlockName = BlockManager.bugBlock2Name;
                                break;
                            case 2:
                                bugBlockName = BlockManager.bugBlock3Name;
                                break;
                        }
                        Block newBlock = Registry.BLOCK.get(BlockManager.identifierFactory(bugBlockName));
                        world.removeBlock(bPos,false);
                        world.setBlockState(bPos, newBlock.getDefaultState());
                    }
                }
            }
            if (rnd.nextDouble() <= 0.1)
                GameManager.updateLevel(GameManager.breakTextureName, GameManager.breakTextureLevel+1, player.getGameProfile().getName());
        });
    }
}
