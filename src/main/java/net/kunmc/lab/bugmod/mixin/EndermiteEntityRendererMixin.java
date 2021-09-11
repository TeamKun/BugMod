package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.EndermiteEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EndermiteEntityModel;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EndermiteEntityRenderer.class)
public class EndermiteEntityRendererMixin extends MobEntityRenderer<EndermiteEntity, EndermiteEntityModel<EndermiteEntity>> {
    @Shadow
    @Final
    private static Identifier TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/endermite.png");

    public EndermiteEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new EndermiteEntityModel(), 0.3F);
    }

    @Override
    public Identifier getTexture(EndermiteEntity endermiteEntity) {
        if (GameManager.breakMobTextureLevel >= 6) {
            return BUG_TEXTURE;
        }
        return this.TEXTURE;
    }
}
