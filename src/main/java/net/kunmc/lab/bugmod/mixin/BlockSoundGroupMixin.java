package net.kunmc.lab.bugmod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.client.BugModHUD;
import net.kunmc.lab.bugmod.client.BugsHUD;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.MessageType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(BlockSoundGroup.class)
public class BlockSoundGroupMixin {
    @Shadow @Final private SoundEvent placeSound;

    /**
     * @author POne0301
     * @reason hoge
     * @return
     */
    @Overwrite()
    public SoundEvent getPlaceSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    /**
     * @author POne0301
     * @reason hoge
     * @return
     */
    @Overwrite()
    public SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }


    //@Environment(EnvType.CLIENT)
    //public SoundEvent getBreakSound() {
    //    return this.breakSound;
    //}

    //public SoundEvent getStepSound() {
    //    return this.stepSound;
    //}

    //public SoundEvent getPlaceSound() {
    //    return this.placeSound;
    //}

    //@Environment(EnvType.CLIENT)
    //public SoundEvent getHitSound() {
    //    return this.hitSound;
    //}

    //public SoundEvent getFallSound() {
    //    return this.fallSound;
    //}
}