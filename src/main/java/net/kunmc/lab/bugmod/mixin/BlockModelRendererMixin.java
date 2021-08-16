package net.kunmc.lab.bugmod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;


@Environment(EnvType.CLIENT)
@Mixin(BlockModelRenderer.class)
public class BlockModelRendererMixin {

    @Shadow @Final private BlockColors colorMap;

    /**
     * @author POne0301
     * @reason hogehoge
     * @param world
     * @param state
     * @param pos
     * @param vertexConsumer
     * @param matrixEntry
     * @param quad
     * @param brightness0
     * @param brightness1
     * @param brightness2
     * @param brightness3
     * @param light0
     * @param light1
     * @param light2
     * @param light3
     * @param overlay
     */
    @Overwrite()
    private void renderQuad(BlockRenderView world, BlockState state, BlockPos pos, VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry, BakedQuad quad, float brightness0, float brightness1, float brightness2, float brightness3, int light0, int light1, int light2, int light3, int overlay) {
        System.out.println("renderQuad");
        float j;
        float k;
        float l;
        if (quad.hasColor()) {
            //int i = this.colorMap.getColor(state, world, pos, quad.getColorIndex());
            int i = -110111110;
            j = (float)(i >> 16 & 255) / 255.0F;
            k = (float)(i >> 8 & 255) / 255.0F;
            l = (float)(i & 255) / 255.0F;
        } else {
            j = 1.0F;
            k = 1.0F;
            l = 1.0F;
        }

        vertexConsumer.quad(matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, j, k, l, new int[]{light0, light1, light2, light3}, overlay, true);
    }

    /**
     * @author Pone0301
     * @reason foo
     * @param world
     * @param model
     * @param state
     * @param pos
     * @param matrix
     * @param vertexConsumer
     * @param cull
     * @param random
     * @param seed
     * @param overlay
     * @return
     */
    @Overwrite
    public boolean render(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay) {
        System.out.println("Render TEST");
        return true;
    }
}