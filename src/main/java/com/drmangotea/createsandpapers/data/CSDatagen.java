package com.drmangotea.createsandpapers.data;

import com.drmangotea.createsandpapers.CSRegistrate;
import com.drmangotea.createsandpapers.CreateSandpapers;
import com.drmangotea.createsandpapers.ModSandpapers;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.infrastructure.data.CreateDatagen;
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
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.drmangotea.createsandpapers.CSRegistrate.modItemTag;

public class CSDatagen {

    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(CreateSandpapers.ID))
            addExtraRegistrateData();
    }

    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(CreateSandpapers.ID))
            return;

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        //Why is this necessary???
        var blockTags = new BlockTagsProvider(output, lookupProvider, CreateSandpapers.ID, existingFileHelper) {@Override protected void addTags(HolderLookup.Provider provider) {}};
        generator.addProvider(event.includeServer(), blockTags);

        generator.addProvider(event.includeServer(), new CSItemTagProvider(output, lookupProvider, blockTags.contentsGetter()));
        generator.addProvider(event.includeServer(), new CSCraftingProvider(output, lookupProvider));
    }

    private static void addExtraRegistrateData() {
        CreateSandpapers.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            CSRegistrate.provideSandpaperLang(langConsumer);
            langConsumer.accept("itemGroup." + CreateSandpapers.ID, "Create: More Sand Papers");
        });
    }

    private static final TagKey<Item> createsSandpaper = modItemTag("createsandpapers", "creates_sandpaper");
    private static final TagKey<Item> createsRedSandpaper = modItemTag("createsandpapers", "creates_red_sandpaper");
    
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

            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AllItems.SAND_PAPER)
                    .requires(Items.PAPER)
                    .requires(createsSandpaper)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(createsSandpaper))
                    .save(recipeOutput.withConditions(new NotCondition(new TagEmptyCondition(createsSandpaper))), CreateSandpapers.asResource("crafting/sand_paper"));
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AllItems.RED_SAND_PAPER)
                    .requires(Items.PAPER)
                    .requires(createsRedSandpaper)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(createsRedSandpaper))
                    .save(recipeOutput.withConditions(new NotCondition(new TagEmptyCondition(createsRedSandpaper))), CreateSandpapers.asResource("crafting/red_sand_paper"));
        }
        
        protected static void sandPaperRecipe(String name, RecipeOutput recipeOutput) {
            TagKey<Item> sandTag = CSRegistrate.makesSandpaper(name);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, CSRegistrate.getSandpaper(name))
                    .requires(Items.PAPER)
                    .requires(sandTag)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(sandTag))
                    .save(recipeOutput.withConditions(new NotCondition(new TagEmptyCondition(sandTag))), CreateSandpapers.asResource("crafting/" + name + "_sand_paper"));
        }
    }

    public static class CSItemTagProvider extends ItemTagsProvider {

        public CSItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
            super(output, lookupProvider, blockTags);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            Map<ModSandpapers, SandPaperIngredient> ingredients = SandpaperIngredients.collect();
            for (var entry : ingredients.entrySet()) {
                SandPaperIngredient ingredient = entry.getValue();
                String name = entry.getKey().getName();
                TagKey<Item> sandTag = CSRegistrate.makesSandpaper(name);
                var appender = tag(sandTag);
                ingredient.getItems().forEach(appender::addOptional);
                ingredient.getTags().forEach(appender::addOptionalTag);
            }

            // Default Create Sandpapers
            tag(createsSandpaper)
                    .addOptionalTag(ResourceLocation.parse("c:sands/colorless"))
                    .addOptional(ResourceLocation.parse("tfc:sand/yellow"));
            tag(createsRedSandpaper)
                    .addOptionalTag(ResourceLocation.parse("c:sands/red"))
                    .addOptional(ResourceLocation.parse("tfc:sand/red"));
        }
    }
}
