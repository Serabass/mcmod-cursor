package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for RedBananaItem - a red banana (because yellow ones are too boring)
 * 🍌🔴🧪
 * 
 * NOTE: Yes, red bananas exist in nature. Google it!
 */
@DisplayName("🍌 RedBananaItem Tests")
class RedBananaItemTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🍌 RedBananaItem class should exist")
    void testRedBananaItemClassExists() {
        assertNotNull(RedBananaItem.class, 
            "📦 RedBananaItem class should exist!");
    }

    @Test
    @DisplayName("🆔 RedBananaItem should be an Item")
    void testRedBananaItemIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(RedBananaItem.class),
            "🏷️ RedBananaItem should extend Item!");
    }

    @Test
    @DisplayName("🍽️ RedBananaItem should have finishUsingItem method")
    void testRedBananaItemHasFinishUsingItem() {
        try {
            var method = RedBananaItem.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            assertNotNull(method, "🍽️ RedBananaItem should have finishUsingItem method!");
        } catch (NoSuchMethodException e) {
            fail("🍽️ RedBananaItem should have finishUsingItem method!");
        }
    }

    @Test
    @DisplayName("⏱️ RedBananaItem should have getUseDuration method")
    void testRedBananaItemHasGetUseDuration() {
        try {
            var method = RedBananaItem.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ RedBananaItem should have getUseDuration method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ RedBananaItem should have getUseDuration method!");
        }
    }

    @Test
    @DisplayName("✅ All basic RedBananaItem methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(RedBananaItem.class, "Class exists");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(RedBananaItem.class), "Extends Item");
        
        try {
            RedBananaItem.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            RedBananaItem.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
        } catch (NoSuchMethodException e) {
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}

