package com.cursormod.client;

import com.cursormod.Cursor;
import com.cursormod.client.renderer.CubeMobRenderer;
import com.cursormod.client.CarrotProjectileRenderer;
import com.cursormod.client.ThorHammerRenderer;
import com.cursormod.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        Cursor.LOGGER.info("🔷 Initializing Mod Client for " + Cursor.MOD_ID);
        
        // Регистрируем рендерер для кубического моба
        EntityRendererRegistry.register(ModEntities.CUBE_MOB, CubeMobRenderer::new);
        
        // Регистрируем рендерер для снаряда морковки
        EntityRendererRegistry.register(ModEntities.CARROT_PROJECTILE, CarrotProjectileRenderer::new);
        
        // Регистрируем рендерер для молота Тора
        EntityRendererRegistry.register(ModEntities.THOR_HAMMER_PROJECTILE, ThorHammerRenderer::new);
        
        Cursor.LOGGER.info("🔷 CubeMobRenderer registered!");
        Cursor.LOGGER.info("🥕 CarrotProjectileRenderer registered!");
        Cursor.LOGGER.info("🔨⚡ ThorHammerRenderer registered!");
    }
}
