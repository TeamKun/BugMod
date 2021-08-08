package net.kunmc.lab.bugmod.networking;

import com.mojang.brigadier.context.CommandContext;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.RunArgs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.ServerCommandSource;

public class ServerNetworking {
    public static void sendRedScreenLevel(CommandContext<ServerCommandSource> context){
        context.getSource().getMinecraftServer().getPlayerManager().getPlayerList().forEach(player -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeInt(GameManager.redScreenLevel);
            ServerPlayNetworking.send(player, BugModNetworking.RED_SCREEN_LEVEL, new PacketByteBuf(Unpooled.buffer()));
        });
    }
}
