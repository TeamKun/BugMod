package net.kunmc.lab.bugmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.client.render.VertexFormat;

import java.util.Random;

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
            DrawableHelper.fill(matrices, 0, 0,
                    MinecraftClient.getInstance().getWindow().getWidth(),
                    MinecraftClient.getInstance().getWindow().getHeight(),
                    alphaColor);
        }
    }
    public static void renderBreakScreen(MatrixStack matrices){
        /**
         * - 画面が右端から徐々に削れていくことを想定する
         *   - 削る範囲が小さいと意味がないので、範囲を決めて削るようにする
         *     - 画面を縦にn分割, 横にm分割すると想定
         *     - 右端画面からmの範囲を決めてmaxの削る範囲を決める
         *     - 四角に画面を削ってしまうと味気ないので、nの範囲毎にmの値を変えて長さの違う横棒がいくつもある感じで削る
         */
        // See: https://qiita.com/konifar/items/106731d8a35303606597
        // int alphaColor = ((int)(transparency * 255) << 24) & 0xFF000000 | color;
        int alphaColor = 0xFF000000;

        // 画面を覆う範囲のmaxの割合
        int splitHorizontalRatio = 2;
        // 画面の縦分割の割合
        int splitVerticalRatio = 100;
        Random rnd = new Random();
        if (GameManager.breakScreenLevel > 1) {
            for (int i=0; i<splitVerticalRatio; i++ ){
                int start_x = (int)(MinecraftClient.getInstance().getWindow().getWidth()/splitHorizontalRatio * rnd.nextDouble());
                int start_y = MinecraftClient.getInstance().getWindow().getHeight()/splitVerticalRatio*i;
                int end_x = MinecraftClient.getInstance().getWindow().getWidth() - MinecraftClient.getInstance().getWindow().getWidth()/splitHorizontalRatio;
                int end_y = MinecraftClient.getInstance().getWindow().getHeight()/splitVerticalRatio*(i+1);
                DrawableHelper.fill(matrices, start_x, start_y,
                        end_x,
                        end_y,
                        alphaColor);
            }
        }
    }
}