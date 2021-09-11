package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.PlayerSkinTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PlayerSkinTexture.class)
public class PlayerSkinTextureMixin {
    @Inject(method = "remapTexture", at = @At("TAIL"))
    private static void BugModTexture(NativeImage image, CallbackInfoReturnable<NativeImage> info) {
        if (GameManager.runningMode != GameManager.GameMode.MODE_START || GameManager.breakSkinLevel == 0) return;

        // スキンの一部がバグる確率
        double prob = 0;
        // バグる範囲
        int maxCnt = 0;
        switch (GameManager.breakSkinLevel) {
            case 1:
                prob = 0.01;
                maxCnt = 3;
                break;
            case 2:
                prob = 0.02;
                maxCnt = 4;
                break;
            case 3:
                prob = 0.04;
                maxCnt = 5;
                break;
            case 4:
                prob = 0.06;
                maxCnt = 6;
                break;
            case 5:
                prob = 0.1;
                maxCnt = 10;
                break;
        }

        // 同じ色が続いたほうがバグっぽいのでカウントで調整
        int tmpCnt = 0;
        int preColor = 0x0;
        for(int x = 0; x<64; x++) {
            for(int y = 0; y<64; y++) {
                if (GameManager.rand.nextDouble() < prob || tmpCnt > 0) {
                    /**
                     * 三種類くらいの色 + 透明を用意する
                     *   色の設定はargb
                     *   color 00F8DFなどgoogleで検索すると色が見やすい
                     */

                    int c = 0x0;
                    if (tmpCnt == 0) {
                        switch (GameManager.rand.nextInt(4)) {
                            case 0:
                                c = 0xFF0008ff;
                                break;
                            case 1:
                                c = 0xFFE7F800;
                                break;
                            case 2:
                                // 0008ff
                                c = 0xFF00FA2A;
                                break;
                            case 3:
                                //透明
                                c = 0x000000;
                                break;
                        }
                        preColor = c;
                        tmpCnt+=1;
                    } else if (tmpCnt < maxCnt) {
                        c = preColor;
                        tmpCnt+=1;
                    } else if (tmpCnt >= maxCnt) {
                        c = preColor;
                        tmpCnt = 0;
                        preColor = 0x0;
                    }
                    image.setPixelColor(x, y, c);
                }
            }
        }
    }
}
