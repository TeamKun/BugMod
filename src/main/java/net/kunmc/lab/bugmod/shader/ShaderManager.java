package net.kunmc.lab.bugmod.shader;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.kunmc.lab.bugmod.BugMod;
import net.minecraft.util.Identifier;

public class ShaderManager {
    private static int glitchTime;

    private static final ManagedShaderEffect GLITCH_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(BugMod.MODID, "shaders/post/glitch.json"));

    public static void register(){
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (glitchTime > 0) {
                GLITCH_SHADER.render(tickDelta);
                glitchTime--;
            }
        });
    }

    public static void runGlitch(int time){
        glitchTime = time;
    }
}
