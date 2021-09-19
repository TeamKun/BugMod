package net.kunmc.lab.bugmod.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.fabric.mixin.networking.accessor.EntityTrackerAccessor;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ThreadedAnvilChunkStorage.class)
public interface ThreadedAnvilChunkStorageAccessor {
    @Accessor
    Int2ObjectMap<EntityTrackerAccessor> getEntityTrackers();
}
