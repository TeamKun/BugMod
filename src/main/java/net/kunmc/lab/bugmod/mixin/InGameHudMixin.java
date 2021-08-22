package net.kunmc.lab.bugmod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kunmc.lab.bugmod.client.BugModHUD;
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
    private static char[] chars = {'?','§','℃','√','∞','∴','■','○','●','ゅ','上','九','代','吶','壹','峨','後','悶','溘','縺','繧','舌','輔','＜','＞','＠','ｂ','ｅ','ｆ','ｊ','ｌ','ｐ','ｒ','｢','｣','､','･','ｦ','ｧ','ｨ','ｩ','ｪ','ｫ','ｬ','ｭ','ｮ','ｯ','ｰ','ｱ','ｲ','ｳ','ｴ','ｵ','ｶ','ｷ','ｸ','ｹ','ｺ','ｻ','ｼ','ｽ','ｾ','ｿ','￠','￡'};

    @Inject(at = @At("TAIL"), method = "render")
    public void bugModRender(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        BugModHUD.renderRedScreen(matrixStack);
        BugModHUD.renderBreakScreen(matrixStack);
        BugModHUD.renderBlackScreen(matrixStack);
    }

    @ModifyVariable(at = @At(value = "HEAD"), method = "addChatMessage")
    private Text bugModChatMessage(Text message, MessageType type) {
        if (type == MessageType.CHAT) {
            // exp: <Player90> aaa
            String[] message_info = message.getString().split(" ");
            //message部分だけを取得する
            List<String> split_message_info = new ArrayList(Arrays.asList(message_info));
            split_message_info.remove(0);

            // メッセージ格納
            String user_message = String.join(" ", split_message_info);

            // バグらせる割合計算
            int ratio = (int)(user_message.length()*0.5);
            List<Integer> arr = IntStream.rangeClosed(0, user_message.length()-1).boxed().collect(Collectors.toList());
            Collections.shuffle(arr);
            StringBuilder sb_message = new StringBuilder(user_message);

            // 決められた割合分文字列をバグらせる
            Random rnd = new Random();
            for (int i=0; i<=ratio; i++){
                sb_message.setCharAt(arr.get(i), chars[rnd.nextInt(chars.length)]);
            }
            message = Text.of(message_info[0] + " " + sb_message);
        }
        return message;
    }
}
