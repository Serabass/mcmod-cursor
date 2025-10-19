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

public class ModEntities {
    
    // Регистрируем кубического моба
    public static final EntityType<CubeMob> CUBE_MOB = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        new ResourceLocation(Cursor.MOD_ID, "cube_mob"),
        FabricEntityTypeBuilder.create(MobCategory.CREATURE, CubeMob::new)
            .dimensions(EntityDimensions.fixed(1.0f, 1.0f))
            .build()
    );
    
    public static void registerModEntities() {
        Cursor.LOGGER.info("🔷 Registering Mod Entities for " + Cursor.MOD_ID);
        
        // Регистрируем атрибуты моба
        FabricDefaultAttributeRegistry.register(CUBE_MOB, CubeMob.createAttributes());
        
        Cursor.LOGGER.info("🔷 CUBE_MOB registered with ID: cursor:cube_mob");
    }
}
