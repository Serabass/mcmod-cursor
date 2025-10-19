package com.cursormod.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для BigPigEffect - эффект, который делает свиней большими и быстрыми
 * 🐷💨🧪
 * 
 * NOTE: Полное тестирование эффектов требует создания полноценного Minecraft мира
 */
@DisplayName("🐷💨 Тесты BigPigEffect")
class BigPigEffectTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🐷 BigPigEffect класс должен существовать")
    void testBigPigEffectClassExists() {
        assertNotNull(BigPigEffect.class, 
            "📦 Класс BigPigEffect должен существовать!");
    }

    @Test
    @DisplayName("🆔 BigPigEffect должен быть MobEffect")
    void testBigPigEffectIsMobEffect() {
        assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(BigPigEffect.class),
            "🏷️ BigPigEffect должен наследоваться от MobEffect!");
    }

    @Test
    @DisplayName("⚡ BigPigEffect должен иметь статическое поле BIG_PIG_EFFECT")
    void testBigPigEffectHasStaticField() {
        try {
            var field = BigPigEffect.class.getField("BIG_PIG_EFFECT");
            assertNotNull(field, "⚡ BigPigEffect должен иметь статическое поле BIG_PIG_EFFECT!");
            assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(field.getType()),
                "⚡ BIG_PIG_EFFECT должен быть типа MobEffect!");
        } catch (NoSuchFieldException e) {
            fail("⚡ BigPigEffect должен иметь статическое поле BIG_PIG_EFFECT!");
        }
    }

    @Test
    @DisplayName("🎯 BigPigEffect должен иметь метод applyEffectTick")
    void testBigPigEffectHasApplyEffectTick() {
        try {
            var method = BigPigEffect.class.getMethod("applyEffectTick", 
                net.minecraft.world.entity.LivingEntity.class,
                int.class);
            assertNotNull(method, "🎯 BigPigEffect должен иметь метод applyEffectTick!");
        } catch (NoSuchMethodException e) {
            fail("🎯 BigPigEffect должен иметь метод applyEffectTick!");
        }
    }

    @Test
    @DisplayName("⏱️ BigPigEffect должен иметь метод isDurationEffectTick")
    void testBigPigEffectHasIsDurationEffectTick() {
        try {
            var method = BigPigEffect.class.getMethod("isDurationEffectTick", 
                int.class, int.class);
            assertNotNull(method, "⏱️ BigPigEffect должен иметь метод isDurationEffectTick!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ BigPigEffect должен иметь метод isDurationEffectTick!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы BigPigEffect существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(BigPigEffect.class, "Класс существует");
        assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(BigPigEffect.class), "Наследуется от MobEffect");
        
        try {
            BigPigEffect.class.getField("BIG_PIG_EFFECT");
            BigPigEffect.class.getMethod("applyEffectTick", 
                net.minecraft.world.entity.LivingEntity.class, int.class);
            BigPigEffect.class.getMethod("isDurationEffectTick", int.class, int.class);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            fail("Не все методы/поля существуют: " + e.getMessage());
        }
    }
}

