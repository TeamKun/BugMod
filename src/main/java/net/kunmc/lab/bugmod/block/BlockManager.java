package net.kunmc.lab.bugmod.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kunmc.lab.bugmod.BugMod;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockManager {
    public static final String bugBlock1Name = "bug_block1";
    private static BugBlock1 bugBlock1 = new BugBlock1(FabricBlockSettings.of(Material.ICE));

    public static final String bugBlock2Name = "bug_block2";
    private static BugBlock1 bugBlock2 = new BugBlock1(FabricBlockSettings.of(Material.ICE));

    public static final String bugBlock3Name = "bug_block3";
    private static BugBlock1 bugBlock3 = new BugBlock1(FabricBlockSettings.of(Material.ICE));

    public static void register(){
        Registry.register(net.minecraft.util.registry.Registry.BLOCK, identifierFactory(bugBlock1Name), bugBlock1);
        Registry.register(Registry.ITEM, new Identifier(BugMod.MODID, bugBlock1Name), new BlockItem(bugBlock1, new Item.Settings().group(ItemGroup.MISC)));

        Registry.register(net.minecraft.util.registry.Registry.BLOCK, identifierFactory(bugBlock2Name), bugBlock2);
        Registry.register(Registry.ITEM, new Identifier(BugMod.MODID, bugBlock2Name), new BlockItem(bugBlock2, new Item.Settings().group(ItemGroup.MISC)));

        Registry.register(net.minecraft.util.registry.Registry.BLOCK, identifierFactory(bugBlock3Name), bugBlock3);
        Registry.register(Registry.ITEM, new Identifier(BugMod.MODID, bugBlock3Name), new BlockItem(bugBlock3, new Item.Settings().group(ItemGroup.MISC)));
    }

    public static Identifier identifierFactory(String name) {
        switch (name) {
            case bugBlock1Name:
                return new Identifier(BugMod.MODID, bugBlock1Name);
            case bugBlock2Name:
                return new Identifier(BugMod.MODID, bugBlock2Name);
            case bugBlock3Name:
                return new Identifier(BugMod.MODID, bugBlock3Name);
        }
        return null;
    }
}
