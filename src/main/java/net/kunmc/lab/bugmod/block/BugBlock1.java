package net.kunmc.lab.bugmod.block;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.sound.BugSoundManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;

public class BugBlock1 extends Block {
    public BugBlock1(AbstractBlock.Settings settings) {
        super(settings);
    }

    private void spawnSilverfish(ServerWorld world, BlockPos pos) {
        SilverfishEntity silverfishEntity = EntityType.SILVERFISH.create(world);
        silverfishEntity.refreshPositionAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
        world.spawnEntity(silverfishEntity);
        silverfishEntity.playSpawnEffects();
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            spawnSilverfish(world, pos);
        }
    }
}
