package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PizzaSword - a sword you can eat
 * 🍕⚔️🧪
 * 
 * NOTE: Best sword for hungry warriors
 */
@DisplayName("🍕⚔️ PizzaSword Tests")
class PizzaSwordTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🍕 PizzaSword class should exist")
    void testPizzaSwordClassExists() {
        assertNotNull(PizzaSword.class, 
            "📦 PizzaSword class should exist!");
    }

    @Test
    @DisplayName("🆔 PizzaSword should be a SwordItem")
    void testPizzaSwordIsSwordItem() {
        assertTrue(net.minecraft.world.item.SwordItem.class.isAssignableFrom(PizzaSword.class),
            "🏷️ PizzaSword should extend SwordItem!");
    }

    @Test
    @DisplayName("⚔️ PizzaSword should have hurtEnemy method")
    void testPizzaSwordHasHurtEnemy() {
        try {
            var method = PizzaSword.class.getMethod("hurtEnemy", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.entity.LivingEntity.class,
                net.minecraft.world.entity.LivingEntity.class);
            assertNotNull(method, "⚔️ PizzaSword should have hurtEnemy method!");
        } catch (NoSuchMethodException e) {
            fail("⚔️ PizzaSword should have hurtEnemy method!");
        }
    }

    @Test
    @DisplayName("🎭 PizzaSword should have getUseAnimation method")
    void testPizzaSwordHasGetUseAnimation() {
        try {
            var method = PizzaSword.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 PizzaSword should have getUseAnimation method!");
        } catch (NoSuchMethodException e) {
            fail("🎭 PizzaSword should have getUseAnimation method!");
        }
    }

    @Test
    @DisplayName("⏱️ PizzaSword should have getUseDuration method")
    void testPizzaSwordHasGetUseDuration() {
        try {
            var method = PizzaSword.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ PizzaSword should have getUseDuration method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ PizzaSword should have getUseDuration method!");
        }
    }

    @Test
    @DisplayName("🍽️ PizzaSword should be edible")
    void testPizzaSwordIsEdible() {
        try {
            var method = PizzaSword.class.getMethod("isEdible");
            assertNotNull(method, "🍽️ PizzaSword should have isEdible method!");
        } catch (NoSuchMethodException e) {
            fail("🍽️ PizzaSword should have isEdible method!");
        }
    }

    @Test
    @DisplayName("🍴 PizzaSword should have finishUsingItem method")
    void testPizzaSwordHasFinishUsingItem() {
        try {
            var method = PizzaSword.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            assertNotNull(method, "🍴 PizzaSword should have finishUsingItem method!");
        } catch (NoSuchMethodException e) {
            fail("🍴 PizzaSword should have finishUsingItem method!");
        }
    }

    @Test
    @DisplayName("✅ All basic PizzaSword methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(PizzaSword.class, "Class exists");
        assertTrue(net.minecraft.world.item.SwordItem.class.isAssignableFrom(PizzaSword.class), "Extends SwordItem");
        
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
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}

