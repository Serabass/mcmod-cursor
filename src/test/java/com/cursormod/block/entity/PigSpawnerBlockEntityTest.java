package com.cursormod.block.entity;

import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Максимально простые тесты для BlockEntity Свиноматора-3000
 * ⚙️🐷
 * 
 * NOTE: Полное тестирование требует создания полноценного Minecraft мира
 */
@DisplayName("⚙️ Тесты PigSpawnerBlockEntity")
class PigSpawnerBlockEntityTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🏗️ PigSpawnerBlockEntity класс должен существовать")
    void testPigSpawnerBlockEntityClassExists() {
        assertNotNull(PigSpawnerBlockEntity.class, 
            "⚙️ Класс PigSpawnerBlockEntity должен существовать!");
    }

    @Test
    @DisplayName("🔢 PigSpawnerBlockEntity должен быть BlockEntity")
    void testIsBlockEntity() {
        assertTrue(net.minecraft.world.level.block.entity.BlockEntity.class.isAssignableFrom(PigSpawnerBlockEntity.class),
            "🏗️ PigSpawnerBlockEntity должен наследоваться от BlockEntity!");
    }

    @Test
    @DisplayName("💾 PigSpawnerBlockEntity должен иметь метод saveAdditional")
    void testHasSaveAdditional() {
        try {
            var method = PigSpawnerBlockEntity.class.getDeclaredMethod("saveAdditional", CompoundTag.class);
            assertNotNull(method, "💾 PigSpawnerBlockEntity должен иметь метод saveAdditional!");
        } catch (NoSuchMethodException e) {
            fail("💾 PigSpawnerBlockEntity должен иметь метод saveAdditional!");
        }
    }

    @Test
    @DisplayName("📖 PigSpawnerBlockEntity должен иметь метод load")
    void testHasLoad() {
        try {
            var method = PigSpawnerBlockEntity.class.getMethod("load", CompoundTag.class);
            assertNotNull(method, "📖 PigSpawnerBlockEntity должен иметь метод load!");
        } catch (NoSuchMethodException e) {
            fail("📖 PigSpawnerBlockEntity должен иметь метод load!");
        }
    }

    @Test
    @DisplayName("⏱️ PigSpawnerBlockEntity должен иметь метод tick")
    void testHasTick() {
        try {
            var method = PigSpawnerBlockEntity.class.getMethod("tick", 
                net.minecraft.world.level.Level.class,
                net.minecraft.core.BlockPos.class,
                net.minecraft.world.level.block.state.BlockState.class,
                PigSpawnerBlockEntity.class);
            assertNotNull(method, "⏱️ PigSpawnerBlockEntity должен иметь метод tick!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ PigSpawnerBlockEntity должен иметь метод tick!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы PigSpawnerBlockEntity существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(PigSpawnerBlockEntity.class, "Класс существует");
        assertTrue(net.minecraft.world.level.block.entity.BlockEntity.class.isAssignableFrom(PigSpawnerBlockEntity.class), "Наследуется от BlockEntity");
        
        try {
            PigSpawnerBlockEntity.class.getDeclaredMethod("saveAdditional", CompoundTag.class);
            PigSpawnerBlockEntity.class.getMethod("load", CompoundTag.class);
            PigSpawnerBlockEntity.class.getMethod("tick", 
                net.minecraft.world.level.Level.class,
                net.minecraft.core.BlockPos.class,
                net.minecraft.world.level.block.state.BlockState.class,
                PigSpawnerBlockEntity.class);
        } catch (NoSuchMethodException e) {
            fail("Не все методы существуют: " + e.getMessage());
        }
    }
}