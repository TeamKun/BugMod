package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SkeletonEntityRenderer.class)
public class SkeletonEntityRendererMixin extends BipedEntityRenderer<AbstractSkeletonEntity, SkeletonEntityModel<AbstractSkeletonEntity>> {
    @Shadow @Final private static Identifier TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/skeleton/skeleton.png");

    public SkeletonEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SkeletonEntityModel(), 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, new SkeletonEntityModel(0.5F, true), new SkeletonEntityModel(1.0F, true)));
    }

    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        if (GameManager.breakMobTextureLevel >= 4){
            return BUG_TEXTURE;
        }
        return this.TEXTURE;
    }
}
