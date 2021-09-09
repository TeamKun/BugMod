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
            if (GameManager.runningMode != GameManager.GameMode.MODE_START || player.isSpectator()) return;

            Random rnd = new Random();
            int xzRange = 0;
            int yRange = 2;
            double changeProb = 0.0;
            if (GameManager.breakBlockLevel == 1) {
                xzRange = 2;
                changeProb = 0.1;
            } else if (GameManager.breakBlockLevel == 2) {
                xzRange = 3;
                changeProb = 0.1;
            } else if (GameManager.breakBlockLevel >= 3) {
                xzRange = 4;
                changeProb = 0.2;
            }
            int px = (int) player.getPos().x;
            int py = (int) player.getPos().y;
            int pz = (int) player.getPos().z;
            for (int x = xzRange * -1; xzRange * -1 <= x && x <= xzRange; x++) {
                for (int y = yRange * -1; yRange * -1 <= y && y <= yRange; y++) {
                    for (int z = xzRange * -1; xzRange * -1 <= z && z <= xzRange; z++) {
                        if (rnd.nextDouble() > changeProb) continue;

                        BlockPos bPos = new BlockPos(px + x, py + y, pz + z);

                        Block b = world.getBlockState(bPos).getBlock();
                        if (b == Blocks.AIR || b instanceof BedBlock || b instanceof SignBlock || b instanceof DoorBlock ||
                                b == Blocks.LAVA || b == Blocks.WATER || b == Blocks.OBSIDIAN || b == Blocks.NETHER_PORTAL ||
                                b == Blocks.END_GATEWAY || b == Blocks.END_PORTAL || b == Blocks.END_PORTAL_FRAME ||
                                b == Blocks.COAL_ORE || b == Blocks.IRON_ORE || b == Blocks.DIAMOND_ORE || b == Blocks.SPAWNER || b == Blocks.CHEST)
                            continue;
                        String bugBlockName = "";

                        switch (rnd.nextInt(3)) {
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
                        world.removeBlock(bPos, false);
                        world.setBlockState(bPos, newBlock.getDefaultState());
                    }
                }
            }
            GameManager.updateLevel(GameManager.breakBlockName, GameManager.breakBlockLevel + 1, player.getGameProfile().getName());
        });
    }
}
