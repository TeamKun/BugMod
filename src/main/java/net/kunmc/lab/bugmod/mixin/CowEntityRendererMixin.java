package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.CowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CowEntityRenderer.class)
public class CowEntityRendererMixin extends MobEntityRenderer<CowEntity, CowEntityModel<CowEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/cow/cow.png");
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/cow/cow.png");

    public CowEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CowEntityModel(), 0.7F);
    }

    @Override
    public Identifier getTexture(CowEntity cowEntity) {
        if (GameManager.breakMobTextureLevel >= 1){
            return BUG_TEXTURE;
        }
        return TEXTURE;
    }
}

