package net.kunmc.lab.bugmod.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kunmc.lab.bugmod.client.BugModSkinUpdater;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class ClientNetworking {
    public static void registerReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.redScreenName), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            GameManager.redScreenLevel = packetByteBuf.readInt();
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.garbledCharName), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            GameManager.garbledCharLevel = packetByteBuf.readInt();
        });
    }
    public static void sendRedScreenLevel() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(GameManager.redScreenLevel+1);
        MinecraftClient.getInstance().getSkinProvider().loadSkin(MinecraftClient.getInstance().player.getGameProfile(), (type, id, texture) -> {}, true);
        //BugModSkinUpdater.updatePlayerSkin(MinecraftClient.getInstance().player.getName().asString());
        ClientPlayNetworking.send(BugModNetworking.identifierFactory(GameManager.redScreenName), buf);
    }
}
