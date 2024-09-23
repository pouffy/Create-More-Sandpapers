package com.drmangotea.createsandpapers;

import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.tterrag.registrate.util.entry.ItemEntry;

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
}
