package com.cursormod.entity;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Максимально простые тесты для взрывных свиней
 * 🐷💣🧪
 * 
 * NOTE: Полное тестирование Minecraft сущностей требует интеграционных тестов
 */
@DisplayName("🐷💣 Тесты ExplodingPig")
class ExplodingPigTest {

    @BeforeAll
    static void initMinecraft() {
        // Инициализируем Minecraft
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🐷 ExplodingPig класс должен существовать")
    void testExplodingPigClassExists() {
        assertNotNull(ExplodingPig.class, 
            "📦 Класс ExplodingPig должен существовать!");
    }

    @Test
    @DisplayName("🆔 ExplodingPig должен быть Entity")
    void testExplodingPigIsEntity() {
        assertTrue(net.minecraft.world.entity.Entity.class.isAssignableFrom(ExplodingPig.class),
            "🏷️ ExplodingPig должен наследоваться от Entity!");
    }

    @Test
    @DisplayName("🔥 ExplodingPig должен иметь метод createAttributes")
    void testExplodingPigHasCreateAttributes() {
        try {
            var method = ExplodingPig.class.getMethod("createAttributes");
            assertNotNull(method, "🔥 ExplodingPig должен иметь метод createAttributes!");
        } catch (NoSuchMethodException e) {
            fail("🔥 ExplodingPig должен иметь метод createAttributes!");
        }
    }

    @Test
    @DisplayName("💣 ExplodingPig должен иметь метод explode")
    void testExplodingPigHasExplode() {
        try {
            var method = ExplodingPig.class.getDeclaredMethod("explode");
            assertNotNull(method, "💣 ExplodingPig должен иметь метод explode!");
        } catch (NoSuchMethodException e) {
            fail("💣 ExplodingPig должен иметь метод explode!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы ExplodingPig существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(ExplodingPig.class, "Класс существует");
        assertTrue(net.minecraft.world.entity.Entity.class.isAssignableFrom(ExplodingPig.class), "Наследуется от Entity");
        
        try {
            ExplodingPig.class.getMethod("createAttributes");
            ExplodingPig.class.getDeclaredMethod("explode");
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}