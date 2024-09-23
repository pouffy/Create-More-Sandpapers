package com.drmangotea.createsandpapers;

import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

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
    
    public final SandPaperEntry SAND_PAPER;
    
    ModSandpapers() {
        CSRegistrate reg = CreateSandpapers.REGISTRATE;
        SAND_PAPER = reg.sandPaper(name().toLowerCase());
    }
    
    public SandPaperEntry getSandPaper() {
        return SAND_PAPER;
    }
    
    public String getName() {
        return this.name().toLowerCase();
    }
    
    public ItemEntry<SandPaperItem> getItem() {
        return SAND_PAPER.getItem();
    }
    
    public SandPaperItem getSandPaperItem() {
        return SAND_PAPER.getItem().get();
    }
}
