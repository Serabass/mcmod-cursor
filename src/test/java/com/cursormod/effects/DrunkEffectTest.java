package com.cursormod.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DrunkEffect - intoxication effect that inverts controls
 * 🍺🎮🧪
 * 
 * NOTE: Testing while sober doesn't guarantee correct behavior while drunk
 */
@DisplayName("🍺🎮 DrunkEffect Tests")
class DrunkEffectTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🍺 DrunkEffect class should exist")
    void testDrunkEffectClassExists() {
        assertNotNull(DrunkEffect.class, 
            "📦 DrunkEffect class should exist!");
    }

    @Test
    @DisplayName("🆔 DrunkEffect should be a MobEffect")
    void testDrunkEffectIsMobEffect() {
        assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(DrunkEffect.class),
            "🏷️ DrunkEffect should extend MobEffect!");
    }

    @Test
    @DisplayName("⚡ DrunkEffect should have static field DRUNK_EFFECT")
    void testDrunkEffectHasStaticField() {
        try {
            var field = DrunkEffect.class.getField("DRUNK_EFFECT");
            assertNotNull(field, "⚡ DrunkEffect should have static field DRUNK_EFFECT!");
            assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(field.getType()),
                "⚡ DRUNK_EFFECT should be of type MobEffect!");
        } catch (NoSuchFieldException e) {
            fail("⚡ DrunkEffect should have static field DRUNK_EFFECT!");
        }
    }

    @Test
    @DisplayName("🎯 DrunkEffect should have applyEffectTick method")
    void testDrunkEffectHasApplyEffectTick() {
        try {
            var method = DrunkEffect.class.getMethod("applyEffectTick", 
                net.minecraft.world.entity.LivingEntity.class,
                int.class);
            assertNotNull(method, "🎯 DrunkEffect should have applyEffectTick method!");
        } catch (NoSuchMethodException e) {
            fail("🎯 DrunkEffect should have applyEffectTick method!");
        }
    }

    @Test
    @DisplayName("⏱️ DrunkEffect should have isDurationEffectTick method")
    void testDrunkEffectHasIsDurationEffectTick() {
        try {
            var method = DrunkEffect.class.getMethod("isDurationEffectTick", 
                int.class, int.class);
            assertNotNull(method, "⏱️ DrunkEffect should have isDurationEffectTick method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ DrunkEffect should have isDurationEffectTick method!");
        }
    }

    @Test
    @DisplayName("✅ All basic DrunkEffect methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(DrunkEffect.class, "Class exists");
        assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(DrunkEffect.class), "Extends MobEffect");
        
        try {
            DrunkEffect.class.getField("DRUNK_EFFECT");
            DrunkEffect.class.getMethod("applyEffectTick", 
                net.minecraft.world.entity.LivingEntity.class, int.class);
            DrunkEffect.class.getMethod("isDurationEffectTick", int.class, int.class);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            fail("Not all methods/fields exist: " + e.getMessage());
        }
    }
}

