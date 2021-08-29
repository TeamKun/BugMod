package net.kunmc.lab.bugmod.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.network.PacketByteBuf;

public class ServerNetworking {
    public static int tick = 0;

    public static void sendLevel(String name, int level) {
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeInt(level);
            ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(name), buf);
        });
    }

    public static void sendGameMode() {
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(GameManager.runningMode.toString());
            ServerPlayNetworking.send(player, BugModNetworking.identifierFactory("gamemode"), buf);
        });
    }

    public static void receiveLevel(){
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.redScreenName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.redScreenMaxLevel >= level) {
                GameManager.updateLevel(GameManager.redScreenName, level);
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.garbledCharName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.garbledCharMaxLevel >= level) {
                GameManager.updateLevel(GameManager.garbledCharName, level);
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.breakScreenName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.breakScreenMaxLevel >= level) {
                GameManager.updateLevel(GameManager.breakScreenName, level);
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.breakTextureName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.breakTextureMaxLevel >= level) {
                GameManager.updateLevel(GameManager.breakTextureName, level);
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.helpSoundName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.helpSoundMaxLevel > level) {
                GameManager.updateLevel(GameManager.helpSoundName, level);
            }
        });
    }

    public static void syncServerAndClientEveryTick(){
        ServerTickEvents.START_SERVER_TICK.register(m -> {
            tick ++;
            if (tick % 20 == 0){
                // レベル送信
                BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    // 全パラメータをスペース区切りで送る
                    String all = GameManager.redScreenLevel + " " +
                            GameManager.garbledCharLevel + " " +
                            GameManager.breakScreenLevel + " " +
                            GameManager.breakTextureLevel + " " +
                            GameManager.helpSoundLevel;
                    buf.writeString(all);

                    ServerPlayNetworking.send(player, BugModNetworking.identifierFactory("all"), buf);
                });
                // ゲームモード送信
                sendGameMode();
            }
        });
    }
}
