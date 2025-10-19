package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для ChickenGun - оружие, которое стреляет взрывающимися свиньями
 * 🐷🔫🧪
 * 
 * NOTE: Название класса - ChickenGun, но стреляет свиньями. Это не баг, это фича!
 */
@DisplayName("🐷🔫 Тесты ChickenGun")
class ChickenGunTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🔫 ChickenGun класс должен существовать")
    void testChickenGunClassExists() {
        assertNotNull(ChickenGun.class, 
            "📦 Класс ChickenGun должен существовать!");
    }

    @Test
    @DisplayName("🆔 ChickenGun должен быть Item")
    void testChickenGunIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(ChickenGun.class),
            "🏷️ ChickenGun должен наследоваться от Item!");
    }

    @Test
    @DisplayName("🎯 ChickenGun должен иметь метод use")
    void testChickenGunHasUse() {
        try {
            var method = ChickenGun.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            assertNotNull(method, "🎯 ChickenGun должен иметь метод use!");
        } catch (NoSuchMethodException e) {
            fail("🎯 ChickenGun должен иметь метод use!");
        }
    }

    @Test
    @DisplayName("⏱️ ChickenGun должен иметь метод getUseDuration")
    void testChickenGunHasGetUseDuration() {
        try {
            var method = ChickenGun.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ ChickenGun должен иметь метод getUseDuration!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ ChickenGun должен иметь метод getUseDuration!");
        }
    }

    @Test
    @DisplayName("🎭 ChickenGun должен иметь метод getUseAnimation")
    void testChickenGunHasGetUseAnimation() {
        try {
            var method = ChickenGun.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 ChickenGun должен иметь метод getUseAnimation!");
        } catch (NoSuchMethodException e) {
            fail("🎭 ChickenGun должен иметь метод getUseAnimation!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы ChickenGun существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(ChickenGun.class, "Класс существует");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(ChickenGun.class), "Наследуется от Item");
        
        try {
            ChickenGun.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            ChickenGun.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            ChickenGun.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}

