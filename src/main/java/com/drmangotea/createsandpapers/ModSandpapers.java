package com.drmangotea.createsandpapers;

import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.tterrag.registrate.util.entry.ItemEntry;

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
    
    //public final SandPaperEntry SAND_PAPER;
    
    ModSandpapers() {
        CSRegistrate reg = (CSRegistrate) CreateSandpapers.registrate().creativeModeTab(() -> CreateSandpapers.itemGroup);
        //SAND_PAPER = reg.sandPaper(getName());
    }
    
    public String getName() {
        return this.name().toLowerCase();
    }
    
    //public SandPaperItem getSandPaperItem() {
    //    return SAND_PAPER.getItem().get();
    //}
}
