package com.drmangotea.createsandpapers.data;

import com.drmangotea.createsandpapers.CSRegistrate;
import com.drmangotea.createsandpapers.CreateSandpapers;
import com.drmangotea.createsandpapers.ModSandpapers;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
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
        PackOutput packOutput = generator.getPackOutput();
        boolean client = event.includeClient();
        boolean server = event.includeServer();
        
        generator.addProvider(server, new CSCraftingProvider(packOutput));
    }
    
    private static void addExtraRegistrateData() {
        CreateSandpapers.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            CSRegistrate.provideSandpaperLang(langConsumer);
            langConsumer.accept("itemGroup." + CreateSandpapers.ID, "Create: More Sand Papers");
        });
    }
    
    public static class CSCraftingProvider extends RecipeProvider {
        
        public CSCraftingProvider(PackOutput pOutput) {
            super(pOutput);
        }
        
        @Override
        protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
            ModSandpapers[] sandpapers = ModSandpapers.values();
            for (ModSandpapers sandpaper : sandpapers) {
                sandPaperRecipe(sandpaper.getName().toLowerCase(), consumer);
            }
        }
        
        protected static void sandPaperRecipe(String name, Consumer<FinishedRecipe> consumer) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, CSRegistrate.getSandpaper(name))
                    .requires(Items.PAPER)
                    .requires(CSRegistrate.makesSandpaper(name))
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(CSRegistrate.makesSandpaper(name)))
                    .save(consumer, CreateSandpapers.asResource("crafting/" + name + "_sand_paper"));
        }
    }
}
