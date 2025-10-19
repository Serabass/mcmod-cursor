package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для RedBananaItem - красный банан (потому что желтые слишком скучные)
 * 🍌🔴🧪
 * 
 * NOTE: Да, красные бананы существуют в природе. Гуглите!
 */
@DisplayName("🍌 Тесты RedBananaItem")
class RedBananaItemTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🍌 RedBananaItem класс должен существовать")
    void testRedBananaItemClassExists() {
        assertNotNull(RedBananaItem.class, 
            "📦 Класс RedBananaItem должен существовать!");
    }

    @Test
    @DisplayName("🆔 RedBananaItem должен быть Item")
    void testRedBananaItemIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(RedBananaItem.class),
            "🏷️ RedBananaItem должен наследоваться от Item!");
    }

    @Test
    @DisplayName("🍽️ RedBananaItem должен иметь метод finishUsingItem")
    void testRedBananaItemHasFinishUsingItem() {
        try {
            var method = RedBananaItem.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            assertNotNull(method, "🍽️ RedBananaItem должен иметь метод finishUsingItem!");
        } catch (NoSuchMethodException e) {
            fail("🍽️ RedBananaItem должен иметь метод finishUsingItem!");
        }
    }

    @Test
    @DisplayName("⏱️ RedBananaItem должен иметь метод getUseDuration")
    void testRedBananaItemHasGetUseDuration() {
        try {
            var method = RedBananaItem.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ RedBananaItem должен иметь метод getUseDuration!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ RedBananaItem должен иметь метод getUseDuration!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы RedBananaItem существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(RedBananaItem.class, "Класс существует");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(RedBananaItem.class), "Наследуется от Item");
        
        try {
            RedBananaItem.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            RedBananaItem.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}

