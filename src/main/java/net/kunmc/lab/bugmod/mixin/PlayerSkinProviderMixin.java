package net.kunmc.lab.bugmod.mixin;


import com.google.common.hash.Hashing;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.client.texture.PlayerSkinTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;

@Environment(EnvType.CLIENT)
@Mixin(PlayerSkinProvider.class)
public class PlayerSkinProviderMixin {
    @Shadow
    @Final
    private TextureManager textureManager;
    @Shadow
    @Final
    private File skinCacheDir;

    /**
     * @param profileTexture
     * @param type
     * @param callback
     * @return
     * @author POne0301
     * @reason hoge
     */
    @Overwrite()
    private Identifier loadSkin(MinecraftProfileTexture profileTexture, MinecraftProfileTexture.Type type, @Nullable PlayerSkinProvider.SkinTextureAvailableCallback callback) {
        String string = Hashing.sha1().hashUnencodedChars(profileTexture.getHash()).toString();
        Identifier identifier = new Identifier("skins/" + string);
        File file = new File(this.skinCacheDir, string.length() > 2 ? string.substring(0, 2) : "xx");
        File file2 = new File(file, string);
        PlayerSkinTexture playerSkinTexture = new PlayerSkinTexture(file2, profileTexture.getUrl(), DefaultSkinHelper.getTexture(), type == MinecraftProfileTexture.Type.SKIN, () -> {
            if (callback != null) {
                callback.onSkinTextureAvailable(type, identifier, profileTexture);
            }
        });
        this.textureManager.registerTexture(identifier, playerSkinTexture);
        return identifier;
    }
}
