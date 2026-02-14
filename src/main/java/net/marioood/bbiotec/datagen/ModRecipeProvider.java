package net.marioood.bbiotec.datagen;

import net.marioood.bbiotec.BBioTec;
import net.marioood.bbiotec.ModBlocks;
import net.marioood.bbiotec.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    private static int safetyShearsRecipes = 0;

    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput out) {
        safetyShears(out, Items.HONEYCOMB, ModItems.LIPIDS.get(), 1);
        safetyShears(out, Blocks.HONEYCOMB_BLOCK, ModItems.LIPIDS.get(), 4);
        safetyShears(out, Blocks.PUMPKIN, ModItems.LIPIDS.get(), 4);
        safetyShears(out, Items.GOLD_INGOT, ModItems.LIPIDS.get(), 1);
        safetyShears(out, Blocks.GOLD_BLOCK, ModBlocks.CLUMP_OF_LIPIDS.get(), 1);

        safetyShears(out, Items.APPLE, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.CARROT, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.BEETROOT, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.MELON_SLICE, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.SUGAR_CANE, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.SWEET_BERRIES, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.FERMENTED_SPIDER_EYE, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.CHORUS_FRUIT, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.GLOW_BERRIES, ModItems.GLUCOSE_SYRUP.get(), 1);
        safetyShears(out, Items.HONEY_BOTTLE, ModItems.GLUCOSE_SYRUP.get(), 4);
        safetyShears(out, Blocks.HONEY_BLOCK, ModItems.GLUCOSE_SYRUP.get(), 16);

        safetyShears(out, Blocks.BROWN_MUSHROOM, ModItems.PROTEINS.get(), 1);
        safetyShears(out, Blocks.RED_MUSHROOM, ModItems.PROTEINS.get(), 1);
        safetyShears(out, Items.EGG, ModItems.PROTEINS.get(), 1);

        //TODO ore block
    }

    private void safetyShears(RecipeOutput out, ItemLike input, ItemLike output, int count) {
        String uniqueId = BBioTec.MODID + ":from_safety_shears_" + safetyShearsRecipes;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count)
                .requires(input)
                .requires(ModItems.SAFETY_SHEARS.get())
                .unlockedBy(getHasName(ModItems.SAFETY_SHEARS.get()), has(ModItems.SAFETY_SHEARS.get()))
                .save(out, uniqueId);

        safetyShearsRecipes++;
    }
}
