package net.kunmc.lab.bugmod.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kunmc.lab.bugmod.client.UpdateClientLevelManager;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class ClientNetworking {
    public static void registerReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(BugModNetworking.level), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            String[] array = packetByteBuf.readString().split(" ");
            UpdateClientLevelManager.updateLevel(array[0], Integer.parseInt(array[1]), array[2], true, false);
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(BugModNetworking.forceLevel), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            String[] array = packetByteBuf.readString().split(" ");
            UpdateClientLevelManager.updateLevel(array[0], Integer.parseInt(array[1]), "", false, false);
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(BugModNetworking.recoverLevel), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            String[] array = packetByteBuf.readString().split(" ");
            UpdateClientLevelManager.updateLevel(array[0], Integer.parseInt(array[1]), array[2], false, true);
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(BugModNetworking.gameMode), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            String gameMode = packetByteBuf.readString();
            if (gameMode.equals(GameManager.GameMode.MODE_START.toString())) {
                GameManager.controller(GameManager.GameMode.MODE_START);
            }else if (gameMode.equals(GameManager.GameMode.MODE_NEUTRAL.toString())) {
                GameManager.controller(GameManager.GameMode.MODE_NEUTRAL);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(BugModNetworking.allLevel), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            int[] levelArray = packetByteBuf.readIntArray();
            String[] name = GameManager.getAllBugName();

            for (int i=0; i < name.length; i++) {
                UpdateClientLevelManager.updateLevel(name[i], levelArray[i], "", false, false);
            }
        });
    }
    public static void sendRedScreenLevel() {
        if (MinecraftClient.getInstance().player.isSpectator()) return;
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(GameManager.redScreenName + " " + (GameManager.redScreenLevel+1));
        ClientPlayNetworking.send(BugModNetworking.identifierFactory(BugModNetworking.level), buf);
    }
    public static void sendBreakScreenLevel() {
        if (MinecraftClient.getInstance().player.isSpectator()) return;
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(GameManager.breakScreenName + " " + (GameManager.breakScreenLevel+1));
        ClientPlayNetworking.send(BugModNetworking.identifierFactory(BugModNetworking.level), buf);
    }
}
