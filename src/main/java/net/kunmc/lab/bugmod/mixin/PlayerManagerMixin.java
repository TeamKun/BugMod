package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.network.MessageType;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    public abstract void sendToAll(Packet<?> packet);

    /**
     * @author POne0301
     * @reason bugmod
     */
    @Overwrite
    public void broadcastChatMessage(Text message, MessageType type, UUID sender) {

        // よくある文字化けの文字
        char[] chars = {'?', '§', '℃', '√', '∞', '∴', '■', '○', '●', 'ゅ', '上', '九', '代', '吶', '壹', '峨', '後', '悶', '溘', '縺', '繧', '舌', '輔', '＜', '＞', '＠', 'ｂ', 'ｅ', 'ｆ', 'ｊ', 'ｌ', 'ｐ', 'ｒ', '｢', '｣', '､', '･', 'ｦ', 'ｧ', 'ｨ', 'ｩ', 'ｪ', 'ｫ', 'ｬ', 'ｭ', 'ｮ', 'ｯ', 'ｰ', 'ｱ', 'ｲ', 'ｳ', 'ｴ', 'ｵ', 'ｶ', 'ｷ', 'ｸ', 'ｹ', 'ｺ', 'ｻ', 'ｼ', 'ｽ', 'ｾ', 'ｿ', '￠', '￡'};
        if (GameManager.runningMode == GameManager.GameMode.MODE_START && type == MessageType.CHAT && !BugMod.minecraftServerInstance.getPlayerManager().getPlayer(sender).isSpectator()) {
            // exp: <Player90> aaa
            TranslatableText text = (TranslatableText) message;
            String playerName = ((Text) text.getArgs()[0]).asString();
            String textMessage = ((String) text.getArgs()[1]);

            // バグらせる文字数計算
            //  レベル分だけ文字化けする
            int num = GameManager.getPlayerBugLevel(BugMod.minecraftServerInstance.getPlayerManager().getPlayer(sender).getEntityName(), GameManager.garbledCharName);
            if (num == GameManager.garbledCharMaxLevel) {
                num = textMessage.length();
            }
            num = Math.min(num, textMessage.length());

            List<Integer> arr = IntStream.rangeClosed(0, textMessage.length() - 1).boxed().collect(Collectors.toList());
            Collections.shuffle(arr);
            StringBuilder sb_message = new StringBuilder(textMessage);

            // 決められた文字数文をバグらせる
            for (int i = 0; i < num; i++) {
                sb_message.setCharAt(arr.get(i), chars[GameManager.rand.nextInt(chars.length)]);
                message = new LiteralText("<" + playerName + "> " + sb_message);
            }
        }

        this.server.sendSystemMessage(message, sender);
        this.sendToAll(new GameMessageS2CPacket(message, type, sender));
    }
}
