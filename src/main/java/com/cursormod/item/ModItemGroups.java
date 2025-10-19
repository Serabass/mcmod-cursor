package com.cursormod.item;

import com.cursormod.Cursor;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    
    public static final CreativeModeTab CURSOR_GROUP = Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        new ResourceLocation(Cursor.MOD_ID, "cursor_group"),
        FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.cursor.cursor_group"))
            .icon(() -> new ItemStack(ModItems.RED_BANANA))
                   .displayItems((context, entries) -> {
                       entries.accept(ModItems.RED_BANANA);
                       entries.accept(ModItems.CUBE_MOB_SPAWN_EGG);
                       entries.accept(ModItems.PIZZA_SWORD);
                       entries.accept(ModItems.VODKA);
                       entries.accept(ModItems.LASER_GUN);
                       entries.accept(ModItems.CARROT_CANNON);
                       entries.accept(ModItems.CHICKEN_GUN);
                       entries.accept(ModItems.DISAPPOINTMENT_BUCKET);
                       entries.accept(ModItems.PIG_SPAWNER);
                       entries.accept(ModItems.THOR_HAMMER);
                   })
            .build()
    );
    
    public static void registerModItemGroups() {
        Cursor.LOGGER.info("🍌 Registering Mod Item Groups for " + Cursor.MOD_ID);
    }
}
