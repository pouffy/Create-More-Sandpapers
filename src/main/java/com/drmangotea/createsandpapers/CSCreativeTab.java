package com.drmangotea.createsandpapers;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.AllItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

public class CSCreativeTab {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateSandpapers.ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BASE_CREATIVE_TAB = REGISTER.register("base", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + CreateSandpapers.ID))
            .icon(ModSandpapers.BLACK.getItem()::asStack)
            .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getId())
            .displayItems((displayParameters, output) -> {
                output.accept(AllItems.RED_SAND_PAPER);
                output.accept(AllItems.SAND_PAPER);
            })
            .build());

    @ApiStatus.Internal
    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
