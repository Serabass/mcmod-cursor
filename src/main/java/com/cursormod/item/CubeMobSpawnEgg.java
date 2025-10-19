package com.cursormod.item;

import com.cursormod.Cursor;
import com.cursormod.entity.ModEntities;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class CubeMobSpawnEgg extends SpawnEggItem {
    
    public CubeMobSpawnEgg() {
        super(ModEntities.CUBE_MOB, 0x00FF00, 0xFF0000, new Item.Properties());
        Cursor.LOGGER.info("🔷 CubeMobSpawnEgg created! Green and red cubic spawn egg!");
    }
}
