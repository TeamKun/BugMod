package net.kunmc.lab.bugmod.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class BugModHUD {
    /**
     * fillで画面を赤っぽくするだけ
     * @param matrices
     */
    public static void renderRedScreen(MatrixStack matrices){
        if (GameManager.redScreenLevel == 0) return;
        // See: https://qiita.com/konifar/items/106731d8a35303606597
        int alphaColor = 0x0;
        switch (GameManager.redScreenLevel){
            case 1:
                // 40%
                alphaColor = 0x662f0000;
                break;
            case 2:
                // 50%
                alphaColor = 0x802f0000;
                break;
            case 3:
                // 60%
                alphaColor = 0x992f0000;
                break;
            case 4:
                // 70%
                alphaColor = 0xB32f0000;
                break;
            case 5:
                // 80%
                alphaColor = 0xCC2f0000;
                break;
        }

        DrawableHelper.fill(matrices, 0, 0,
                MinecraftClient.getInstance().getWindow().getWidth(),
                MinecraftClient.getInstance().getWindow().getHeight(),
                alphaColor);
    }
    public static void renderBreakScreen(MatrixStack matrices){
        if (GameManager.breakScreenLevel == 0) return;
        /**
         * - 画面が左端から徐々に削れていくことを想定する
         *   - 削る範囲が小さいと意味がないので、範囲を決めて削るようにする
         *     - 画面を縦にn分割, 横にm分割すると想定
         *     - 左端画面からmの範囲を決めてmaxの削る範囲を決める
         *     - 四角に画面を削ってしまうと味気ないので、nの範囲毎にmの値を変えて長さの違う横棒がいくつもある感じで削る
         */
        // See: https://qiita.com/konifar/items/106731d8a35303606597
        // int alphaColor = ((int)(transparency * 255) << 24) & 0xFF000000 | color;
        int alphaColor = 0xFF000000;

        // 画面の横分割の数
        double singleHorizontalSize = MinecraftClient.getInstance().getWindow().getScaledWidth()/GameManager.breakScreenMaxLevel;
        // 画面の縦分割の割合、intで計算すると小数点切り捨ての都合から画面を覆えないので、必要な数を出しておく
        int splitVerticalNum = 100;
        int scaleHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int splitScaleHeight = scaleHeight/splitVerticalNum;
        int cnt = 0;

        // 雰囲気のためrandで覆う範囲を少し動かす
        Random rnd = new Random();
        while (splitScaleHeight * cnt < scaleHeight) {
            int left_x = 0;
            int right_x = (int)(singleHorizontalSize * GameManager.breakScreenLevel * 0.9 + singleHorizontalSize * rnd.nextDouble() * 0.3);
            int down_y = splitScaleHeight * cnt;
            int up_y = splitScaleHeight * (cnt+1);
            DrawableHelper.fill(matrices, left_x, down_y,
                    right_x,
                    up_y,
                    alphaColor);
            cnt ++;
        }
    }
    public static void renderBugs(MatrixStack matrices){
        if (BugsHUD.time <= 0) return;
        BugsHUD.renderBugs(matrices);
    }
}