package net.kunmc.lab.bugmod.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.network.PacketByteBuf;

public class ServerNetworking {
    public static void sendLevel(String name, int level) {
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeInt(level);
            ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(name), buf);
        });
    }

    public static void receiveRedScreenLevel(){
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.redScreenName), (server, player, handler, buf, response) -> {
            GameManager.updateLevel(GameManager.redScreenName, buf.readInt());
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.garbledCharName), (server, player, handler, buf, response) -> {
            GameManager.updateLevel(GameManager.garbledCharName, buf.readInt());
        });
    }
}
