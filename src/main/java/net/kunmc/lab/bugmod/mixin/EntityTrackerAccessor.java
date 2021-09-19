package net.kunmc.lab.bugmod.mixin;

import net.minecraft.server.network.EntityTrackerEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.server.world.ThreadedAnvilChunkStorage$EntityTracker")
public interface EntityTrackerAccessor {
    @Accessor
    EntityTrackerEntry getEntry();
}