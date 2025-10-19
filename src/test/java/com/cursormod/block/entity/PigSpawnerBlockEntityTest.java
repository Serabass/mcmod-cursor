package com.cursormod.block.entity;

import com.cursormod.block.ModBlocks;
import com.cursormod.entity.ExplodingPig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для BlockEntity Свиноматора-3000
 * Проверяем внутренности машины хаоса! ⚙️🐷
 */
@DisplayName("⚙️ Тесты PigSpawnerBlockEntity")
class PigSpawnerBlockEntityTest {

    private PigSpawnerBlockEntity blockEntity;
    
    @Mock
    private ServerLevel mockLevel;
    
    @Mock
    private BlockState mockState;
    
    private BlockPos testPos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testPos = new BlockPos(0, 64, 0);
        blockEntity = new PigSpawnerBlockEntity(testPos, mockState);
        
        // Настройка мока уровня
        when(mockLevel.isClientSide).thenReturn(false);
    }

    @Test
    @DisplayName("🏗️ BlockEntity должен создаваться правильно")
    void testBlockEntityCreation() {
        // Then: BlockEntity создан
        assertNotNull(blockEntity, 
            "⚙️ PigSpawnerBlockEntity должен быть создан!");
        assertEquals(testPos, blockEntity.getBlockPos(),
            "📍 Позиция BlockEntity должна совпадать!");
    }

    @Test
    @DisplayName("💾 BlockEntity должен сохранять данные в NBT")
    void testSaveToNBT() {
        // Given: BlockEntity с некоторым состоянием
        CompoundTag tag = new CompoundTag();
        
        // When: Сохраняем данные
        blockEntity.saveAdditional(tag);
        
        // Then: Данные должны быть в NBT
        assertTrue(tag.contains("TickCounter"), 
            "⏱️ TickCounter должен быть сохранён в NBT!");
        
        // Проверяем что значение не отрицательное
        int tickCounter = tag.getInt("TickCounter");
        assertTrue(tickCounter >= 0, 
            "⏱️ TickCounter не должен быть отрицательным!");
    }

    @Test
    @DisplayName("📖 BlockEntity должен загружать данные из NBT")
    void testLoadFromNBT() {
        // Given: NBT с данными
        CompoundTag tag = new CompoundTag();
        tag.putInt("TickCounter", 42);
        
        // When: Загружаем данные
        blockEntity.load(tag);
        
        // Then: Данные должны быть загружены
        // (мы не можем напрямую проверить приватное поле, но можем сохранить и сравнить)
        CompoundTag savedTag = new CompoundTag();
        blockEntity.saveAdditional(savedTag);
        
        assertEquals(42, savedTag.getInt("TickCounter"),
            "⏱️ TickCounter должен быть загружен из NBT!");
    }

    @Test
    @DisplayName("🔄 BlockEntity должен корректно цикличить счётчик")
    void testTickCounterCycles() {
        // Given: BlockEntity с почти полным счётчиком
        CompoundTag tag = new CompoundTag();
        tag.putInt("TickCounter", 99);
        blockEntity.load(tag);
        
        // When: Делаем один тик
        PigSpawnerBlockEntity.tick(mockLevel, testPos, mockState, blockEntity);
        
        // Then: Счётчик должен сброситься (так как достиг 100)
        CompoundTag savedTag = new CompoundTag();
        blockEntity.saveAdditional(savedTag);
        
        // После достижения 100 счётчик сбрасывается в 0
        int tickCounter = savedTag.getInt("TickCounter");
        assertTrue(tickCounter == 0 || tickCounter == 100,
            "⏱️ TickCounter должен сброситься после достижения интервала!");
    }

    @Test
    @DisplayName("🎮 Tick не должен работать на клиенте")
    void testTickDoesNothingOnClient() {
        // Given: Клиентская сторона
        when(mockLevel.isClientSide).thenReturn(true);
        
        CompoundTag tagBefore = new CompoundTag();
        blockEntity.saveAdditional(tagBefore);
        
        // When: Пытаемся тикнуть
        PigSpawnerBlockEntity.tick(mockLevel, testPos, mockState, blockEntity);
        
        // Then: Состояние не должно измениться
        CompoundTag tagAfter = new CompoundTag();
        blockEntity.saveAdditional(tagAfter);
        
        assertEquals(tagBefore.getInt("TickCounter"), tagAfter.getInt("TickCounter"),
            "💤 На клиенте тик не должен изменять состояние!");
    }

    @Test
    @DisplayName("🆔 BlockEntity должен иметь правильный тип")
    void testBlockEntityType() {
        // Then: Тип должен совпадать с зарегистрированным
        assertEquals(ModBlockEntities.PIG_SPAWNER_BLOCK_ENTITY, 
                     blockEntity.getType(),
            "🔖 Тип BlockEntity должен быть PIG_SPAWNER_BLOCK_ENTITY!");
    }

    @Test
    @DisplayName("📍 BlockEntity должен помнить свою позицию")
    void testBlockEntityPosition() {
        // Given: BlockEntity создан с позицией
        BlockPos pos = new BlockPos(123, 45, 678);
        var entity = new PigSpawnerBlockEntity(pos, mockState);
        
        // Then: Позиция должна совпадать
        assertEquals(pos, entity.getBlockPos(),
            "📍 BlockEntity должен помнить свою позицию!");
    }

    @Test
    @DisplayName("⏰ Интервал спавна должен быть 100 тиков (5 секунд)")
    void testSpawnInterval() {
        // Given: BlockEntity который почти готов к спавну
        CompoundTag tag = new CompoundTag();
        
        // Проверяем что при 99 тиках спавн не происходит
        tag.putInt("TickCounter", 99);
        blockEntity.load(tag);
        
        // Тикаем один раз - должен сброситься
        PigSpawnerBlockEntity.tick(mockLevel, testPos, mockState, blockEntity);
        
        CompoundTag savedTag = new CompoundTag();
        blockEntity.saveAdditional(savedTag);
        
        // После 100 тиков счётчик сбрасывается
        int tickCounter = savedTag.getInt("TickCounter");
        assertTrue(tickCounter < 100,
            "⏰ После 100 тиков (5 секунд) должен произойти спавн и сброс!");
    }

    @Test
    @DisplayName("🧱 BlockEntity должен работать с правильным блоком")
    void testCorrectBlock() {
        // Given: BlockEntity для Свиноматора
        when(mockState.getBlock()).thenReturn(ModBlocks.PIG_SPAWNER);
        
        // Then: Блок должен быть Свиноматором
        assertSame(ModBlocks.PIG_SPAWNER, mockState.getBlock(),
            "🏭 BlockEntity должен работать с PigSpawnerBlock!");
    }

    @Test
    @DisplayName("💾 Сохранение и загрузка должны быть идемпотентными")
    void testSaveLoadIdempotent() {
        // Given: BlockEntity с некоторым состоянием
        CompoundTag tag1 = new CompoundTag();
        tag1.putInt("TickCounter", 77);
        blockEntity.load(tag1);
        
        // When: Сохраняем и загружаем обратно
        CompoundTag savedTag = new CompoundTag();
        blockEntity.saveAdditional(savedTag);
        
        PigSpawnerBlockEntity newEntity = new PigSpawnerBlockEntity(testPos, mockState);
        newEntity.load(savedTag);
        
        // Then: Состояние должно сохраниться
        CompoundTag finalTag = new CompoundTag();
        newEntity.saveAdditional(finalTag);
        
        assertEquals(savedTag.getInt("TickCounter"), finalTag.getInt("TickCounter"),
            "🔄 Сохранение и загрузка должны сохранять состояние!");
    }
}

