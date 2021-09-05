package net.kunmc.lab.bugmod.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.RunArgs;
import net.minecraft.network.PacketByteBuf;

public class ServerNetworking {
    public static int tick = 0;

    public static void sendLevel(String name, int level, String playerName) {
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(name + " " + level + " " + playerName);
            ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(BugModNetworking.level), buf);
        });
    }

    public static void sendRecoveryLevel(String name, int level, String playerName) {
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(name + " " + level + " " + playerName);
            ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(BugModNetworking.recoverLevel), buf);
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
            String test = buf.readString(30);
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
                    buf.writeIntArray(GameManager.getAllBugLevel());

                    ServerPlayNetworking.send(player, BugModNetworking.identifierFactory("all"), buf);
                });
                // ゲームモード送信
                sendGameMode();
            }
        });
    }
}
