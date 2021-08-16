package net.kunmc.lab.bugmod.mixin;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(BlockRenderManager.class)
public class BlockRenderManagerMixin {
    @Shadow
    @Final
    private BlockModelRenderer blockModelRenderer;
    @Shadow
    @Final
    private BlockColors blockColors;
    @Shadow
    @Final
    private Random random;
    @Shadow
    @Final
    private BlockModels models;


    @Shadow
    public BakedModel getModel(BlockState state) {
        return this.models.getModel(state);
    }

    /**
     * @param state
     * @param matrices
     * @param vertexConsumer
     * @param light
     * @param overlay
     * @author Pone0301
     * @reason hogehoge
     */
    @Overwrite
    public void renderBlockAsEntity(BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumer, int light, int overlay) {
        System.out.println("renderBlockAsEntity");
        //BlockRenderType blockRenderType = state.getRenderType();
        //if (blockRenderType != BlockRenderType.INVISIBLE) {
        //    switch(blockRenderType) {
        //        case MODEL:
        //            BakedModel bakedModel = this.getModel(state);
        //            int i = this.blockColors.getColor(state, (BlockRenderView)null, (BlockPos)null, 0);
        //            float f = (float)(i >> 16 & 255) / 255.0F;
        //            float g = (float)(i >> 8 & 255) / 255.0F;
        //            float h = (float)(i & 255) / 255.0F;
        //            this.blockModelRenderer.render(matrices.peek(), vertexConsumer.getBuffer(RenderLayers.getEntityBlockLayer(state, false)), state, bakedModel, f, g, h, light, overlay);
        //            break;
        //        case ENTITYBLOCK_ANIMATED:
        //            BuiltinModelItemRenderer.INSTANCE.render(new ItemStack(state.getBlock()), ModelTransformation.Mode.NONE, matrices, vertexConsumer, light, overlay);
        //    }
        //}
    }

    /**
     * @param state
     * @param pos
     * @param world
     * @param matrix
     * @param vertexConsumer
     * @param cull
     * @param random
     * @return
     * @author P
     * @reason hoge
     */
    @Overwrite
    public boolean renderBlock(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random) {
        System.out.println("render Block");
        return true;
    }
}
