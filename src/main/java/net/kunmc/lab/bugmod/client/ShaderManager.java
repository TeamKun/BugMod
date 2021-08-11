package net.kunmc.lab.bugmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.util.Identifier;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

public class ShaderManager {
    //public static ShaderEffect register(Identifier id) {
    //    MinecraftClient client = MinecraftClient.getInstance();
    //    try {
    //        System.out.println(id.toString());
    //        ShaderManager
    //        ShaderEffect shader = new ShaderEffect(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), id);
    //        shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
    //        return shader;
    //    } catch (IOException e) {
    //        System.out.println("Could not read shader: " + e.getMessage());
    //    }
    //    return null;
    //}

    //public static void render(ShaderEffect shader, float tickDelta){
    //    RenderSystem.disableBlend();
    //    RenderSystem.disableDepthTest();
    //    RenderSystem.enableTexture();
    //    System.out.println(tickDelta);
    //    shader.render(tickDelta);
    //    System.out.println(shader.getName());
    //    MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
    //    RenderSystem.disableBlend();
    //    RenderSystem.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // restore blending
    //    RenderSystem.enableDepthTest();
    //}
}
