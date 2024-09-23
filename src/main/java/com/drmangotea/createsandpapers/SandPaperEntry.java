package com.drmangotea.createsandpapers;

import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import java.util.function.BiConsumer;

public class SandPaperEntry {
    private final String name;
    public final ItemEntry<SandPaperItem> item;
    
    public SandPaperEntry(CSRegistrate reg, String name) {
        this.name = name;
        this.item = reg.sandPaperItem(name);
    }
    
    public String getName() {
        return name;
    }
    
    public ItemEntry<SandPaperItem> getItem() {
        return item;
    }
    
    public void provideSandpaperLang(BiConsumer<String, String> consumer) {
        consumer.accept("item.createsandpapers." + getName() + "_sand_paper.tooltip.summary", "Can be used to _refine materials_. The process can be automated with a Deployer.");
        consumer.accept("item.createsandpapers." + getName() + "_sand_paper.tooltip.condition1", "When Used");
        consumer.accept("item.createsandpapers." + getName() + "_sand_paper.tooltip.behaviour1", "Applies polish to items held in the _offhand_ or lying on the _floor_ when _looking at them_");
    }
}
