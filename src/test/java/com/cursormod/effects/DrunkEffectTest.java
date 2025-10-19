package com.cursormod.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для DrunkEffect - эффект опьянения, который инвертирует управление
 * 🍺🎮🧪
 * 
 * NOTE: Тестирование в трезвом состоянии не гарантирует корректную работу в пьяном
 */
@DisplayName("🍺🎮 Тесты DrunkEffect")
class DrunkEffectTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🍺 DrunkEffect класс должен существовать")
    void testDrunkEffectClassExists() {
        assertNotNull(DrunkEffect.class, 
            "📦 Класс DrunkEffect должен существовать!");
    }

    @Test
    @DisplayName("🆔 DrunkEffect должен быть MobEffect")
    void testDrunkEffectIsMobEffect() {
        assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(DrunkEffect.class),
            "🏷️ DrunkEffect должен наследоваться от MobEffect!");
    }

    @Test
    @DisplayName("⚡ DrunkEffect должен иметь статическое поле DRUNK_EFFECT")
    void testDrunkEffectHasStaticField() {
        try {
            var field = DrunkEffect.class.getField("DRUNK_EFFECT");
            assertNotNull(field, "⚡ DrunkEffect должен иметь статическое поле DRUNK_EFFECT!");
            assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(field.getType()),
                "⚡ DRUNK_EFFECT должен быть типа MobEffect!");
        } catch (NoSuchFieldException e) {
            fail("⚡ DrunkEffect должен иметь статическое поле DRUNK_EFFECT!");
        }
    }

    @Test
    @DisplayName("🎯 DrunkEffect должен иметь метод applyEffectTick")
    void testDrunkEffectHasApplyEffectTick() {
        try {
            var method = DrunkEffect.class.getMethod("applyEffectTick", 
                net.minecraft.world.entity.LivingEntity.class,
                int.class);
            assertNotNull(method, "🎯 DrunkEffect должен иметь метод applyEffectTick!");
        } catch (NoSuchMethodException e) {
            fail("🎯 DrunkEffect должен иметь метод applyEffectTick!");
        }
    }

    @Test
    @DisplayName("⏱️ DrunkEffect должен иметь метод isDurationEffectTick")
    void testDrunkEffectHasIsDurationEffectTick() {
        try {
            var method = DrunkEffect.class.getMethod("isDurationEffectTick", 
                int.class, int.class);
            assertNotNull(method, "⏱️ DrunkEffect должен иметь метод isDurationEffectTick!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ DrunkEffect должен иметь метод isDurationEffectTick!");
        }
    }

    @Test
    @DisplayName("✅ Все базовые методы DrunkEffect существуют")
    void testAllBasicMethodsExist() {
        assertNotNull(DrunkEffect.class, "Класс существует");
        assertTrue(net.minecraft.world.effect.MobEffect.class.isAssignableFrom(DrunkEffect.class), "Наследуется от MobEffect");
        
        try {
            DrunkEffect.class.getField("DRUNK_EFFECT");
            DrunkEffect.class.getMethod("applyEffectTick", 
                net.minecraft.world.entity.LivingEntity.class, int.class);
            DrunkEffect.class.getMethod("isDurationEffectTick", int.class, int.class);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            fail("Не все методы/поля существуют: " + e.getMessage());
        }
    }
}

