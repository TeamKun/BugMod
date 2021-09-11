package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.GhastEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.GhastEntityModel;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GhastEntityRenderer.class)
public class GhastEntityRendererMixin extends MobEntityRenderer<GhastEntity, GhastEntityModel<GhastEntity>> {

    @Shadow
    @Final
    private static Identifier TEXTURE;
    @Shadow
    @Final
    private static Identifier ANGRY_TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/ghast/ghast.png");
    private static final Identifier BUG_ANGRY_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/ghast/ghast_shooting.png");

    public GhastEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new GhastEntityModel(), 1.5F);
    }

    public Identifier getTexture(GhastEntity ghastEntity) {
        if (GameManager.breakMobTextureLevel >= 5) {
            return ghastEntity.isShooting() ? BUG_ANGRY_TEXTURE : BUG_TEXTURE;
        }
        return ghastEntity.isShooting() ? this.ANGRY_TEXTURE : this.TEXTURE;
    }
}

