package com.drmangotea.createsandpapers;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class CSRegistrate extends CreateRegistrate {
    private static final List<String> sandpaperLang = new ArrayList<>();
    protected CSRegistrate() {
        super(CreateSandpapers.ID);
    }
    
    public static void printSandpapers() {
        for (String sandpaper : getSandpaperLang()) {
            CreateSandpapers.LOGGER.info("Created '{} sand paper'", sandpaper);
        }
    }
    
    public static List<String> getSandpaperLang() {
        return sandpaperLang;
    }
    
    public static String autoLang(String id) {
        StringBuilder builder = new StringBuilder();
        boolean b = true;
        for (char c: id.toCharArray()) {
            if(c == '_') {
                builder.append(' ');
                b = true;
            } else {
                builder.append(b ? String.valueOf(c).toUpperCase() : c);
                b = false;
            }
        }
        return builder.toString();
    }
    
    public static CSRegistrate create() {
        return new CSRegistrate();
    }
    
    public SandPaperEntry sandPaper(String name) {
        return new SandPaperEntry(this, name);
    }
    public void addType(String name) {
        sandpaperLang.add(name);
    }
    
    public ItemEntry<SandPaperItem> sandPaperItem(String name) {
        sandpaperLang.add(name);
        return this.item(name + "_sand_paper", SandPaperItem::new)
                .tag(AllTags.AllItemTags.SANDPAPER.tag)
                .tab(() -> CreateSandpapers.itemGroup)
                .lang(autoLang(name + "_sand_paper"))
                .register();
    }
    
    public static Item getSandpaper(String name) {
        return CreateSandpapers.registrate().get(name+"_sand_paper", ForgeRegistries.ITEMS.getRegistryKey()).get();
    }
    
    
    
    public static void provideSandpaperLang(BiConsumer<String, String> consumer) {
        for (String name : sandpaperLang) {
            consumer.accept("item.createsandpapers." + name + "_sand_paper.tooltip.summary", "Can be used to _refine materials_. The process can be automated with a Deployer.");
            consumer.accept("item.createsandpapers." + name + "_sand_paper.tooltip.condition1", "When Used");
            consumer.accept("item.createsandpapers." + name + "_sand_paper.tooltip.behaviour1", "Applies polish to items held in the _offhand_ or lying on the _floor_ when _looking at them_");
        }
    }
    
    public static <T> TagKey<T> optionalTag(IForgeRegistry<T> registry,
                                            ResourceLocation id) {
        return registry.tags()
                .createOptionalTagKey(id, Collections.emptySet());
    }
    public static <T> TagKey<T> modTag(IForgeRegistry<T> registry, String namespace, String path) {
        return optionalTag(registry, new ResourceLocation(namespace, path));
    }
    public static TagKey<Item> modItemTag(String namespace, String path) {
        return modTag(ForgeRegistries.ITEMS, namespace, path);
    }
    public static TagKey<Item> makesSandpaper(String name) {
        return modItemTag("createsandpapers", "creates_" + name + "_sandpaper");
    }
}
