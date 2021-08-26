package net.kunmc.lab.bugmod.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kunmc.lab.bugmod.client.UpdateClientLevelManager;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.network.PacketByteBuf;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientNetworking {
    public static void registerReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.redScreenName), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            UpdateClientLevelManager.startUpdateLevel(GameManager.redScreenName, packetByteBuf.readInt());
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.garbledCharName), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            UpdateClientLevelManager.startUpdateLevel(GameManager.garbledCharName, packetByteBuf.readInt());
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.breakScreenName), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            UpdateClientLevelManager.startUpdateLevel(GameManager.breakScreenName, packetByteBuf.readInt());
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory("ALL"), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            if (UpdateClientLevelManager.shouldUpdatedLevel()){
                String[] array = packetByteBuf.readString().split(" ");
                GameManager.redScreenLevel = Integer.parseInt(array[0]);
                GameManager.garbledCharLevel = Integer.parseInt(array[1]);
                GameManager.breakScreenLevel = Integer.parseInt(array[2]);
                GameManager.breakTextureLevel = Integer.parseInt(array[3]);
                GameManager.helpSoundLevel = Integer.parseInt(array[4]);

            }
        });
    }
    public static void sendRedScreenLevel() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(GameManager.redScreenLevel+1);
        ClientPlayNetworking.send(BugModNetworking.identifierFactory(GameManager.redScreenName), buf);
    }
    public static void sendBreakScreenLevel() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(GameManager.breakScreenLevel+1);
        ClientPlayNetworking.send(BugModNetworking.identifierFactory(GameManager.breakScreenName), buf);
    }
}
