package com.cursormod.block.entity;

import com.cursormod.Cursor;
import com.cursormod.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    
    public static BlockEntityType<PigSpawnerBlockEntity> PIG_SPAWNER_BLOCK_ENTITY;
    
    public static void registerBlockEntities() {
        PIG_SPAWNER_BLOCK_ENTITY = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation(Cursor.MOD_ID, "pig_spawner"),
            BlockEntityType.Builder.of(
                PigSpawnerBlockEntity::new,
                ModBlocks.PIG_SPAWNER
            ).build(null)
        );
        
        Cursor.LOGGER.info("🏭⚙️ Зарегистрирован BlockEntity для Свиноматора-3000!");
    }
}

