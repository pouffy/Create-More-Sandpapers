package com.drmangotea.createsandpapers;

import javax.annotation.Nonnull;

import com.simibubi.create.Create;

import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

@JeiPlugin
@SuppressWarnings("unused")
public class CreateSandpapersJEI implements IModPlugin {
    private final ResourceLocation spPolishing = new ResourceLocation("create", "sandpaper_polishing");


    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        Arrays.stream(ModSandpapers.values()).toList().forEach(sandpaper -> registerPolishingCatalyst(registration, sandpaper.getSandPaperItem()));
    }
    
    private void registerPolishingCatalyst(IRecipeCatalystRegistration registration, Item item) {
        registration.getJeiHelpers().getRecipeType(spPolishing).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(item), type));
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return Create.asResource("jei_plugin");
    }


}