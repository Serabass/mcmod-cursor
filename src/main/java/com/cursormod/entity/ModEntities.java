package com.cursormod.entity;

import com.cursormod.Cursor;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

public class ModEntities {
    
    // Регистрируем кубического моба
    public static final EntityType<CubeMob> CUBE_MOB = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        new ResourceLocation(Cursor.MOD_ID, "cube_mob"),
        FabricEntityTypeBuilder.create(MobCategory.CREATURE, CubeMob::new)
            .dimensions(EntityDimensions.fixed(1.0f, 1.0f))
            .build()
    );
    
    // Регистрируем снаряд морковки
    public static final EntityType<CarrotProjectile> CARROT_PROJECTILE = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        new ResourceLocation(Cursor.MOD_ID, "carrot_projectile"),
        FabricEntityTypeBuilder.create(MobCategory.MISC, (EntityType<CarrotProjectile> entityType, Level level) -> 
            new CarrotProjectile(entityType, level))
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
            .build()
    );
    
    // Регистрируем взрывающуюся свинью
    public static final EntityType<ExplodingPig> EXPLODING_PIG = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        new ResourceLocation(Cursor.MOD_ID, "exploding_pig"),
        FabricEntityTypeBuilder.create(MobCategory.CREATURE, ExplodingPig::new)
            .dimensions(EntityDimensions.fixed(0.9f, 0.9f))
            .build()
    );
    
    public static void registerModEntities() {
        Cursor.LOGGER.info("🔷 Registering Mod Entities for " + Cursor.MOD_ID);
        
        // Регистрируем атрибуты моба
        FabricDefaultAttributeRegistry.register(CUBE_MOB, CubeMob.createAttributes());
        FabricDefaultAttributeRegistry.register(EXPLODING_PIG, ExplodingPig.createAttributes());
        
        Cursor.LOGGER.info("🔷 CUBE_MOB registered with ID: cursor:cube_mob");
        Cursor.LOGGER.info("🥕 CARROT_PROJECTILE registered with ID: cursor:carrot_projectile");
        Cursor.LOGGER.info("🐷💣 EXPLODING_PIG registered with ID: cursor:exploding_pig");
    }
}
