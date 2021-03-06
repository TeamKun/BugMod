package net.kunmc.lab.bugmod.shader;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.sound.BugSoundManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

public class ShaderManager {
    public static int glitchTime = 0;

    private static final ManagedShaderEffect GLITCH_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(BugMod.MODID, "shaders/post/glitch.json"));

    public static void register() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (glitchTime > 0) {
                GLITCH_SHADER.render(tickDelta);
                glitchTime--;
            }
        });
    }

    public static void runGlitch(int time) {
        if (MinecraftClient.getInstance().player.isSpectator()) return;

        glitchTime = time;
        ClientPlayerEntity p = MinecraftClient.getInstance().player;
        p.playSound(BugSoundManager.noiseSound, 0.05f, 1);
    }
}
