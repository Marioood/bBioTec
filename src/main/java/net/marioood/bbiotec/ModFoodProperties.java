package net.marioood.bbiotec;

import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties BARBEQUE_SANDWICH =
            new FoodProperties.Builder().nutrition(4).saturationModifier(0.8f).build();

    public static final FoodProperties GLUCOSE_SYRUP =
            new FoodProperties.Builder().nutrition(1).saturationModifier(0.05f).build();
}
