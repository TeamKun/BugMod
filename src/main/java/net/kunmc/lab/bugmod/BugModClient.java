package net.kunmc.lab.bugmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.event.PlayerEvent;
import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BugModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("Register");
        ClientNetworking.registerReceiver();
        PlayerEvent.register();
        System.out.println("Register");
    }
}
