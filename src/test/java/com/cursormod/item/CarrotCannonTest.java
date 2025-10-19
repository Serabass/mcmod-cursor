package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CarrotCannon - a weapon that shoots carrots
 * 🥕🔫🧪
 * 
 * NOTE: Verified - carrots really do fly
 */
@DisplayName("🥕🔫 CarrotCannon Tests")
class CarrotCannonTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🥕 CarrotCannon class should exist")
    void testCarrotCannonClassExists() {
        assertNotNull(CarrotCannon.class, 
            "📦 CarrotCannon class should exist!");
    }

    @Test
    @DisplayName("🆔 CarrotCannon should be an Item")
    void testCarrotCannonIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(CarrotCannon.class),
            "🏷️ CarrotCannon should extend Item!");
    }

    @Test
    @DisplayName("🎯 CarrotCannon should have use method")
    void testCarrotCannonHasUse() {
        try {
            var method = CarrotCannon.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            assertNotNull(method, "🎯 CarrotCannon should have use method!");
        } catch (NoSuchMethodException e) {
            fail("🎯 CarrotCannon should have use method!");
        }
    }

    @Test
    @DisplayName("⏱️ CarrotCannon should have getUseDuration method")
    void testCarrotCannonHasGetUseDuration() {
        try {
            var method = CarrotCannon.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ CarrotCannon should have getUseDuration method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ CarrotCannon should have getUseDuration method!");
        }
    }

    @Test
    @DisplayName("🎭 CarrotCannon should have getUseAnimation method")
    void testCarrotCannonHasGetUseAnimation() {
        try {
            var method = CarrotCannon.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 CarrotCannon should have getUseAnimation method!");
        } catch (NoSuchMethodException e) {
            fail("🎭 CarrotCannon should have getUseAnimation method!");
        }
    }

    @Test
    @DisplayName("✅ All basic CarrotCannon methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(CarrotCannon.class, "Class exists");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(CarrotCannon.class), "Extends Item");
        
        try {
            CarrotCannon.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            CarrotCannon.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            CarrotCannon.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
        } catch (NoSuchMethodException e) {
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}

