package net.kunmc.lab.bugmod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.client.BugModHUD;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
    // よくある文字化けの文字
    private static char[] chars = {'?','§','℃','√','∞','∴','■','○','●','ゅ','上','九','代','吶','壹','峨','後','悶','溘','縺','繧','舌','輔','＜','＞','＠','ｂ','ｅ','ｆ','ｊ','ｌ','ｐ','ｒ','｢','｣','､','･','ｦ','ｧ','ｨ','ｩ','ｪ','ｫ','ｬ','ｭ','ｮ','ｯ','ｰ','ｱ','ｲ','ｳ','ｴ','ｵ','ｶ','ｷ','ｸ','ｹ','ｺ','ｻ','ｼ','ｽ','ｾ','ｿ','￠','￡'};

    @Inject(at = @At("HEAD"), method = "render")
    public void bugModRender(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        if (GameManager.runningMode == GameManager.GameMode.MODE_START) {
            BugModHUD.renderRedScreen(matrixStack);
            BugModHUD.renderBreakScreen(matrixStack);
        }
    }

    @ModifyVariable(at = @At(value = "HEAD"), method = "addChatMessage")
    private Text bugModChatMessage(Text message, MessageType type) {
        if (GameManager.runningMode != GameManager.GameMode.MODE_START)
        if (type == MessageType.CHAT) {
            // exp: <Player90> aaa
            String[] message_info = message.getString().split(" ");
            //message部分だけを取得する
            List<String> split_message_info = new ArrayList(Arrays.asList(message_info));
            split_message_info.remove(0);
            String user_message = String.join(" ", split_message_info);

            // バグらせる文字数計算
            //  割合だとすぐに全文字見えなくなりそうなので文字数にしておく
            int num = 0;

            // もうレベル = 文字化け数にしちゃったほうがいいかも？後で要調整
            switch (GameManager.garbledCharLevel){
                case 1:
                    num = 1;
                    break;
                case 2:
                    num = 2;
                    break;
                case 3:
                    num = 4;
                    break;
                case 4:
                    num = 8;
                    break;
                case 5:
                    num = user_message.length();
                    break;
            }
            num = Math.min(num, user_message.length());

            List<Integer> arr = IntStream.rangeClosed(0, user_message.length()-1).boxed().collect(Collectors.toList());
            Collections.shuffle(arr);
            StringBuilder sb_message = new StringBuilder(user_message);

            // 決められた文字数文をバグらせる
            Random rnd = new Random();
            for (int i=0; i<num; i++){
                sb_message.setCharAt(arr.get(i), chars[rnd.nextInt(chars.length)]);
            }
            message = Text.of(message_info[0] + " " + sb_message);
        }
        return message;
    }
}
