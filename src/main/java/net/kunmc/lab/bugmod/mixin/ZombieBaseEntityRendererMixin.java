package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ZombieBaseEntityRenderer.class)
public class ZombieBaseEntityRendererMixin<T extends ZombieEntity, M extends ZombieEntityModel<T>> extends BipedEntityRenderer<T, M> {

    @Shadow
    @Final
    private static Identifier TEXTURE;
    private static final Identifier BUG_TEXTURE = new Identifier(BugMod.MODID, "textures/entity/zombie/zombie.png");

    protected ZombieBaseEntityRendererMixin(EntityRenderDispatcher dispatcher, M zombieEntityModel, M zombieEntityModel2, M zombieEntityModel3) {
        super(dispatcher, zombieEntityModel, 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, zombieEntityModel2, zombieEntityModel3));
    }

    /**
     * @author POne0301
     * @reason Bug Mod
     */
    @Overwrite
    public Identifier getTexture(ZombieEntity zombieEntity) {
        if (GameManager.breakMobTextureLevel >= 3) {
            return BUG_TEXTURE;
        }
        return this.TEXTURE;
    }
}
