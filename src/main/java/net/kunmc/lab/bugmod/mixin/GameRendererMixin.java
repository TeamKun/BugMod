package net.kunmc.lab.bugmod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.BugModClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//@Environment(EnvType.CLIENT)
//@Mixin(GameRenderer.class)
public class GameRendererMixin {
    //@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawEntityOutlinesFramebuffer()V", shift = At.Shift.AFTER))
    //public void renderShaders(float tickDelta, long startTime, boolean tick, CallbackInfo ci){
    //    System.out.println("ABC");
    //    BugModClient.getInstance().renderShaders(tickDelta);
    //}
}
