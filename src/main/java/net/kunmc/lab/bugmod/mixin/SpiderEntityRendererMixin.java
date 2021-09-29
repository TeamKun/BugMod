package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpiderEntityRenderer.class)
public class SpiderEntityRendererMixin<T extends SpiderEntity> extends MobEntityRenderer<T, SpiderEntityModel<T>> {
    @Shadow
    @Final
    private static Identifier TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/spider/spider.png");

    public SpiderEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher, SpiderEntityModel<T> entityModel, float f) {
        super(entityRenderDispatcher, entityModel, f);
    }

    /**
     * @author POne0301
     * @reason Bug Mod
     */
    @Overwrite
    public Identifier getTexture(T spiderEntity) {
        if (GameManager.breakMobTextureLevel >= 4 && GameManager.runningMode == GameManager.GameMode.MODE_START) {
            return BUG_TEXTURE;
        }
        return this.TEXTURE;
    }
}
