package com.drmangotea.createsandpapers;

import biomesoplenty.init.ModItems;
import com.drmangotea.createsandpapers.data.CSDatagen;
import com.mojang.logging.LogUtils;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static com.simibubi.create.foundation.item.TooltipHelper.styleFromColor;

@Mod("createsandpapers")
public class CreateSandpapers
{
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "createsandpapers";
    public static final CSRegistrate REGISTRATE = CSRegistrate.create();
    
    public static final TooltipHelper.Palette CS_PALETTE = new TooltipHelper.Palette(styleFromColor(0xd8b395), styleFromColor(0xe8dec8));
    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, CS_PALETTE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }
    
    public static final CreativeModeTab itemGroup = new CreativeModeTab(ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(CSRegistrate.getSandpaper("black"));
        }
    };
    
    public CreateSandpapers()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CSItems.register();
        REGISTRATE.registerEventListeners(modEventBus);
        modEventBus.addListener(EventPriority.LOWEST, CSDatagen::gatherData);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        CSRegistrate.printSandpapers();
    }
    
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }
    
    public static @NotNull CSRegistrate registrate() {
        return REGISTRATE;
    }
}
