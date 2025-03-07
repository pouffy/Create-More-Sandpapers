package com.drmangotea.createsandpapers;

import com.drmangotea.createsandpapers.data.CSDatagen;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;


import java.util.Arrays;

import static com.simibubi.create.foundation.item.TooltipHelper.styleFromColor;

@Mod("createsandpapers")
public class CreateSandpapers
{
    public static final String ID = "createsandpapers";
    public static final CSRegistrate REGISTRATE = CSRegistrate.create();
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final FontHelper.Palette CS_PALETTE = new FontHelper.Palette(styleFromColor(0xd8b395), styleFromColor(0xe8dec8));
    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, CS_PALETTE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }
    
    public CreateSandpapers(IEventBus eventBus, ModContainer modContainer) {
        REGISTRATE.registerEventListeners(eventBus);

        ModSandpapers.register();
        CSCreativeTab.register(eventBus);

        eventBus.addListener(EventPriority.LOWEST, CSDatagen::gatherData);
    }
    
    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
