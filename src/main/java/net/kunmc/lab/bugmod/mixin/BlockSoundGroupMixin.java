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
    @Shadow @Final private SoundEvent stepSound;

    /**
     * @author POne0301
     * @reason hoge
     * @return
     */
    @Overwrite()
    public SoundEvent getPlaceSound() {
        if (GameManager.spiderSoundLevel > 0)
            return SoundEvents.ENTITY_SPIDER_AMBIENT;

        return this.placeSound;
    }

    /**
     * @author POne0301
     * @reason hoge
     * @return
     */
    @Overwrite()
    public SoundEvent getStepSound() {
        if (GameManager.spiderSoundLevel > 1)
            return SoundEvents.ENTITY_SPIDER_AMBIENT;

        return this.stepSound;
    }
}