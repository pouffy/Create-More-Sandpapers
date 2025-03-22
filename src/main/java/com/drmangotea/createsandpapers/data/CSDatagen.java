package com.drmangotea.createsandpapers.data;

import com.drmangotea.createsandpapers.CSRegistrate;
import com.drmangotea.createsandpapers.CreateSandpapers;
import com.drmangotea.createsandpapers.ModSandpapers;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class CSDatagen {
    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();
        
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        boolean client = event.includeClient();
        boolean server = event.includeServer();
        
        generator.addProvider(server, new CSCraftingProvider(packOutput, lookupProvider));

        event.getGenerator().addProvider(true, CreateSandpapers.REGISTRATE.setDataProvider(new RegistrateDataProvider(CreateSandpapers.REGISTRATE, CreateSandpapers.ID, event)));
    }

    private static void addExtraRegistrateData() {
        CreateSandpapers.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            CSRegistrate.provideSandpaperLang(langConsumer);
            langConsumer.accept("itemGroup." + CreateSandpapers.ID, "Create: More Sand Papers");
        });
    }
    
    public static class CSCraftingProvider extends RecipeProvider {
        
        public CSCraftingProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(pOutput, registries);
        }
        
        @Override
        protected void buildRecipes(RecipeOutput recipeOutput) {
            ModSandpapers[] sandpapers = ModSandpapers.values();
            for (ModSandpapers sandpaper : sandpapers) {
                sandPaperRecipe(sandpaper.getName().toLowerCase(), recipeOutput);
            }
        }
        
        protected static void sandPaperRecipe(String name, RecipeOutput recipeOutput) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, CSRegistrate.getSandpaper(name))
                    .requires(Items.PAPER)
                    .requires(CSRegistrate.makesSandpaper(name))
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(CSRegistrate.makesSandpaper(name)))
                    .save(recipeOutput, CreateSandpapers.asResource("crafting/" + name + "_sand_paper"));
        }
    }
}
