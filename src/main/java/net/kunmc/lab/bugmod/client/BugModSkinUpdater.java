//package net.kunmc.lab.bugmod.client;
//
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.network.AbstractClientPlayerEntity;
//import net.minecraft.client.texture.AbstractTexture;
//import net.minecraft.client.texture.PlayerSkinTexture;
//import net.minecraft.client.texture.TextureManager;
//import net.minecraft.client.util.DefaultSkinHelper;
//import net.minecraft.util.ChatUtil;
//import net.minecraft.util.Identifier;
//
//public class BugModSkinUpdater {
//    public static void updatePlayerSkin(String playerName){
//        System.out.println(playerName);
//        Identifier skinId = AbstractClientPlayerEntity.getSkinId(playerName);
//
//        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
//        AbstractTexture abstractTexture = new PlayerSkinTexture(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", ChatUtil.stripTextFormat(playerName)), DefaultSkinHelper.getTexture(AbstractClientPlayerEntity.getOfflinePlayerUuid(playerName)), true, null);
//        MinecraftClient.getInstance().getTextureManager().registerTexture(skinId, abstractTexture);
//    }
//}