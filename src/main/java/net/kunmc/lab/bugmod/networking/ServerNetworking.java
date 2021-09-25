package net.kunmc.lab.bugmod.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.kunmc.lab.bugmod.texture.ReloadTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Arrays;

public class ServerNetworking {
    public static int tick = 0;

    public static void sendLevel(String bugName, int level, String playerName) {
        PlayerEntity p = BugMod.minecraftServerInstance.getPlayerManager().getPlayer(playerName);
        if (p == null || p.isSpectator()) return;

        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(bugName + " " + level + " " + playerName);

        // 全員で共通のレベルのバグ
        if (GameManager.isCommonLevelBug(bugName)) {
            BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
               ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(BugModNetworking.level), buf);
            });
        } else {
            ServerPlayNetworking.send((ServerPlayerEntity) p, BugModNetworking.identifierFactory(BugModNetworking.level), buf);
        }
   }

    public static void sendRecoveryLevel(String bugName, int level, String playerName) {
        PlayerEntity p = BugMod.minecraftServerInstance.getPlayerManager().getPlayer(playerName);
        if (p == null || p.isSpectator()) return;

        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(bugName + " " + level + " " + playerName);

        // 全員で共通のレベルのバグ
        if (GameManager.isCommonLevelBug(bugName)) {
            BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
                ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(BugModNetworking.recoverLevel), buf);
            });
        } else {
            ServerPlayNetworking.send((ServerPlayerEntity) p, BugModNetworking.identifierFactory(BugModNetworking.recoverLevel), buf);
        }
    }

    public static void sendGameMode() {
        BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(GameManager.runningMode.toString());
            ServerPlayNetworking.send(player, BugModNetworking.identifierFactory(BugModNetworking.gameMode), buf);
        });
    }

    public static void receiveLevel() {
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(BugModNetworking.level), (server, player, handler, buf, response) -> {
            String test = buf.readString(30);
            String[] array = test.split(" ");
            if (GameManager.runningMode == GameManager.GameMode.MODE_START) {
                GameManager.updateLevelDispatcher(array[0], Integer.parseInt(array[1]), player.getGameProfile().getName());
            }
        });
    }

    public static void receiveSkinUpdate() {
        ServerPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(BugModNetworking.updateSkin), (server, player, handler, buf, response) -> {
            ReloadTexture.reload(player);
        });
    }


    public static void syncServerAndClientEveryTick() {
        ServerTickEvents.START_SERVER_TICK.register(m -> {
            tick++;
            if (tick % 20 == 0) {
                // レベル送信
                BugMod.minecraftServerInstance.getPlayerManager().getPlayerList().forEach(player -> {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    // * クライアントに同期すべき変数を全て送る
                    //   * 全レベルと、メッセージ表示の有無を送信
                    int[] send = Arrays.copyOf(GameManager.getAllBugLevel(player.getEntityName()), GameManager.getAllBugLevel(player.getEntityName()).length + 1);
                    send[send.length - 1] = BooleanUtils.toInteger(GameManager.showUpdateLevelMessage);
                    buf.writeIntArray(send);
                    System.out.println(player.getEntityName() + " " + Arrays.toString(GameManager.getAllBugLevel(player.getEntityName())));
                    ServerPlayNetworking.send(player, BugModNetworking.identifierFactory("all"), buf);
                });
                // ゲームモード送信
                sendGameMode();
            }
        });
    }
}
