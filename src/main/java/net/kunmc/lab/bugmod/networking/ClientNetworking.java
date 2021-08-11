package net.kunmc.lab.bugmod.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class ClientNetworking {
    public static void registerReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(BugModNetworking.identifierFactory(GameManager.redScreenName), (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            GameManager.redScreenLevel = packetByteBuf.readInt();
        });
    }
    public static void sendRedScreenLevel() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(GameManager.redScreenLevel+1);
        ClientPlayNetworking.send(BugModNetworking.identifierFactory(GameManager.redScreenName), buf);
    }
}
