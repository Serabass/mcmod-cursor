package com.cursormod.entity;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для CarrotProjectile - летающая морковка, которая бьет врагов
 * 🥕💨🧪
 * 
 * NOTE: Морковки тоже могут быть опасными
 */
@DisplayName("🥕💨 Тесты CarrotProjectile")
class CarrotProjectileTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🥕 CarrotProjectile класс должен существовать")
    void testCarrotProjectileClassExists() {
        assertNotNull(CarrotProjectile.class, 
            "📦 Класс CarrotProjectile должен существовать!");
    }

    @Test
    @DisplayName("🆔 CarrotProjectile должен быть ThrowableItemProjectile")
    void testCarrotProjectileIsThrowableItemProjectile() {
        assertTrue(net.minecraft.world.entity.projectile.ThrowableItemProjectile.class.isAssignableFrom(CarrotProjectile.class),
            "🏷️ CarrotProjectile должен наследоваться от ThrowableItemProjectile!");
    }

    @Test
    @DisplayName("🎯 CarrotProjectile должен иметь метод getDefaultItem")
    void testCarrotProjectileHasGetDefaultItem() {
        try {
            var method = CarrotProjectile.class.getDeclaredMethod("getDefaultItem");
            assertNotNull(method, "🎯 CarrotProjectile должен иметь метод getDefaultItem!");
        } catch (NoSuchMethodException e) {
            fail("🎯 CarrotProjectile должен иметь метод getDefaultItem!");
        }
    }

    @Test
    @DisplayName("💥 CarrotProjectile должен иметь метод onHitEntity")
    void testCarrotProjectileHasOnHitEntity() {
        try {
            var method = CarrotProjectile.class.getDeclaredMethod("onHitEntity", 
                net.minecraft.world.phys.EntityHitResult.class);
            assertNotNull(method, "💥 CarrotProjectile должен иметь метод onHitEntity!");
        } catch (NoSuchMethodException e) {
            fail("💥 CarrotProjectile должен иметь метод onHitEntity!");
        }
    }

    @Test
    @DisplayName("🧱 CarrotProjectile должен иметь метод onHit")
    void testCarrotProjectileHasOnHit() {
        try {
            var method = CarrotProjectile.class.getDeclaredMethod("onHit", 
                net.minecraft.world.phys.HitResult.class);
            assertNotNull(method, "🧱 CarrotProjectile должен иметь метод onHit!");
        } catch (NoSuchMethodException e) {
            fail("🧱 CarrotProjectile должен иметь метод onHit!");
        }
    }

    @Test
    @DisplayName("⏱️ CarrotProjectile должен иметь метод tick")
    void testCarrotProjectileHasTick() {
        try {
            var method = CarrotProjectile.class.getMethod("tick");
            assertNotNull(method, "⏱️ CarrotProjectile должен иметь метод tick!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ CarrotProjectile должен иметь метод tick!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы CarrotProjectile существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(CarrotProjectile.class, "Класс существует");
        assertTrue(net.minecraft.world.entity.projectile.ThrowableItemProjectile.class.isAssignableFrom(CarrotProjectile.class), 
            "Наследуется от ThrowableItemProjectile");
        
        try {
            CarrotProjectile.class.getDeclaredMethod("getDefaultItem");
            CarrotProjectile.class.getDeclaredMethod("onHitEntity", 
                net.minecraft.world.phys.EntityHitResult.class);
            CarrotProjectile.class.getDeclaredMethod("onHit", 
                net.minecraft.world.phys.HitResult.class);
            CarrotProjectile.class.getMethod("tick");
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}

