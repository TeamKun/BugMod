package net.kunmc.lab.bugmod.mixin;

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
        System.out.println("set pixel");
        Random rand = new Random();
        for(int x = 0; x<30; x++) {
            for(int y = 0; y<30; y++) {
                if (rand.nextInt(2)==0) {
                    image.setPixelColor(x, y, -16713505);
                }
            }
        }
    }
}