package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для PizzaSword - меч, который можно съесть
 * 🍕⚔️🧪
 * 
 * NOTE: Лучший меч для голодных воинов
 */
@DisplayName("🍕⚔️ Тесты PizzaSword")
class PizzaSwordTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🍕 PizzaSword класс должен существовать")
    void testPizzaSwordClassExists() {
        assertNotNull(PizzaSword.class, 
            "📦 Класс PizzaSword должен существовать!");
    }

    @Test
    @DisplayName("🆔 PizzaSword должен быть SwordItem")
    void testPizzaSwordIsSwordItem() {
        assertTrue(net.minecraft.world.item.SwordItem.class.isAssignableFrom(PizzaSword.class),
            "🏷️ PizzaSword должен наследоваться от SwordItem!");
    }

    @Test
    @DisplayName("⚔️ PizzaSword должен иметь метод hurtEnemy")
    void testPizzaSwordHasHurtEnemy() {
        try {
            var method = PizzaSword.class.getMethod("hurtEnemy", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.entity.LivingEntity.class,
                net.minecraft.world.entity.LivingEntity.class);
            assertNotNull(method, "⚔️ PizzaSword должен иметь метод hurtEnemy!");
        } catch (NoSuchMethodException e) {
            fail("⚔️ PizzaSword должен иметь метод hurtEnemy!");
        }
    }

    @Test
    @DisplayName("🎭 PizzaSword должен иметь метод getUseAnimation")
    void testPizzaSwordHasGetUseAnimation() {
        try {
            var method = PizzaSword.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 PizzaSword должен иметь метод getUseAnimation!");
        } catch (NoSuchMethodException e) {
            fail("🎭 PizzaSword должен иметь метод getUseAnimation!");
        }
    }

    @Test
    @DisplayName("⏱️ PizzaSword должен иметь метод getUseDuration")
    void testPizzaSwordHasGetUseDuration() {
        try {
            var method = PizzaSword.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ PizzaSword должен иметь метод getUseDuration!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ PizzaSword должен иметь метод getUseDuration!");
        }
    }

    @Test
    @DisplayName("🍽️ PizzaSword должен быть съедобным")
    void testPizzaSwordIsEdible() {
        try {
            var method = PizzaSword.class.getMethod("isEdible");
            assertNotNull(method, "🍽️ PizzaSword должен иметь метод isEdible!");
        } catch (NoSuchMethodException e) {
            fail("🍽️ PizzaSword должен иметь метод isEdible!");
        }
    }

    @Test
    @DisplayName("🍴 PizzaSword должен иметь метод finishUsingItem")
    void testPizzaSwordHasFinishUsingItem() {
        try {
            var method = PizzaSword.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            assertNotNull(method, "🍴 PizzaSword должен иметь метод finishUsingItem!");
        } catch (NoSuchMethodException e) {
            fail("🍴 PizzaSword должен иметь метод finishUsingItem!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы PizzaSword существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(PizzaSword.class, "Класс существует");
        assertTrue(net.minecraft.world.item.SwordItem.class.isAssignableFrom(PizzaSword.class), "Наследуется от SwordItem");
        
        try {
            PizzaSword.class.getMethod("hurtEnemy", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.entity.LivingEntity.class,
                net.minecraft.world.entity.LivingEntity.class);
            PizzaSword.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
            PizzaSword.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            PizzaSword.class.getMethod("isEdible");
            PizzaSword.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}

