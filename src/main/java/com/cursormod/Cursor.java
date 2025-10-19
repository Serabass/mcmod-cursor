package com.cursormod;

import com.cursormod.block.ModBlocks;
import com.cursormod.block.entity.ModBlockEntities;
import com.cursormod.fluid.ModFluids;
import com.cursormod.item.ModItems;
import com.cursormod.item.ModItemGroups;
import com.cursormod.entity.ModEntities;
import com.cursormod.events.ServerTickHandler;
import com.cursormod.events.EntityInteractionHandler;
import com.cursormod.events.PigIgnitionHandler;
import com.cursormod.effects.DrunkEffect;
import com.cursormod.effects.BigPigEffect;
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
		
		// Регистрируем жидкости мода (должно быть первым!)
		ModFluids.registerModFluids();
		
		// Теперь регистрируем блок жидкости
		ModBlocks.registerDisappointmentBlock(() -> ModFluids.DISAPPOINTMENT);
		ModBlocks.registerModBlocks();
		
		// Регистрируем BlockEntity
		ModBlockEntities.registerBlockEntities();
		
		// Регистрируем предметы мода
		ModItems.registerModItems();
		
		// Регистрируем группы предметов
		ModItemGroups.registerModItemGroups();
		
		// Регистрируем сущности мода
		ModEntities.registerModEntities();
		
        // Регистрируем обработчик событий сервера
        ServerTickHandler.register();
        
        // Регистрируем обработчик взаимодействия с сущностями
        EntityInteractionHandler.register();
        
        // Регистрируем обработчик загорания свиней
        PigIgnitionHandler.register();
        
        // Регистрируем эффекты мода
        DrunkEffect.DRUNK_EFFECT.toString(); // Инициализируем эффект
        BigPigEffect.BIG_PIG_EFFECT.toString(); // Инициализируем эффект большой свиньи
	}
}