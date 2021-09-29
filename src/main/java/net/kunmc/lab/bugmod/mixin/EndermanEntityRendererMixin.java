package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EndermanBlockFeatureRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EndermanEntityRenderer.class)
public class EndermanEntityRendererMixin extends MobEntityRenderer<EndermanEntity, EndermanEntityModel<EndermanEntity>> {
    @Shadow
    @Final
    private static Identifier TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/enderman/enderman.png");

    public EndermanEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new EndermanEntityModel(0.0F), 0.5F);
        this.addFeature(new EndermanEyesFeatureRenderer(this));
        this.addFeature(new EndermanBlockFeatureRenderer(this));
    }

    /**
     * @author POne0301
     * @reason Bug Mod
     */
    @Overwrite
    public Identifier getTexture(EndermanEntity endermanEntity) {
        if (GameManager.breakMobTextureLevel >= 6 && GameManager.runningMode == GameManager.GameMode.MODE_START) {
            return BUG_TEXTURE;
        }
        return this.TEXTURE;
    }
}
