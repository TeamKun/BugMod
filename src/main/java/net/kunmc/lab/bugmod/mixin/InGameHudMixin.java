package net.kunmc.lab.bugmod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.client.BugModHUD;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void bugModRender(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        if (MinecraftClient.getInstance().player.isSpectator()) return;
        if (GameManager.runningMode == GameManager.GameMode.MODE_START) {
            BugModHUD.renderRedScreen(matrixStack);
            BugModHUD.renderBreakScreen(matrixStack);
        }
    }
}
