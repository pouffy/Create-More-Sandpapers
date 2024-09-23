package com.drmangotea.createsandpapers.data;

import com.drmangotea.createsandpapers.CSRegistrate;
import com.drmangotea.createsandpapers.CreateSandpapers;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CSDatagen {
    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();
        
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        
        boolean client = event.includeClient();
        boolean server = event.includeServer();
        generator.addProvider(server, new CSCraftingProvider(generator));
    }
    
    private static void addExtraRegistrateData() {
        CreateSandpapers.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            CSRegistrate.provideSandpaperLang(langConsumer);
            langConsumer.accept("itemGroup." + CreateSandpapers.ID, "Create: More Sand Papers");
        });
    }
    
    public static class CSCraftingProvider extends RecipeProvider {
        
        public CSCraftingProvider(DataGenerator generator) {
            super(generator);
        }
        
        @Override
        protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
            for (String sandpaper : CSRegistrate.getSandpaperLang()) {
                sandPaperRecipe(sandpaper, consumer);
            }
        }
        
        protected static void sandPaperRecipe(String name, Consumer<FinishedRecipe> consumer) {
            ShapelessRecipeBuilder.shapeless(CSRegistrate.getSandpaper(name))
                    .requires(Items.PAPER)
                    .requires(CSRegistrate.makesSandpaper(name))
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(CSRegistrate.makesSandpaper(name)))
                    .save(consumer, CreateSandpapers.asResource("crafting/" + name + "_sand_paper"));
        }
    }
}
