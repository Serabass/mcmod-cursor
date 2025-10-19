package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для LaserGun - оружие, которое жжет всё на своем пути
 * 🔫🔥🧪
 * 
 * NOTE: Не направляйте на себя, может быть опасно
 */
@DisplayName("🔫🔥 Тесты LaserGun")
class LaserGunTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🔫 LaserGun класс должен существовать")
    void testLaserGunClassExists() {
        assertNotNull(LaserGun.class, 
            "📦 Класс LaserGun должен существовать!");
    }

    @Test
    @DisplayName("🆔 LaserGun должен быть Item")
    void testLaserGunIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(LaserGun.class),
            "🏷️ LaserGun должен наследоваться от Item!");
    }

    @Test
    @DisplayName("🎯 LaserGun должен иметь метод use")
    void testLaserGunHasUse() {
        try {
            var method = LaserGun.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            assertNotNull(method, "🎯 LaserGun должен иметь метод use!");
        } catch (NoSuchMethodException e) {
            fail("🎯 LaserGun должен иметь метод use!");
        }
    }

    @Test
    @DisplayName("⏱️ LaserGun должен иметь метод getUseDuration")
    void testLaserGunHasGetUseDuration() {
        try {
            var method = LaserGun.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ LaserGun должен иметь метод getUseDuration!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ LaserGun должен иметь метод getUseDuration!");
        }
    }

    @Test
    @DisplayName("🎭 LaserGun должен иметь метод getUseAnimation")
    void testLaserGunHasGetUseAnimation() {
        try {
            var method = LaserGun.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 LaserGun должен иметь метод getUseAnimation!");
        } catch (NoSuchMethodException e) {
            fail("🎭 LaserGun должен иметь метод getUseAnimation!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы LaserGun существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(LaserGun.class, "Класс существует");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(LaserGun.class), "Наследуется от Item");
        
        try {
            LaserGun.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            LaserGun.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            LaserGun.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}

