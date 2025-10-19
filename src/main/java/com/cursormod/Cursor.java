package com.cursormod;

import com.cursormod.item.ModItems;
import com.cursormod.item.ModItemGroups;
import com.cursormod.entity.ModEntities;
import com.cursormod.events.ServerTickHandler;
import com.cursormod.effects.DrunkEffect;
import com.cursormod.effects.FlyingPigEffect;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cursor implements ModInitializer {
	public static final String MOD_ID = "cursor";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		
		// Регистрируем предметы мода
		ModItems.registerModItems();
		
		// Регистрируем группы предметов
		ModItemGroups.registerModItemGroups();
		
		// Регистрируем сущности мода
		ModEntities.registerModEntities();
		
        // Регистрируем обработчик событий сервера
        ServerTickHandler.register();
        
        // Регистрируем эффекты мода
        DrunkEffect.DRUNK_EFFECT.toString(); // Инициализируем эффект
        FlyingPigEffect.FLYING_PIG_EFFECT.toString(); // Инициализируем эффект летающей свиньи
	}
}