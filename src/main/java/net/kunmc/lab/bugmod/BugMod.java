package net.kunmc.lab.bugmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.kunmc.lab.bugmod.block.BlockManager;
import net.kunmc.lab.bugmod.command.BugCommand;
import net.kunmc.lab.bugmod.event.PlayerServerEvent;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.networking.ServerNetworking;
import net.kunmc.lab.bugmod.sound.BugSoundManager;
import net.minecraft.server.MinecraftServer;

public class BugMod implements ModInitializer {
    public static MinecraftServer minecraftServerInstance;
    public static final String MODID = "bugmod";


    @Override
    public void onInitialize() {
        ServerStartCallback.EVENT.register(server -> minecraftServerInstance = server);
        ServerNetworking.receiveLevel();
        ServerNetworking.syncServerAndClientEveryTick();
        PlayerServerEvent.register();
        BlockManager.register();
        BugCommand.register();
        BugSoundManager.register();
        GameManager.resetGame();
        GameManager.resetUpdateLevelProbability();
    }
}
