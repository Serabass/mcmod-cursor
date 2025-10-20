package com.cursormod.item;

import com.cursormod.Cursor;
import com.cursormod.block.ModBlocks;
import com.cursormod.fluid.ModFluids;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;

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
    
    // Пицца-меч - восстанавливает голод при ударе и использовании
    public static final Item PIZZA_SWORD = registerItem("pizza_sword",
        new PizzaSword(Tiers.IRON, 3, -2.4f, new FabricItemSettings()));
    
    // Водка - инвертирует управление
    public static final Item VODKA = registerItem("vodka",
        new VodkaItem(new FabricItemSettings()
            .food(new FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(0.8f)
                .build())
        ));
    
    // Лазерная пушка - поджигает всё на своём пути
    public static final Item LASER_GUN = registerItem("laser_gun",
        new LaserGun(new FabricItemSettings()
            .durability(100) // 100 выстрелов
        ));
    
    // Морковная пушка - стреляет морковками
    public static final Item CARROT_CANNON = registerItem("carrot_cannon",
        new CarrotCannon(new FabricItemSettings()
            .durability(200) // 200 выстрелов
        ));
    
    // Куриная пушка - стреляет взрывающимися курицами
    public static final Item CHICKEN_GUN = registerItem("chicken_gun",
        new ChickenGun(new FabricItemSettings()
            .durability(150) // 150 выстрелов
        ));
    
    // Огненная картофельная пушка - стреляет поджигающими картофелинами
    public static final Item FIRE_POTATO_GUN = registerItem("fire_potato_gun",
        new FirePotatoGun(new FabricItemSettings()
            .durability(160)
            .fireResistant()
        ));
    
    // Ведро с жидким разочарованием - для тех моментов, когда надо полить им всё вокруг
    public static final Item DISAPPOINTMENT_BUCKET = registerItem("disappointment_bucket",
        new BucketItem(ModFluids.DISAPPOINTMENT, new FabricItemSettings()
            .craftRemainder(Items.BUCKET)
            .stacksTo(1)
        ));
    
    // Свиноматор-3000 - блок для спавна взрывных свиней
    public static final Item PIG_SPAWNER = registerItem("pig_spawner",
        new BlockItem(ModBlocks.PIG_SPAWNER, new FabricItemSettings()));
    
    // Молот Тора - бросается и возвращается в руку
    public static final Item THOR_HAMMER = registerItem("thor_hammer",
        new ThorHammerItem(new FabricItemSettings()
            .durability(500) // 500 использований
            .fireResistant() // Не горит в лаве
        ));

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Cursor.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Cursor.LOGGER.info("🍌 Registering Mod Items for " + Cursor.MOD_ID);
        Cursor.LOGGER.info("🍌 RED_BANANA registered with ID: cursor:red_banana");
        Cursor.LOGGER.info("🔨⚡ THOR_HAMMER registered with ID: cursor:thor_hammer");
        Cursor.LOGGER.info("🔥🥔 FIRE_POTATO_GUN registered with ID: cursor:fire_potato_gun");
    }
}
