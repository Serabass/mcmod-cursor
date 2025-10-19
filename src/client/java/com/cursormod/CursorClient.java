package com.cursormod;

import com.cursormod.client.CarrotProjectileRenderer;
import com.cursormod.client.renderer.CubeMobRenderer;
import com.cursormod.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ChickenRenderer;

public class CursorClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Cursor.LOGGER.info("🔷 Initializing Mod Client for " + Cursor.MOD_ID);
		
		// Регистрируем рендереры сущностей
		EntityRendererRegistry.register(ModEntities.CUBE_MOB, CubeMobRenderer::new);
		EntityRendererRegistry.register(ModEntities.CARROT_PROJECTILE, CarrotProjectileRenderer::new);
		EntityRendererRegistry.register(ModEntities.EXPLODING_CHICKEN, ChickenRenderer::new);
		
		Cursor.LOGGER.info("🔷 CubeMobRenderer registered!");
		Cursor.LOGGER.info("🥕 CarrotProjectileRenderer registered!");
		Cursor.LOGGER.info("🐔💣 ExplodingChickenRenderer registered!");
	}
}