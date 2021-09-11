package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.ChickenEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChickenEntityRenderer.class)
public class ChickenEntityRendererMixin extends MobEntityRenderer<ChickenEntity, ChickenEntityModel<ChickenEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/chicken.png");
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/chicken.png");

    public ChickenEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ChickenEntityModel(), 0.3F);
    }

    @Override
    public Identifier getTexture(ChickenEntity chickenEntity) {
        if (GameManager.breakMobTextureLevel >= 2){
            return BUG_TEXTURE;
        }
        return TEXTURE;
    }
}
