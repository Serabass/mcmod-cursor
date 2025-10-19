package com.cursormod.block;

import com.cursormod.Cursor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class ModBlocks {
    
    // Блок жидкого разочарования - будет инициализирован позже
    public static Block DISAPPOINTMENT_BLOCK;
    
	// Свиноматор-3000 - генератор хаоса (НЕУЯЗВИМЫЙ!)
	public static final Block PIG_SPAWNER = register("pig_spawner",
		new PigSpawnerBlock(BlockBehaviour.Properties.of()
			.mapColor(MapColor.METAL)
			.strength(50.0F, 3600000.0F) // Сила = 50, сопротивление взрывам = как у bedrock
			.sound(SoundType.METAL)
			.requiresCorrectToolForDrops()
	));
    
    public static void registerDisappointmentBlock(Supplier<net.minecraft.world.level.material.FlowingFluid> fluid) {
        DISAPPOINTMENT_BLOCK = register("disappointment_block",
            new LiquidBlock(fluid.get(), BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .replaceable()
                .noCollission()
                .strength(100.0F)
                .pushReaction(PushReaction.DESTROY)
                .noLootTable()
                .liquid()
        ));
    }
    
    private static Block register(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Cursor.MOD_ID, name), block);
    }
    
    public static void registerModBlocks() {
        Cursor.LOGGER.info("🧱 Зарегистрированы блоки разочарования. Прекрасно.");
        Cursor.LOGGER.info("🐷🏭 Зарегистрирован Свиноматор-3000. Chaos Factory готова к работе!");
    }
}

