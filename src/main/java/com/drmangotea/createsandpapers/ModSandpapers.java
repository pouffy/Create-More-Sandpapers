package com.drmangotea.createsandpapers;

import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public enum ModSandpapers {
    SOUL,
    PINK,
    VIOLET,
    BLUE,
    WHITE,
    QUARTZITE,
    BLACK,
    WINDSWEPT,
    ORANGE,
    GREEN,
    BROWN,
    SOULLESS,
    CORAL,
    FOAMY,
    MINERAL,
    ARID,
    RED_ARID,
    MARS,
    MOON,
    VENUS
    ;

    public final ItemEntry<SandPaperItem> SAND_PAPER;
    
    ModSandpapers() {
        CSRegistrate reg = CreateSandpapers.REGISTRATE.setCreativeTab(CSCreativeTab.BASE_CREATIVE_TAB);
        SAND_PAPER = reg.sandPaperItem(name().toLowerCase());
    }

    
    public String getName() {
        return this.name().toLowerCase();
    }
    
    public ItemEntry<SandPaperItem> getItem() {
        return SAND_PAPER;
    }
    
    public SandPaperItem getSandPaperItem() {
        return SAND_PAPER.get();
    }

    @SubscribeEvent
    public static void addTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CSCreativeTab.BASE_CREATIVE_TAB.get()) {
            for (ModSandpapers sandpapers: ModSandpapers.values()) {
                if (!event.getTab().contains(sandpapers.getItem().asStack())) {
                    event.accept(sandpapers.getItem().asStack(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                }
            }
        }
    }

    public static void register() {
    }
}
