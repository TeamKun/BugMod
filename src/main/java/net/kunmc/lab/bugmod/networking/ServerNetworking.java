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
import net.minecraft.network.MessageType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.lang.reflect.Type;

public class ServerNetworking {
    public static int tick = 0;

    public static void sendLevel(String name, int level, String playerName) {
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(name + " " + level + " " + playerName);
            ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(BugModNetworking.level), buf);
        });
    }

    public static void sendGameMode() {
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(GameManager.runningMode.toString());
            ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(BugModNetworking.gameMode), buf);
        });
    }

    public static void receiveLevel(){
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(BugModNetworking.level), (server, player, handler, buf, response) -> {
            // Name, level, PlayerName??
            String test = buf.readString(30);
            System.out.println(test);
            String[] array = test.split(" ");
            if (GameManager.runningMode == GameManager.GameMode.MODE_START) {
                GameManager.updateLevel(array[0], Integer.parseInt(array[1]), player.getGameProfile().getName());
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
                            GameManager.breakSkinLevel + " " +
                            GameManager.helpSoundLevel + " " +
                            GameManager.spiderSoundLevel;
                    System.out.println(all);
                    buf.writeString(all);

                    ServerPlayNetworking.send(player, BugModNetworking.identifierFactory("all"), buf);
                });
                // ゲームモード送信
                sendGameMode();
            }
        });
    }
}
