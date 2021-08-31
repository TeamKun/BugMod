package net.kunmc.lab.bugmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.client.BugsHUD;
import net.kunmc.lab.bugmod.client.UpdateClientLevelManager;
import net.kunmc.lab.bugmod.event.PlayerClientEvent;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.kunmc.lab.bugmod.shader.ShaderManager;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BugModClient implements ClientModInitializer {
    public static BugModClient instance;
    public static final Identifier SPIDER1 = new Identifier(BugMod.MODID, "textures/entity/spider1.png");
    public static final Identifier SPIDER2 = new Identifier(BugMod.MODID, "textures/entity/spider2.png");

    public static BugModClient getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        instance = this;
        ClientNetworking.registerReceiver();
        PlayerClientEvent.register();
        UpdateClientLevelManager.register();
        ShaderManager.register();
        BugsHUD.startBugs();
    }
}
