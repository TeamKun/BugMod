package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SheepEntityRenderer;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SheepEntityRenderer.class)
public class SheepEntityRendererMixin extends MobEntityRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
    @Shadow
    @Final
    private static Identifier TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/sheep/sheep.png");

    public SheepEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SheepEntityModel(), 0.7F);
        this.addFeature(new SheepWoolFeatureRenderer(this));
    }

    /**
     * @author POne0301
     * @reason Bug Mod
     */
    @Overwrite
    public Identifier getTexture(SheepEntity sheepEntity) {
        if (GameManager.breakMobTextureLevel >= 2 && GameManager.runningMode == GameManager.GameMode.MODE_START) {
            return BUG_TEXTURE;
        }
        return this.TEXTURE;
    }
}

