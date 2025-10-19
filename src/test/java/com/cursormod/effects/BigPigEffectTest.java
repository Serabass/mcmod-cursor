package com.cursormod.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BigPigEffect - an effect that makes pigs big and fast
 * 🐷💨🧪
 * 
 * NOTE: Full testing of effects requires creating a complete Minecraft world
 */
@DisplayName("🐷💨 BigPigEffect Tests")
class BigPigEffectTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🐷 BigPigEffect class should exist")
    void testBigPigEffectClassExists() {
        assertNotNull(BigPigEffect.class, 
            "📦 BigPigEffect class should exist!");
    }

    @Test
    @DisplayName("🆔 BigPigEffect should be a MobEffect")
    void testBigPigEffectIsMobEffect() {
        assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(BigPigEffect.class),
            "🏷️ BigPigEffect should extend MobEffect!");
    }

    @Test
    @DisplayName("⚡ BigPigEffect should have static field BIG_PIG_EFFECT")
    void testBigPigEffectHasStaticField() {
        try {
            var field = BigPigEffect.class.getField("BIG_PIG_EFFECT");
            assertNotNull(field, "⚡ BigPigEffect should have static field BIG_PIG_EFFECT!");
            assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(field.getType()),
                "⚡ BIG_PIG_EFFECT should be of type MobEffect!");
        } catch (NoSuchFieldException e) {
            fail("⚡ BigPigEffect should have static field BIG_PIG_EFFECT!");
        }
    }

    @Test
    @DisplayName("🎯 BigPigEffect should have applyEffectTick method")
    void testBigPigEffectHasApplyEffectTick() {
        try {
            var method = BigPigEffect.class.getMethod("applyEffectTick", 
                net.minecraft.world.entity.LivingEntity.class,
                int.class);
            assertNotNull(method, "🎯 BigPigEffect should have applyEffectTick method!");
        } catch (NoSuchMethodException e) {
            fail("🎯 BigPigEffect should have applyEffectTick method!");
        }
    }

    @Test
    @DisplayName("⏱️ BigPigEffect should have isDurationEffectTick method")
    void testBigPigEffectHasIsDurationEffectTick() {
        try {
            var method = BigPigEffect.class.getMethod("isDurationEffectTick", 
                int.class, int.class);
            assertNotNull(method, "⏱️ BigPigEffect should have isDurationEffectTick method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ BigPigEffect should have isDurationEffectTick method!");
        }
    }

    @Test
    @DisplayName("✅ All basic BigPigEffect methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(BigPigEffect.class, "Class exists");
        assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(BigPigEffect.class), "Extends MobEffect");
        
        try {
            BigPigEffect.class.getField("BIG_PIG_EFFECT");
            BigPigEffect.class.getMethod("applyEffectTick", 
                net.minecraft.world.entity.LivingEntity.class, int.class);
            BigPigEffect.class.getMethod("isDurationEffectTick", int.class, int.class);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            fail("Not all methods/fields exist: " + e.getMessage());
        }
    }
}

