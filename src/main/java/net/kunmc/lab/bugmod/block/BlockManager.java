package net.kunmc.lab.bugmod.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.game.GameManager;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockManager {
    public static final String bugStone = "bug_stone";
    private static BugStoneBlock bugStoneBlock = new BugStoneBlock(FabricBlockSettings.of(Material.STONE).hardness(0.4f));

    public static void register(){
        Registry.register(net.minecraft.util.registry.Registry.BLOCK, identifierFactory(bugStone), bugStoneBlock);
        Registry.register(Registry.ITEM, new Identifier(BugMod.MODID, bugStone), new BlockItem(bugStoneBlock, new Item.Settings().group(ItemGroup.MISC)));
    }

    public static Identifier identifierFactory(String name) {
        switch (name) {
            case bugStone:
                return new Identifier(BugMod.MODID, bugStone);
        }
        return null;
    }
}
