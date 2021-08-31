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
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.breakTextureName), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            UpdateClientLevelManager.startUpdateLevel(GameManager.breakTextureName, packetByteBuf.readInt());
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.breakSkinName), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            System.out.println("AAABBB");
            UpdateClientLevelManager.startUpdateLevel(GameManager.breakSkinName, packetByteBuf.readInt());
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory("gamemode"), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            String gameMode = packetByteBuf.readString();
            if (gameMode.equals(GameManager.GameMode.MODE_START.toString())) {
                GameManager.controller(GameManager.GameMode.MODE_START);
            }else if (gameMode.equals(GameManager.GameMode.MODE_NEUTRAL.toString())) {
                GameManager.controller(GameManager.GameMode.MODE_NEUTRAL);
            }else if (gameMode.equals(GameManager.GameMode.MODE_PAUSE.toString())) {
                GameManager.controller(GameManager.GameMode.MODE_PAUSE);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory("all"), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            String level = packetByteBuf.readString();
            String[] levelArray = level.split(" ");
            System.out.println(level);
            GameManager.redScreenLevel = Integer.parseInt(levelArray[0]);
            GameManager.garbledCharLevel = Integer.parseInt(levelArray[1]);
            GameManager.breakScreenLevel = Integer.parseInt(levelArray[2]);
            GameManager.breakTextureLevel = Integer.parseInt(levelArray[3]);
            GameManager.breakSkinLevel = Integer.parseInt(levelArray[4]);
            GameManager.helpSoundLevel = Integer.parseInt(levelArray[5]);
            GameManager.spiderSoundLevel = Integer.parseInt(levelArray[6]);
        });
    }
    public static void sendRedScreenLevel() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        System.out.println(GameManager.redScreenLevel+1);
        buf.writeInt(GameManager.redScreenLevel+1);
        ClientPlayNetworking.send(BugModNetworking.identifierFactory(GameManager.redScreenName), buf);
    }
    public static void sendBreakScreenLevel() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(GameManager.breakScreenLevel+1);
        ClientPlayNetworking.send(BugModNetworking.identifierFactory(GameManager.breakScreenName), buf);
    }
}
