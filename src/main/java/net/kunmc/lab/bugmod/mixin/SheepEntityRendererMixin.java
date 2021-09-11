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
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SheepEntityRenderer.class)
public class SheepEntityRendererMixin extends MobEntityRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/sheep/sheep.png");
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID,"textures/entity/sheep/sheep.png");

    public SheepEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SheepEntityModel(), 0.7F);
        this.addFeature(new SheepWoolFeatureRenderer(this));
    }

    public Identifier getTexture(SheepEntity sheepEntity) {
        if (GameManager.breakMobTextureLevel >= 2){
            return BUG_TEXTURE;
        }
        return TEXTURE;
    }
}

