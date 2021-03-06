package net.kunmc.lab.bugmod.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class BugModHUD {
    /**
     * fillで画面を赤っぽくするだけ
     *
     * @param matrices
     */
    public static void renderRedScreen(MatrixStack matrices) {
        if (GameManager.redScreenLevel == 0) return;
        // See: https://qiita.com/konifar/items/106731d8a35303606597

        // 計算が面倒なのでゴリ押し、5%ずつ透明度が下がる 100 - case * 5 = 透明度
        int alphaColor = 0x0;
        switch (GameManager.redScreenLevel) {
            case 1:
                alphaColor = 0x262f0000;
                break;
            case 2:
                alphaColor = 0x332f0000;
                break;
            case 3:
                alphaColor = 0x402f0000;
                break;
            case 4:
                alphaColor = 0x4D2f0000;
                break;
            case 5:
                alphaColor = 0x592f0000;
                break;
            case 6:
                alphaColor = 0x662f0000;
                break;
            case 7:
                alphaColor = 0x732f0000;
                break;
            case 8:
                alphaColor = 0x802f0000;
                break;
            case 9:
                alphaColor = 0x8C2f0000;
                break;
            case 10:
                alphaColor = 0x992f0000;
                break;
            case 11:
                alphaColor = 0xA62f0000;
                break;
            case 12:
                alphaColor = 0xB32f0000;
                break;
            case 13:
                alphaColor = 0xBF2f0000;
                break;
            case 14:
                alphaColor = 0xCC2f0000;
                break;
            case 15:
                alphaColor = 0xD92f0000;
                break;
            case 16:
                alphaColor = 0xE62f0000;
                break;
        }

        DrawableHelper.fill(matrices, 0, 0,
                MinecraftClient.getInstance().getWindow().getWidth(),
                MinecraftClient.getInstance().getWindow().getHeight(),
                alphaColor);
    }

    public static void renderBreakScreen(MatrixStack matrices) {
        if (GameManager.breakScreenLevel == 0) return;
        /**
         * - 画面が左端から徐々に削れていくことを想定する
         *   - 削る範囲が小さいと意味がないので、範囲を決めて削るようにする
         *     - 画面を縦にn分割, 横にm分割すると想定
         *     - 左端画面からmの範囲を決めてmaxの削る範囲を決める
         *     - 四角に画面を削ってしまうと味気ないので、nの範囲毎にmの値を変えて長さの違う横棒がいくつもある感じで削る
         */
        // See: https://qiita.com/konifar/items/106731d8a35303606597
        int alphaColor = 0xFF000000;

        // 画面の横分割の数
        double singleHorizontalSize = MinecraftClient.getInstance().getWindow().getScaledWidth() / GameManager.breakScreenMaxLevel;
        // 画面の縦分割の割合、intで計算すると小数点切り捨ての都合から画面を覆えないので、必要な数を出しておく
        int splitVerticalNum = 100;
        int scaleHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int splitScaleHeight = scaleHeight / splitVerticalNum;
        int cnt = 0;

        // 雰囲気のためrandで覆う範囲を少し動かす
        while (splitScaleHeight * cnt < scaleHeight) {
            int left_x = 0;
            int right_x = (int) (singleHorizontalSize * GameManager.breakScreenLevel * 0.9 + singleHorizontalSize * GameManager.rand.nextDouble() * 0.3);
            int down_y = splitScaleHeight * cnt;
            int up_y = splitScaleHeight * (cnt + 1);
            DrawableHelper.fill(matrices, left_x, down_y,
                    right_x,
                    up_y,
                    alphaColor);
            cnt++;
        }
    }
}