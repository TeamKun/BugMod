package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(SpiderEntityRenderer.class)
public class SpiderEntityRendererMixin<T extends SpiderEntity> extends MobEntityRenderer<T, SpiderEntityModel<T>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/spider/spider.png");
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID,"textures/entity/spider/spider.png");

    public SpiderEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher, SpiderEntityModel<T> entityModel, float f) {
        super(entityRenderDispatcher, entityModel, f);
    }

    @Override
    public Identifier getTexture(T spiderEntity) {
        if (GameManager.breakMobTextureLevel >= 4){
            return BUG_TEXTURE;
        }
        return TEXTURE;
    }
}