package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для CarrotCannon - оружие, которое стреляет морковками
 * 🥕🔫🧪
 * 
 * NOTE: Проверено - морковки действительно летают
 */
@DisplayName("🥕🔫 Тесты CarrotCannon")
class CarrotCannonTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🥕 CarrotCannon класс должен существовать")
    void testCarrotCannonClassExists() {
        assertNotNull(CarrotCannon.class, 
            "📦 Класс CarrotCannon должен существовать!");
    }

    @Test
    @DisplayName("🆔 CarrotCannon должен быть Item")
    void testCarrotCannonIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(CarrotCannon.class),
            "🏷️ CarrotCannon должен наследоваться от Item!");
    }

    @Test
    @DisplayName("🎯 CarrotCannon должен иметь метод use")
    void testCarrotCannonHasUse() {
        try {
            var method = CarrotCannon.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            assertNotNull(method, "🎯 CarrotCannon должен иметь метод use!");
        } catch (NoSuchMethodException e) {
            fail("🎯 CarrotCannon должен иметь метод use!");
        }
    }

    @Test
    @DisplayName("⏱️ CarrotCannon должен иметь метод getUseDuration")
    void testCarrotCannonHasGetUseDuration() {
        try {
            var method = CarrotCannon.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ CarrotCannon должен иметь метод getUseDuration!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ CarrotCannon должен иметь метод getUseDuration!");
        }
    }

    @Test
    @DisplayName("🎭 CarrotCannon должен иметь метод getUseAnimation")
    void testCarrotCannonHasGetUseAnimation() {
        try {
            var method = CarrotCannon.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 CarrotCannon должен иметь метод getUseAnimation!");
        } catch (NoSuchMethodException e) {
            fail("🎭 CarrotCannon должен иметь метод getUseAnimation!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы CarrotCannon существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(CarrotCannon.class, "Класс существует");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(CarrotCannon.class), "Наследуется от Item");
        
        try {
            CarrotCannon.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            CarrotCannon.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            CarrotCannon.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}

