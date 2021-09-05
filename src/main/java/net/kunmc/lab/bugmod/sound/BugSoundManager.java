package net.kunmc.lab.bugmod.sound;

import net.kunmc.lab.bugmod.BugMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BugSoundManager {
    public static final Identifier noiseId = new Identifier(BugMod.MODID, "noise");
    public static SoundEvent noiseSound = new SoundEvent(noiseId);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, BugSoundManager.noiseId, noiseSound);
    }
}
