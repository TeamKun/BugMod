package net.kunmc.lab.bugmod.game;

import it.unimi.dsi.fastutil.Hash;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// サーバ側で各プレイヤーのレベルを持つ変数
public class PlayerGameManager {
    // Map<playerName, Map<bugName, buglevel>> の想定
    public static Map<String, Map<String, Integer>> playersBugLevel = new HashMap();
}
