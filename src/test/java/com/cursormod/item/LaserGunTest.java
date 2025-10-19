package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for LaserGun - a weapon that burns everything in its path
 * 🔫🔥🧪
 * 
 * NOTE: Don't point at yourself, may be dangerous
 */
@DisplayName("🔫🔥 LaserGun Tests")
class LaserGunTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🔫 LaserGun class should exist")
    void testLaserGunClassExists() {
        assertNotNull(LaserGun.class, 
            "📦 LaserGun class should exist!");
    }

    @Test
    @DisplayName("🆔 LaserGun should be an Item")
    void testLaserGunIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(LaserGun.class),
            "🏷️ LaserGun should extend Item!");
    }

    @Test
    @DisplayName("🎯 LaserGun should have use method")
    void testLaserGunHasUse() {
        try {
            var method = LaserGun.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            assertNotNull(method, "🎯 LaserGun should have use method!");
        } catch (NoSuchMethodException e) {
            fail("🎯 LaserGun should have use method!");
        }
    }

    @Test
    @DisplayName("⏱️ LaserGun should have getUseDuration method")
    void testLaserGunHasGetUseDuration() {
        try {
            var method = LaserGun.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ LaserGun should have getUseDuration method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ LaserGun should have getUseDuration method!");
        }
    }

    @Test
    @DisplayName("🎭 LaserGun should have getUseAnimation method")
    void testLaserGunHasGetUseAnimation() {
        try {
            var method = LaserGun.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 LaserGun should have getUseAnimation method!");
        } catch (NoSuchMethodException e) {
            fail("🎭 LaserGun should have getUseAnimation method!");
        }
    }

    @Test
    @DisplayName("✅ All basic LaserGun methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(LaserGun.class, "Class exists");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(LaserGun.class), "Extends Item");
        
        try {
            LaserGun.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            LaserGun.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            LaserGun.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
        } catch (NoSuchMethodException e) {
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}

