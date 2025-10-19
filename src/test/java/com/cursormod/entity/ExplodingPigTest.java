package com.cursormod.entity;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple tests for exploding pigs
 * 🐷💣🧪
 * 
 * NOTE: Full testing of Minecraft entities requires integration tests
 */
@DisplayName("🐷💣 ExplodingPig Tests")
class ExplodingPigTest {

    @BeforeAll
    static void initMinecraft() {
        // Initialize Minecraft
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🐷 ExplodingPig class should exist")
    void testExplodingPigClassExists() {
        assertNotNull(ExplodingPig.class, 
            "📦 ExplodingPig class should exist!");
    }

    @Test
    @DisplayName("🆔 ExplodingPig should be an Entity")
    void testExplodingPigIsEntity() {
        assertTrue(net.minecraft.world.entity.Entity.class.isAssignableFrom(ExplodingPig.class),
            "🏷️ ExplodingPig should extend Entity!");
    }

    @Test
    @DisplayName("🔥 ExplodingPig should have createAttributes method")
    void testExplodingPigHasCreateAttributes() {
        try {
            var method = ExplodingPig.class.getMethod("createAttributes");
            assertNotNull(method, "🔥 ExplodingPig should have createAttributes method!");
        } catch (NoSuchMethodException e) {
            fail("🔥 ExplodingPig should have createAttributes method!");
        }
    }

    @Test
    @DisplayName("💣 ExplodingPig should have explode method")
    void testExplodingPigHasExplode() {
        try {
            var method = ExplodingPig.class.getDeclaredMethod("explode");
            assertNotNull(method, "💣 ExplodingPig should have explode method!");
        } catch (NoSuchMethodException e) {
            fail("💣 ExplodingPig should have explode method!");
        }
    }

    @Test
    @DisplayName("✅ All basic ExplodingPig methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(ExplodingPig.class, "Class exists");
        assertTrue(net.minecraft.world.entity.Entity.class.isAssignableFrom(ExplodingPig.class), "Extends Entity");
        
        try {
            ExplodingPig.class.getMethod("createAttributes");
            ExplodingPig.class.getDeclaredMethod("explode");
        } catch (NoSuchMethodException e) {
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}