package com.drmangotea.createsandpapers;

import com.drmangotea.createsandpapers.data.CSDatagen;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

import static com.simibubi.create.foundation.item.TooltipHelper.styleFromColor;

@Mod("createsandpapers")
public class CreateSandpapers
{
    public static final String ID = "createsandpapers";
    public static final CSRegistrate REGISTRATE = CSRegistrate.create();
    public static final FontHelper.Palette CS_PALETTE = new FontHelper.Palette(styleFromColor(0xd8b395), styleFromColor(0xe8dec8));
    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, CS_PALETTE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }
    
    public CreateSandpapers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);
        CreativeModeTabs.register(modEventBus);
        modEventBus.addListener(EventPriority.LOWEST, CSDatagen::gatherData);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }
    
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CreativeModeTabs {
        private static final DeferredRegister<CreativeModeTab> REGISTER;
        public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB;
        
        static {
            ModSandpapers[] sandpapers = ModSandpapers.values();
            REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateSandpapers.ID);
            BASE_CREATIVE_TAB = REGISTER.register("base", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + CreateSandpapers.ID))
                    .icon(ModSandpapers.BLACK.getItem()::asStack)
                    .displayItems((enabledFeatures, entries) -> Arrays.stream(sandpapers).toList().forEach(sandpaper ->
                            entries.accept(sandpaper.getSandPaperItem()))
                    ).build());
        }
        
        public static void register(IEventBus modEventBus) {
            REGISTER.register(modEventBus);
        }
        
    }
}
