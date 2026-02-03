package com.drmangotea.createsandpapers.data;

import com.drmangotea.createsandpapers.ModSandpapers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SandpaperIngredients {
    public static Map<ModSandpapers, SandPaperIngredient> sandPaperIngredients = new HashMap<>();

    // Easier to manage the ingredients for sandpaper in this.
    public static Map<ModSandpapers, SandPaperIngredient> collect() {
        ingredient(ModSandpapers.SOUL)
                .commonType().addMod("minecraft")
                .finish(toMap());
        ingredient(ModSandpapers.PINK)
                .commonType(true)
                .addMod("biomeswevegone")
                .addMod("natures_spirit")
                .finish(toMap());
        ingredient(ModSandpapers.VIOLET)
                .name("purple")
                .commonType()
                .addMod("biomeswevegone")
                .finish(toMap());
        ingredient(ModSandpapers.BLUE)
                .commonType()
                .addMod("biomeswevegone")
                .finish(toMap());
        ingredient(ModSandpapers.WHITE)
                .commonType(true)
                .addMod("biomeswevegone")
                .addMod("biomesoplenty")
                .finish(toMap());
        ingredient(ModSandpapers.QUARTZITE)
                .commonType()
                .addMod("byg")
                .finish(toMap());
        ingredient(ModSandpapers.BLACK)
                .commonType(true)
                .addMod("biomeswevegone")
                .addMod("biomesoplenty")
                .finish(toMap());
        ingredient(ModSandpapers.WINDSWEPT)
                .commonType()
                .addMod("biomeswevegone")
                .addMod("byg")
                .finish(toMap());
        ingredient(ModSandpapers.ORANGE)
                .commonType()
                .addMod("biomesoplenty")
                .finish(toMap());
        ingredient(ModSandpapers.GREEN)
                .commonType(true)
                .finish(toMap());
        ingredient(ModSandpapers.BROWN)
                .commonType(true)
                .finish(toMap());
        ingredient(ModSandpapers.SOULLESS)
                .commonType()
                .addMod("forbidden_arcanus")
                .finish(toMap());
        ingredient(ModSandpapers.CORAL)
                .commonType()
                .addMod("tropicraft")
                .finish(toMap());
        ingredient(ModSandpapers.FOAMY)
                .commonType()
                .addMod("tropicraft")
                .finish(toMap());
        ingredient(ModSandpapers.MINERAL)
                .commonType()
                .addMod("tropicraft")
                .finish(toMap());
        ingredient(ModSandpapers.ARID)
                .commonType()
                .addMod("atmospheric")
                .finish(toMap());
        ingredient(ModSandpapers.RED_ARID)
                .commonType()
                .addMod("atmospheric")
                .finish(toMap());
        ingredient(ModSandpapers.MARS)
                .commonType()
                .addMod("ad_astra")
                .finish(toMap());
        ingredient(ModSandpapers.MOON)
                .commonType()
                .addMod("ad_astra")
                .finish(toMap());
        ingredient(ModSandpapers.VENUS)
                .commonType()
                .addMod("ad_astra")
                .finish(toMap());

        return sandPaperIngredients;
    }

    public static SandPaperIngredient ingredient(ModSandpapers type) {
        return new SandPaperIngredient(type);
    }

    private static BiConsumer<ModSandpapers, SandPaperIngredient> toMap() {
        return (type, ingredient) -> sandPaperIngredients.put(type, ingredient);
    }
}
