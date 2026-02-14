package net.marioood.bbiotec.datagen;

import net.marioood.bbiotec.ModBlocks;
import net.marioood.bbiotec.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashMap;

public class IrradiatorRecipes {
    public static ArrayList<HashMap<Item, Integer>> inputs = new ArrayList<>();
    public static ArrayList<ItemStack> outputs = new ArrayList<>();

    static {
        register(
            new ItemStack(ModBlocks.TENDRILDENDRON.get()),
            ModItems.CELLULOSE.get(), 1,
            ModItems.LIPIDS.get(), 1,
            ModItems.PROTEINS.get(), 2
        );
        register(
            new ItemStack(ModBlocks.GIANT_FLOWER_STEM.get()),
            Blocks.POPPY, 4
        );
    }

    private static void register(ItemStack output, Object... ingredients) {
        HashMap<Item, Integer> ingredientsMap = new HashMap<>();
        for(int i = 0; i < ingredients.length; i += 2) {
            ingredientsMap.put(((ItemLike)ingredients[i]).asItem(), (Integer)ingredients[i + 1]);
        }

        inputs.add(ingredientsMap);
        outputs.add(output);
    }

    public static ItemStack getOutput(HashMap<Item, Integer> input) {
        for(int i = 0; i < inputs.size(); i++) {
            if(input.equals(inputs.get(i))) {
                return outputs.get(i);
            }
        }
        return null;
    }
}
