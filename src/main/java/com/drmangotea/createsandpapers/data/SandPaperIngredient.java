package com.drmangotea.createsandpapers.data;

import com.drmangotea.createsandpapers.ModSandpapers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SandPaperIngredient {
    private final List<TagKey<Item>> tags = new ArrayList<>();
    private final List<ResourceLocation> items = new ArrayList<>();

    private final Map<String, String> nameFormatter = new HashMap<>();

    private final String defaultFormat = "%s_sand";

    private final ModSandpapers type;

    private String altName = null;

    public SandPaperIngredient(ModSandpapers type) {
        this.type = type;
    }

    private String getTypeName() {
        return Optional.ofNullable(altName).orElse(type.getName());
    }

    public SandPaperIngredient name(String altName) {
        this.altName = altName;
        return this;
    }

    public SandPaperIngredient commonType(boolean tfc) {
        if (tfc) addItem("tfc:sand/" + getTypeName());
        return commonType();
    }

    public SandPaperIngredient commonType() {
        return addTag(ResourceLocation.fromNamespaceAndPath("c", "sands/" + getTypeName()));
    }

    public SandPaperIngredient addTag(ResourceLocation location) {
        return addTag(ItemTags.create(location));
    }

    public SandPaperIngredient addTag(TagKey<Item> tag) {
        tags.add(tag);
        return this;
    }

    public SandPaperIngredient addMod(String modid, String format) {
        this.nameFormatter.put(modid, format);
        return addMod(modid);
    }

    public SandPaperIngredient addMod(String modid) {
        String path = nameFormatter.getOrDefault(modid, defaultFormat);
        this.items.add(ResourceLocation.fromNamespaceAndPath(modid, path.formatted(getTypeName())));
        return this;
    }

    public SandPaperIngredient addItem(String location) {
        return addItem(ResourceLocation.parse(location));
    }

    public SandPaperIngredient addItems(String... locations) {
        for (String location : locations) {
            addItem(ResourceLocation.parse(location));
        }
        return this;
    }

    public SandPaperIngredient addItem(ResourceLocation location) {
        items.add(location);
        return this;
    }

    public List<TagKey<Item>> getTags() {
        return tags;
    }

    public List<ResourceLocation> getItems() {
        return items;
    }

    public void finish(BiConsumer<ModSandpapers, SandPaperIngredient> consumer) {
        consumer.accept(this.type, this);
    }
}
