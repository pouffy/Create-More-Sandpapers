package com.drmangotea.createsandpapers;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CSRegistrate extends AbstractRegistrate<CSRegistrate> {
    private static final Map<RegistryEntry<?, ?>, DeferredHolder<CreativeModeTab, CreativeModeTab>> TAB_LOOKUP = Collections.synchronizedMap(new IdentityHashMap<>());
    private static final List<String> sandpaperLang = new ArrayList<>();

    @Nullable
    protected Function<Item, TooltipModifier> currentTooltipModifierFactory;
    protected DeferredHolder<CreativeModeTab, CreativeModeTab> currentTab;

    public static boolean isInCreativeTab(RegistryEntry<?, ?> entry, DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
        return TAB_LOOKUP.get(entry) == tab;
    }

    protected CSRegistrate() {
        super(CreateSandpapers.ID);
    }
    
    public static CSRegistrate create() {
        return new CSRegistrate();
    }

    public SandPaperEntry sandPaper(String name) {
        return new SandPaperEntry(this, name);
    }

    public CSRegistrate setTooltipModifierFactory(@Nullable Function<Item, TooltipModifier> factory) {
        currentTooltipModifierFactory = factory;
        return self();
    }

    @Nullable
    public Function<Item, TooltipModifier> getTooltipModifierFactory() {
        return currentTooltipModifierFactory;
    }

    @Nullable
    public CSRegistrate setCreativeTab(DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
        currentTab = tab;
        return self();
    }

    public DeferredHolder<CreativeModeTab, CreativeModeTab> getCreativeTab() {
        return currentTab;
    }

    @Override
    protected <R, T extends R> RegistryEntry<R, T> accept(String name, ResourceKey<? extends Registry<R>> type, Builder<R, T, ?, ?> builder, NonNullSupplier<? extends T> creator, NonNullFunction<DeferredHolder<R, T>, ? extends RegistryEntry<R, T>> entryFactory) {
        RegistryEntry<R, T> entry = super.accept(name, type, builder, creator, entryFactory);
        if (type.equals(Registries.ITEM) && currentTooltipModifierFactory != null) {
            // grab the factory here for the lambda, it can change between now and registration
            Function<Item, TooltipModifier> factory = currentTooltipModifierFactory;
            this.addRegisterCallback(name, Registries.ITEM, item -> {
                TooltipModifier modifier = factory.apply(item);
                TooltipModifier.REGISTRY.register(item, modifier);
            });
        }
        if (currentTab != null)
            TAB_LOOKUP.put(entry, currentTab);

        return entry;
    }
    
    public ItemEntry<SandPaperItem> sandPaperItem(String name) {
        sandpaperLang.add(name);
        ItemBuilder<SandPaperItem, CSRegistrate> builder = item(name + "_sand_paper", SandPaperItem::new).tag(AllTags.AllItemTags.SANDPAPER.tag).onRegister(s -> ItemDescription.referKey(s, AllItems.SAND_PAPER));
        return builder.register();
    }
    
    public static Item getSandpaper(String name) {
        return CreateSandpapers.REGISTRATE.get(name+"_sand_paper", Registries.ITEM).get();
    }
    
    public static void provideSandpaperLang(BiConsumer<String, String> consumer) {
        for (String name : sandpaperLang) {
            consumer.accept("item.createsandpapers." + name + "_sand_paper.tooltip.summary", "Can be used to _refine materials_. The process can be automated with a Deployer.");
            consumer.accept("item.createsandpapers." + name + "_sand_paper.tooltip.condition1", "When Used");
            consumer.accept("item.createsandpapers." + name + "_sand_paper.tooltip.behaviour1", "Applies polish to items held in the _offhand_ or lying on the _floor_ when _looking at them_");
        }
    }
    
    public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
        return TagKey.create(registry.key(), id);
    }
    public static <T> TagKey<T> modTag(Registry<T> registry, String namespace, String path) {
        return optionalTag(registry, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }
    public static TagKey<Item> modItemTag(String namespace, String path) {
        return modTag(BuiltInRegistries.ITEM, namespace, path);
    }
    public static TagKey<Item> makesSandpaper(String name) {
        return modItemTag("createsandpapers", "creates_" + name + "_sandpaper");
    }
}
