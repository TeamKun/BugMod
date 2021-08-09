package net.kunmc.lab.bugmod.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class BugModHUD {
    /**
     * fillで画面を赤っぽくするだけ
     * @param matrices
     */
    public static void renderRedScreen(MatrixStack matrices){
        // See: https://qiita.com/konifar/items/106731d8a35303606597
        // int alphaColor = ((int)(transparency * 255) << 24) & 0xFF000000 | color;
        int alphaColor = 0xCC2f0000;
        if (GameManager.redScreenLevel > 1) {
            DrawableHelper.fill(matrices, 0, 0, 1000, 1000, alphaColor);
        }
    }
}
