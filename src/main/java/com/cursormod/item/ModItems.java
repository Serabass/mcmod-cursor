package com.cursormod.item;

import com.cursormod.Cursor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ModItems {
    
    // Красный банан - восстанавливает 6 голода и 7.2 насыщения
    public static final Item RED_BANANA = registerItem("red_banana",
        new RedBananaItem(new FabricItemSettings()
            .food(new FoodProperties.Builder()
                .nutrition(6)
                .saturationMod(1.2f)
                .build())
        ));
    
    // Яйцо призыва кубического моба
    public static final Item CUBE_MOB_SPAWN_EGG = registerItem("cube_mob_spawn_egg",
        new CubeMobSpawnEgg());

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Cursor.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Cursor.LOGGER.info("🍌 Registering Mod Items for " + Cursor.MOD_ID);
        Cursor.LOGGER.info("🍌 RED_BANANA registered with ID: cursor:red_banana");
    }
}
