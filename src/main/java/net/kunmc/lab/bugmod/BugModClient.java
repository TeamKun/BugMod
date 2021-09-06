package net.kunmc.lab.bugmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.kunmc.lab.bugmod.block.BlockManager;
import net.kunmc.lab.bugmod.event.PlayerClientEvent;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BugModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientNetworking.registerReceiver();
        PlayerClientEvent.register();
        ShaderManager.register();

        // 透過のための処理
        BlockRenderLayerMap.INSTANCE.putBlock(BlockManager.bugBlock1, RenderLayer.getCutout());
        // ログアウト時の処理、他サーバへのログイン時に影響がないようにしておく
        ClientPlayConnectionEvents.DISCONNECT.register((handler,server)-> {
            GameManager.resetGame();
            MinecraftClient.getInstance().getSkinProvider().loadSkin(MinecraftClient.getInstance().player.getGameProfile(), (type, id, texture) -> {
            }, true);
        });
    }
}
