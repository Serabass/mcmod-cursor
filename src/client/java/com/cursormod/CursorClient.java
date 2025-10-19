package com.cursormod;

import com.cursormod.client.CarrotProjectileRenderer;
import com.cursormod.client.renderer.CubeMobRenderer;
import com.cursormod.entity.ModEntities;
import com.cursormod.fluid.ModFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;

public class CursorClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Cursor.LOGGER.info("🔷 Initializing Mod Client for " + Cursor.MOD_ID);
		
		// Регистрируем рендереры сущностей
		EntityRendererRegistry.register(ModEntities.CUBE_MOB, CubeMobRenderer::new);
		EntityRendererRegistry.register(ModEntities.CARROT_PROJECTILE, CarrotProjectileRenderer::new);
		EntityRendererRegistry.register(ModEntities.EXPLODING_PIG, PigRenderer::new);
		
		// Регистрируем рендеринг жидкого разочарования
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.DISAPPOINTMENT, ModFluids.FLOWING_DISAPPOINTMENT,
			new SimpleFluidRenderHandler(
				new ResourceLocation("cursor:block/disappointment_still"),
				new ResourceLocation("cursor:block/disappointment_flow"),
				0x505050 // Серый цвет разочарования
			)
		);
		
		Cursor.LOGGER.info("🔷 CubeMobRenderer registered!");
		Cursor.LOGGER.info("🥕 CarrotProjectileRenderer registered!");
		Cursor.LOGGER.info("🐷💣 ExplodingPigRenderer registered!");
		Cursor.LOGGER.info("💧 DisappointmentFluid renderer registered!");
	}
}