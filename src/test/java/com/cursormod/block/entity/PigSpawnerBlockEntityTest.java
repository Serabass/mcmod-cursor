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
 * Simple tests for PigSpawner-3000 BlockEntity
 * ⚙️🐷
 * 
 * NOTE: Full testing requires creating a complete Minecraft world
 */
@DisplayName("⚙️ PigSpawnerBlockEntity Tests")
class PigSpawnerBlockEntityTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("🏗️ PigSpawnerBlockEntity class should exist")
    void testPigSpawnerBlockEntityClassExists() {
        assertNotNull(PigSpawnerBlockEntity.class, 
            "⚙️ PigSpawnerBlockEntity class should exist!");
    }

    @Test
    @DisplayName("🔢 PigSpawnerBlockEntity should be a BlockEntity")
    void testIsBlockEntity() {
        assertTrue(net.minecraft.world.level.block.entity.BlockEntity.class.isAssignableFrom(PigSpawnerBlockEntity.class),
            "🏗️ PigSpawnerBlockEntity should extend BlockEntity!");
    }

    @Test
    @DisplayName("💾 PigSpawnerBlockEntity should have saveAdditional method")
    void testHasSaveAdditional() {
        try {
            var method = PigSpawnerBlockEntity.class.getDeclaredMethod("saveAdditional", CompoundTag.class);
            assertNotNull(method, "💾 PigSpawnerBlockEntity should have saveAdditional method!");
        } catch (NoSuchMethodException e) {
            fail("💾 PigSpawnerBlockEntity should have saveAdditional method!");
        }
    }

    @Test
    @DisplayName("📖 PigSpawnerBlockEntity should have load method")
    void testHasLoad() {
        try {
            var method = PigSpawnerBlockEntity.class.getMethod("load", CompoundTag.class);
            assertNotNull(method, "📖 PigSpawnerBlockEntity should have load method!");
        } catch (NoSuchMethodException e) {
            fail("📖 PigSpawnerBlockEntity should have load method!");
        }
    }

    @Test
    @DisplayName("⏱️ PigSpawnerBlockEntity should have tick method")
    void testHasTick() {
        try {
            var method = PigSpawnerBlockEntity.class.getMethod("tick", 
                net.minecraft.world.level.Level.class,
                net.minecraft.core.BlockPos.class,
                net.minecraft.world.level.block.state.BlockState.class,
                PigSpawnerBlockEntity.class);
            assertNotNull(method, "⏱️ PigSpawnerBlockEntity should have tick method!");
        } catch (NoSuchMethodException e) {
            fail("⏱️ PigSpawnerBlockEntity should have tick method!");
        }
    }

    @Test
    @DisplayName("✅ All basic PigSpawnerBlockEntity methods exist")
    void testAllBasicMethodsExist() {
        assertNotNull(PigSpawnerBlockEntity.class, "Class exists");
        assertTrue(net.minecraft.world.level.block.entity.BlockEntity.class.isAssignableFrom(PigSpawnerBlockEntity.class), "Extends BlockEntity");
        
        try {
            PigSpawnerBlockEntity.class.getDeclaredMethod("saveAdditional", CompoundTag.class);
            PigSpawnerBlockEntity.class.getMethod("load", CompoundTag.class);
            PigSpawnerBlockEntity.class.getMethod("tick", 
                net.minecraft.world.level.Level.class,
                net.minecraft.core.BlockPos.class,
                net.minecraft.world.level.block.state.BlockState.class,
                PigSpawnerBlockEntity.class);
        } catch (NoSuchMethodException e) {
            fail("Not all methods exist: " + e.getMessage());
        }
    }
}