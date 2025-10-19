package com.cursormod.entity;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CarrotProjectile - a flying carrot that hits enemies
 * 🥕💨🧪
 * 
 * NOTE: Carrots can be dangerous too
 */
@DisplayName("🥕💨 CarrotProjectile Tests")
class CarrotProjectileTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🥕 CarrotProjectile class should exist")
    void testCarrotProjectileClassExists() {
        assertNotNull(CarrotProjectile.class, 
            "📦 CarrotProjectile class should exist!");
    }

    @Test
    @DisplayName("🆔 CarrotProjectile should be a ThrowableItemProjectile")
    void testCarrotProjectileIsThrowableItemProjectile() {
        assertTrue(net.minecraft.world.entity.projectile.ThrowableItemProjectile.class.isAssignableFrom(CarrotProjectile.class),
            "🏷️ CarrotProjectile should extend ThrowableItemProjectile!");
    }

    @Test
    @DisplayName("🎯 CarrotProjectile should have getDefaultItem method")
    void testCarrotProjectileHasGetDefaultItem() {
        try {
            var method = CarrotProjectile.class.getDeclaredMethod("getDefaultItem");
            assertNotNull(method, "🎯 CarrotProjectile should have getDefaultItem method!");
        } catch (NoSuchMethodException e) {
            fail("🎯 CarrotProjectile should have getDefaultItem method!");
        }
    }

    @Test
    @DisplayName("💥 CarrotProjectile should have onHitEntity method")
    void testCarrotProjectileHasOnHitEntity() {
        try {
            var method = CarrotProjectile.class.getDeclaredMethod("onHitEntity", 
                net.minecraft.world.phys.EntityHitResult.class);
            assertNotNull(method, "💥 CarrotProjectile should have onHitEntity method!");
        } catch (NoSuchMethodException e) {
            fail("💥 CarrotProjectile should have onHitEntity method!");
        }
    }

    @Test
    @DisplayName("🧱 CarrotProjectile should have onHit method")
    void testCarrotProjectileHasOnHit() {
        try {
            var method = CarrotProjectile.class.getDeclaredMethod("onHit", 
                net.minecraft.world.phys.HitResult.class);
            assertNotNull(method, "🧱 CarrotProjectile should have onHit method!");
        } catch (NoSuchMethodException e) {
            fail("🧱 CarrotProjectile should have onHit method!");
        }
    }

    @Test
    @DisplayName("⏱️ CarrotProjectile should have tick method")
    void testCarrotProjectileHasTick() {
        try {
            var method = CarrotProjectile.class.getMethod("tick");
            assertNotNull(method, "⏱️ CarrotProjectile should have tick method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ CarrotProjectile should have tick method!");
        }
    }

    @Test
    @DisplayName("✅ All basic CarrotProjectile methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(CarrotProjectile.class, "Class exists");
        assertTrue(net.minecraft.world.entity.projectile.ThrowableItemProjectile.class.isAssignableFrom(CarrotProjectile.class), 
            "Extends ThrowableItemProjectile");
        
        try {
            CarrotProjectile.class.getDeclaredMethod("getDefaultItem");
            CarrotProjectile.class.getDeclaredMethod("onHitEntity", 
                net.minecraft.world.phys.EntityHitResult.class);
            CarrotProjectile.class.getDeclaredMethod("onHit", 
                net.minecraft.world.phys.HitResult.class);
            CarrotProjectile.class.getMethod("tick");
        } catch (NoSuchMethodException e) {
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}

