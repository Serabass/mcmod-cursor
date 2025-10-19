package com.cursormod.item;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ChickenGun - a weapon that shoots exploding pigs
 * 🐷🔫🧪
 * 
 * NOTE: Class name is ChickenGun, but it shoots pigs. This is not a bug, it's a feature!
 */
@DisplayName("🐷🔫 ChickenGun Tests")
class ChickenGunTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🔫 ChickenGun class should exist")
    void testChickenGunClassExists() {
        assertNotNull(ChickenGun.class, 
            "📦 ChickenGun class should exist!");
    }

    @Test
    @DisplayName("🆔 ChickenGun should be an Item")
    void testChickenGunIsItem() {
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(ChickenGun.class),
            "🏷️ ChickenGun should extend Item!");
    }

    @Test
    @DisplayName("🎯 ChickenGun should have use method")
    void testChickenGunHasUse() {
        try {
            var method = ChickenGun.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            assertNotNull(method, "🎯 ChickenGun should have use method!");
        } catch (NoSuchMethodException e) {
            fail("🎯 ChickenGun should have use method!");
        }
    }

    @Test
    @DisplayName("⏱️ ChickenGun should have getUseDuration method")
    void testChickenGunHasGetUseDuration() {
        try {
            var method = ChickenGun.class.getMethod("getUseDuration", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "⏱️ ChickenGun should have getUseDuration method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ ChickenGun should have getUseDuration method!");
        }
    }

    @Test
    @DisplayName("🎭 ChickenGun should have getUseAnimation method")
    void testChickenGunHasGetUseAnimation() {
        try {
            var method = ChickenGun.class.getMethod("getUseAnimation", 
                net.minecraft.world.item.ItemStack.class);
            assertNotNull(method, "🎭 ChickenGun should have getUseAnimation method!");
        } catch (NoSuchMethodException e) {
            fail("🎭 ChickenGun should have getUseAnimation method!");
        }
    }

    @Test
    @DisplayName("✅ All basic ChickenGun methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(ChickenGun.class, "Class exists");
        assertTrue(net.minecraft.world.item.Item.class.isAssignableFrom(ChickenGun.class), "Extends Item");
        
        try {
            ChickenGun.class.getMethod("use", 
                net.minecraft.world.level.Level.class,
                net.minecraft.world.entity.player.Player.class,
                net.minecraft.world.InteractionHand.class);
            ChickenGun.class.getMethod("getUseDuration", net.minecraft.world.item.ItemStack.class);
            ChickenGun.class.getMethod("getUseAnimation", net.minecraft.world.item.ItemStack.class);
        } catch (NoSuchMethodException e) {
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}

