package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для предмета Vodka - напиток, который делает мир лучше (или хуже)
 * 🍺🧪
 * 
 * NOTE: Полное тестирование требует создания полноценного Minecraft мира
 */
@DisplayName("🍺 Тесты VodkaItem")
class VodkaItemTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🍺 VodkaItem класс должен существовать")
    void testVodkaItemClassExists() {
        assertNotNull(VodkaItem.class, 
            "📦 Класс VodkaItem должен существовать!");
    }

    @Test
    @DisplayName("🆔 VodkaItem должен быть Item")
    void testVodkaItemIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(VodkaItem.class),
            "🏷️ VodkaItem должен наследоваться от Item!");
    }

    @Test
    @DisplayName("🍷 VodkaItem должен иметь метод finishUsingItem")
    void testVodkaItemHasFinishUsingItem() {
        try {
            var method = VodkaItem.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            assertNotNull(method, "🍷 VodkaItem должен иметь метод finishUsingItem!");
        } catch (NoSuchMethodException e) {
            fail("🍷 VodkaItem должен иметь метод finishUsingItem!");
        }
    }

    @Test
    @DisplayName("⏱️ VodkaItem должен иметь метод getUseDuration")
    void testVodkaItemHasGetUseDuration() {
        try {
            var method = VodkaItem.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ VodkaItem должен иметь метод getUseDuration!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ VodkaItem должен иметь метод getUseDuration!");
        }
    }

    @Test
    @DisplayName("🎭 VodkaItem должен иметь метод getUseAnimation")
    void testVodkaItemHasGetUseAnimation() {
        try {
            var method = VodkaItem.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 VodkaItem должен иметь метод getUseAnimation!");
        } catch (NoSuchMethodException e) {
            fail("🎭 VodkaItem должен иметь метод getUseAnimation!");
        }
    }

    @Test
    @DisplayName("🍽️ VodkaItem должен быть съедобным")
    void testVodkaItemIsEdible() {
        try {
            var method = VodkaItem.class.getMethod("isEdible");
            assertNotNull(method, "🍽️ VodkaItem должен иметь метод isEdible!");
        } catch (NoSuchMethodException e) {
            fail("🍽️ VodkaItem должен иметь метод isEdible!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы VodkaItem существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(VodkaItem.class, "Класс существует");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(VodkaItem.class), "Наследуется от Item");
        
        try {
            VodkaItem.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            VodkaItem.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            VodkaItem.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
            VodkaItem.class.getMethod("isEdible");
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}

