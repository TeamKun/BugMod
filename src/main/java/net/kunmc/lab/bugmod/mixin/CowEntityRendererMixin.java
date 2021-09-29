package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.CowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CowEntityRenderer.class)
public class CowEntityRendererMixin extends MobEntityRenderer<CowEntity, CowEntityModel<CowEntity>> {
    @Shadow
    @Final
    private static Identifier TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/cow/cow.png");

    public CowEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CowEntityModel(), 0.7F);
    }

    /**
     * @author POne0301
     * @reason Bug Mod
     */
    @Overwrite
    public Identifier getTexture(CowEntity cowEntity) {
        if (GameManager.breakMobTextureLevel >= 1 && GameManager.runningMode == GameManager.GameMode.MODE_START) {
            return BUG_TEXTURE;
        }
        return this.TEXTURE;
    }
}

