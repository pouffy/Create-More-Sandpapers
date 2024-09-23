package com.drmangotea.createsandpapers;

import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.tterrag.registrate.util.entry.ItemEntry;

public class CSItems {
    static CSRegistrate reg = (CSRegistrate) CreateSandpapers.registrate().creativeModeTab(() -> CreateSandpapers.itemGroup);
    
    public static final ItemEntry<SandPaperItem>
        SOUL_SAND_PAPER = reg.sandPaperItem("soul"),
        PINK_SAND_PAPER = reg.sandPaperItem("pink"),
        VIOLET_SAND_PAPER = reg.sandPaperItem("violet"),
        BLUE_SAND_PAPER = reg.sandPaperItem("blue"),
        WHITE_SAND_PAPER = reg.sandPaperItem("white"),
        QUARTZITE_SAND_PAPER = reg.sandPaperItem("quartzite"),
        BLACK_SAND_PAPER = reg.sandPaperItem("black"),
        WINDSWEPT_SAND_PAPER = reg.sandPaperItem("windswept"),
        ORANGE_SAND_PAPER = reg.sandPaperItem("orange"),
        GREEN_SAND_PAPER = reg.sandPaperItem("green"),
        BROWN_SAND_PAPER = reg.sandPaperItem("brown"),
        SOULLESS_SAND_PAPER = reg.sandPaperItem("soulless"),
        CORAL_SAND_PAPER = reg.sandPaperItem("coral"),
        FOAMY_SAND_PAPER = reg.sandPaperItem("foamy"),
        MINERAL_SAND_PAPER = reg.sandPaperItem("mineral"),
        ARID_SAND_PAPER = reg.sandPaperItem("arid"),
        RED_ARID_SAND_PAPER = reg.sandPaperItem("red_arid"),
        MARS_SAND_PAPER = reg.sandPaperItem("mars"),
        MOON_SAND_PAPER = reg.sandPaperItem("moon"),
        VENUS_SAND_PAPER = reg.sandPaperItem("venus")
                ;
    
    
    
    public static void register() {
        CSRegistrate.printSandpapers();
    }
}
