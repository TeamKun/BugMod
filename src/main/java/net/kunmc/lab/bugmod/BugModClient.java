package net.kunmc.lab.bugmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.kunmc.lab.bugmod.block.BlockManager;
import net.kunmc.lab.bugmod.event.PlayerClientEvent;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BugModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientNetworking.registerReceiver();
        PlayerClientEvent.register();
        ShaderManager.register();
        BlockRenderLayerMap.INSTANCE.putBlock(BlockManager.bugBlock1, RenderLayer.getCutout());
    }
}
