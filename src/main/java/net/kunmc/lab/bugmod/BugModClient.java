package net.kunmc.lab.bugmod;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.client.ShaderManager;
import net.kunmc.lab.bugmod.event.PlayerEvent;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

@Environment(EnvType.CLIENT)
public class BugModClient implements ClientModInitializer {
    public static BugModClient instance;
    private ShaderEffect shaderGlitch;
    private static final ManagedShaderEffect GLITCH_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier("bugmod", "shaders/post/glitch.json"));

    public static BugModClient getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        instance = this;
        ClientNetworking.registerReceiver();
        PlayerEvent.register();
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            GLITCH_SHADER.render(tickDelta);
        });

    }

    //public void renderShaders(float tickDelta) {
    //    if(shaderGlitch == null) {
    //        shaderGlitch = ShaderManager.register(new Identifier("bugmod", "shaders/post/glitch.json"));
    //    }

    //    //if(shaderGlitch==null){
    //    //    System.out.println("invalid Null");
    //    //}else{
    //    //    System.out.println("loaded");
    //    //}
    //    ShaderManager.render(shaderGlitch,tickDelta);
    //}
}
