package net.kunmc.lab.bugmod.block;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.sound.BugSoundManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class BugBlock1 extends Block {
    public BugBlock1(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        world.playSound(
                 null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                 pos, // The position of where the sound will come from
                 SoundEvents.ENTITY_SHEEP_DEATH, // The sound that will play
                 SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                 1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                 1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
    }
}
