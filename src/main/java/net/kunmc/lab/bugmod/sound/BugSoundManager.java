package net.kunmc.lab.bugmod.sound;

import net.kunmc.lab.bugmod.BugMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BugSoundManager {
    // なぜかbugmod以下だと音声が鳴らない（何か競合している？）、soundは別のnamespaceに分けておく
    public static final Identifier noiseId = new Identifier(BugMod.MODID, "noise");
    public static SoundEvent noiseSound = new SoundEvent(noiseId);

    public static final Identifier helpId = new Identifier(BugMod.MODID, "help");
    public static SoundEvent helpSound = new SoundEvent(helpId);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, BugSoundManager.noiseId, noiseSound);
        Registry.register(Registry.SOUND_EVENT, BugSoundManager.helpId, helpSound);
    }
}
