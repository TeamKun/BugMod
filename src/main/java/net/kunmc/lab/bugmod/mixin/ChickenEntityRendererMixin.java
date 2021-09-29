package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.ChickenEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChickenEntityRenderer.class)
public class ChickenEntityRendererMixin extends MobEntityRenderer<ChickenEntity, ChickenEntityModel<ChickenEntity>> {
    @Shadow
    @Final
    private static Identifier TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/chicken.png");

    public ChickenEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ChickenEntityModel(), 0.3F);
    }

    /**
     * @author POne0301
     * @reason Bug Mod
     */
    @Overwrite
    public Identifier getTexture(ChickenEntity chickenEntity) {
        if (GameManager.breakMobTextureLevel >= 2 && GameManager.runningMode == GameManager.GameMode.MODE_START) {
            return BUG_TEXTURE;
        }
        return this.TEXTURE;
    }
}
