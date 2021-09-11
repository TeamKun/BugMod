package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.BlazeEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BlazeEntityModel;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(BlazeEntityRenderer.class)
public class BlazeEntityRendererMixin extends MobEntityRenderer<BlazeEntity, BlazeEntityModel<BlazeEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/blaze.png");
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/blaze.png");

    public BlazeEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new BlazeEntityModel(), 0.5F);
    }

    @Override
    public Identifier getTexture(BlazeEntity blazeEntity) {
        if (GameManager.breakMobTextureLevel >= 5) {
            return BUG_TEXTURE;
        }
        return TEXTURE;
    }
}
