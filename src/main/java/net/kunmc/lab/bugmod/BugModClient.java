package net.kunmc.lab.bugmod;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.event.PlayerEvent;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BugModClient implements ClientModInitializer {
    public static BugModClient instance;
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
        //ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
        //    GLITCH_SHADER.render(tickDelta);
        //});

    }
}
