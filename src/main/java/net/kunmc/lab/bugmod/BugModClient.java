package net.kunmc.lab.bugmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.kunmc.lab.bugmod.block.BlockManager;
import net.kunmc.lab.bugmod.block.BugBlock1;
import net.kunmc.lab.bugmod.client.BugsHUD;
import net.kunmc.lab.bugmod.client.UpdateClientLevelManager;
import net.kunmc.lab.bugmod.event.PlayerClientEvent;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.kunmc.lab.bugmod.sound.BugSoundManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class BugModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientNetworking.registerReceiver();
        PlayerClientEvent.register();
        BugsHUD.register();
        ShaderManager.register();
        BlockRenderLayerMap.INSTANCE.putBlock(BlockManager.bugBlock1, RenderLayer.getCutout());
    }
}
