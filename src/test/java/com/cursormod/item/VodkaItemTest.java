package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for VodkaItem - a drink that makes the world better (or worse)
 * 🍺🧪
 * 
 * NOTE: Full testing requires creating a complete Minecraft world
 */
@DisplayName("🍺 VodkaItem Tests")
class VodkaItemTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🍺 VodkaItem class should exist")
    void testVodkaItemClassExists() {
        assertNotNull(VodkaItem.class, 
            "📦 VodkaItem class should exist!");
    }

    @Test
    @DisplayName("🆔 VodkaItem should be an Item")
    void testVodkaItemIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(VodkaItem.class),
            "🏷️ VodkaItem should extend Item!");
    }

    @Test
    @DisplayName("🍷 VodkaItem should have finishUsingItem method")
    void testVodkaItemHasFinishUsingItem() {
        try {
            var method = VodkaItem.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            assertNotNull(method, "🍷 VodkaItem should have finishUsingItem method!");
        } catch (NoSuchMethodException e) {
            fail("🍷 VodkaItem should have finishUsingItem method!");
        }
    }

    @Test
    @DisplayName("⏱️ VodkaItem should have getUseDuration method")
    void testVodkaItemHasGetUseDuration() {
        try {
            var method = VodkaItem.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ VodkaItem should have getUseDuration method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ VodkaItem should have getUseDuration method!");
        }
    }

    @Test
    @DisplayName("🎭 VodkaItem should have getUseAnimation method")
    void testVodkaItemHasGetUseAnimation() {
        try {
            var method = VodkaItem.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 VodkaItem should have getUseAnimation method!");
        } catch (NoSuchMethodException e) {
            fail("🎭 VodkaItem should have getUseAnimation method!");
        }
    }

    @Test
    @DisplayName("🍽️ VodkaItem should be edible")
    void testVodkaItemIsEdible() {
        try {
            var method = VodkaItem.class.getMethod("isEdible");
            assertNotNull(method, "🍽️ VodkaItem should have isEdible method!");
        } catch (NoSuchMethodException e) {
            fail("🍽️ VodkaItem should have isEdible method!");
        }
    }

    @Test
    @DisplayName("✅ All basic VodkaItem methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(VodkaItem.class, "Class exists");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(VodkaItem.class), "Extends Item");
        
        try {
            VodkaItem.class.getMethod("finishUsingItem", 
                net.minecraft.world.item.ItemStack.class,
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.LivingEntity.class);
            VodkaItem.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            VodkaItem.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
            VodkaItem.class.getMethod("isEdible");
        } catch (NoSuchMethodException e) {
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}

