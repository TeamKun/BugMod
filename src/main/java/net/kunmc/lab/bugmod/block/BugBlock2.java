package net.kunmc.lab.bugmod.block;

import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldAccess;

public class BugBlock2 extends Block {
    public BugBlock2(Settings settings) {
        super(settings);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (GameManager.getPlayerBugLevel(GameManager.commonPlayerName, GameManager.breakBlockName) == 3) {
            world.playSound(
                    null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                    pos, // The position of where the sound will come from
                    SoundEvents.ENTITY_ENDERMITE_AMBIENT, // The sound that will play
                    SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                    1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                    1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
            );
        }
    }

    private void spawnEndermite(ServerWorld world, BlockPos pos) {
        EndermiteEntity endermiteEntity = EntityType.ENDERMITE.create(world);
        endermiteEntity.refreshPositionAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        world.spawnEntity(endermiteEntity);
        endermiteEntity.playSpawnEffects();
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        if (GameManager.getPlayerBugLevel(GameManager.commonPlayerName, GameManager.breakBlockName) >= 5) {
            if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                spawnEndermite(world, pos);
            }
        }
    }
}
