package net.kunmc.lab.bugmod.block;

import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldAccess;

public class BugBlock1 extends Block {
    public BugBlock1(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (GameManager.breakBlockLevel == 3) {
            world.playSound(
                    null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                    pos, // The position of where the sound will come from
                    SoundEvents.ENTITY_SILVERFISH_AMBIENT, // The sound that will play
                    SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                    1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                    0.01f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
            );
        }
    }

    private void spawnSilverfish(ServerWorld world, BlockPos pos) {
        SilverfishEntity silverfishEntity = EntityType.SILVERFISH.create(world);
        silverfishEntity.refreshPositionAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        world.spawnEntity(silverfishEntity);
        silverfishEntity.playSpawnEffects();
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        if (GameManager.breakBlockLevel >= 4) {
            if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                spawnSilverfish(world, pos);
            }
        }
    }
}
