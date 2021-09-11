package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PigEntityRenderer.class)
public class PigEntityRendererMixin extends MobEntityRenderer<PigEntity, PigEntityModel<PigEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/pig/pig.png");
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/pig/pig.png");

    public PigEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new PigEntityModel(), 0.7F);
        this.addFeature(new SaddleFeatureRenderer(this, new PigEntityModel(0.5F), new Identifier("textures/entity/pig/pig_saddle.png")));
    }

    @Override
    public Identifier getTexture(PigEntity pigEntity) {
        if (GameManager.breakMobTextureLevel >= 1){
            return BUG_TEXTURE;
        }
        return TEXTURE;
    }
}
