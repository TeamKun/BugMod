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

    public static void receiveLevel(){
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.redScreenName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.redScreenMaxLevel < level) {
                GameManager.updateLevel(GameManager.redScreenName, buf.readInt());
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.garbledCharName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.garbledCharMaxLevel < level) {
                GameManager.updateLevel(GameManager.garbledCharName, buf.readInt());
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.breakScreenName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.breakScreenMaxLevel < level) {
                GameManager.updateLevel(GameManager.breakScreenName, buf.readInt());
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.breakTextureName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.breakTextureMaxLevel < level) {
                GameManager.updateLevel(GameManager.breakTextureName, buf.readInt());
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.helpSoundName), (server, player, handler, buf, response) -> {
            int level = buf.readInt();
            if (GameManager.runningMode == GameManager.GameMode.MODE_START && GameManager.helpSoundMaxLevel < level) {
                GameManager.updateLevel(GameManager.helpSoundName, buf.readInt());
            }
        });
    }

    public static void sendLevelEveryTick(){
        ServerTickEvents.START_SERVER_TICK.register(m -> {
            tick ++;
            if (tick % 20 == 0){
                BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    // 全パラメータをスペース区切りで送る
                    String all = GameManager.redScreenLevel + " " +
                            GameManager.garbledCharLevel + " " +
                            GameManager.breakScreenLevel + " " +
                            GameManager.breakTextureLevel + " " +
                            GameManager.helpSoundLevel;
                    buf.writeString(all);

                    ServerPlayNetworking.send(player, BugModNetworking.identifierFactory("ALL"), buf);
                });
            }
        });
    }
}
