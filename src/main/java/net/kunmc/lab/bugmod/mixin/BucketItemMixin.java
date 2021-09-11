package net.kunmc.lab.bugmod.mixin;

import net.kunmc.lab.bugmod.networking.ClientNetworking;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BucketItem.class)
public class BucketItemMixin {
    @Inject(at = @At("HEAD"), method = "onEmptied", cancellable = true)
    public void onUseItem(World world, ItemStack stack, BlockPos pos, CallbackInfo ci) {
        if (stack.getItem() == Items.WATER_BUCKET ||
                stack.getItem() == Items.LAVA_BUCKET) {
            ClientNetworking.sendBreakScreenLevel();
        }
    }
}