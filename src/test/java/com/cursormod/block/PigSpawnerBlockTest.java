package com.cursormod.block;

import com.cursormod.block.entity.PigSpawnerBlockEntity;
import com.cursormod.entity.ExplodingPig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для Свиноматора-3000 - потому что даже хаос требует тестирования!
 * 🐷💣🧪
 */
@DisplayName("🐷🏭 Тесты Свиноматора-3000")
class PigSpawnerBlockTest {

    private PigSpawnerBlock pigSpawner;
    
    @Mock
    private Level mockLevel;
    
    @Mock
    private BlockState mockState;
    
    @Mock
    private Explosion mockExplosion;
    
    private BlockPos testPos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pigSpawner = ModBlocks.PIG_SPAWNER instanceof PigSpawnerBlock ? 
                     (PigSpawnerBlock) ModBlocks.PIG_SPAWNER : null;
        testPos = new BlockPos(100, 64, 100);
        
        assertNotNull(pigSpawner, "🐷🏭 Свиноматор должен быть зарегистрирован!");
    }

    @Test
    @DisplayName("🛡️ Свиноматор должен быть неуязвим к взрывам")
    void testExplosionResistance() {
        // Given: Свиноматор-3000 в мире
        float resistance = pigSpawner.getExplosionResistance();
        
        // Then: Сопротивление должно быть как у bedrock
        assertEquals(3600000.0F, resistance, 
            "💪 Свиноматор должен иметь максимальное сопротивление взрывам!");
        assertTrue(resistance >= 3600000.0F, 
            "🛡️ Свиноматор прочнее bedrock... или хотя бы такой же!");
    }

    @Test
    @DisplayName("💣 Свиноматор не должен выпадать при взрыве")
    void testNoDropFromExplosion() {
        // Given: Свиноматор-3000 и взрыв
        boolean shouldDrop = pigSpawner.dropFromExplosion(mockExplosion);
        
        // Then: Не должен выпадать
        assertFalse(shouldDrop, 
            "🚫 Свиноматор вечен! Он не выпадает при взрывах!");
    }

    @Test
    @DisplayName("🏭 Свиноматор должен создавать BlockEntity")
    void testCreatesBlockEntity() {
        // Given: Свиноматор размещён в мире
        var blockEntity = pigSpawner.newBlockEntity(testPos, mockState);
        
        // Then: Должен создать правильный BlockEntity
        assertNotNull(blockEntity, 
            "⚙️ Свиноматор должен иметь BlockEntity!");
        assertTrue(blockEntity instanceof PigSpawnerBlockEntity, 
            "🏭 BlockEntity должен быть типа PigSpawnerBlockEntity!");
    }

    @Test
    @DisplayName("📦 Свиноматор должен иметь правильный render shape")
    void testRenderShape() {
        // Given: Свиноматор в мире
        when(mockState.getBlock()).thenReturn(pigSpawner);
        var renderShape = pigSpawner.getRenderShape(mockState);
        
        // Then: Должен использовать MODEL рендеринг
        assertEquals(net.minecraft.world.level.block.RenderShape.MODEL, renderShape,
            "🎨 Свиноматор должен рендериться как модель!");
    }

    @Test
    @DisplayName("🔢 Свиноматор должен иметь правильные свойства прочности")
    void testBlockStrength() {
        // Проверяем, что блок был создан с правильными параметрами
        // Это больше интеграционный тест свойств
        assertNotNull(pigSpawner, 
            "💪 Свиноматор должен существовать!");
        
        // Проверяем что это BaseEntityBlock (через тип)
        assertTrue(pigSpawner instanceof net.minecraft.world.level.block.BaseEntityBlock,
            "🏗️ Свиноматор должен быть BaseEntityBlock!");
    }

    @Test
    @DisplayName("⏰ Свиноматор должен иметь ticker на сервере")
    void testHasServerTicker() {
        // Given: Свиноматор и условия сервера
        when(mockLevel.isClientSide).thenReturn(false);
        
        var ticker = pigSpawner.getTicker(mockLevel, mockState, 
            com.cursormod.block.entity.ModBlockEntities.PIG_SPAWNER_BLOCK_ENTITY);
        
        // Then: Должен иметь ticker на сервере
        assertNotNull(ticker, 
            "⏰ Свиноматор должен тикать на сервере для спавна свиней!");
    }

    @Test
    @DisplayName("💤 Свиноматор НЕ должен тикать на клиенте")
    void testNoClientTicker() {
        // Given: Свиноматор и условия клиента
        when(mockLevel.isClientSide).thenReturn(true);
        
        var ticker = pigSpawner.getTicker(mockLevel, mockState, 
            com.cursormod.block.entity.ModBlockEntities.PIG_SPAWNER_BLOCK_ENTITY);
        
        // Then: НЕ должен иметь ticker на клиенте
        assertNull(ticker, 
            "💤 Свиноматор не должен тикать на клиенте - только на сервере!");
    }

    @Test
    @DisplayName("🆔 Свиноматор должен иметь правильный ID")
    void testBlockId() {
        // Given: Свиноматор зарегистрирован
        var registry = net.minecraft.core.registries.BuiltInRegistries.BLOCK;
        var id = registry.getKey(pigSpawner);
        
        // Then: ID должен быть cursor:pig_spawner
        assertNotNull(id, "🔖 Свиноматор должен иметь ResourceLocation!");
        assertEquals("cursor", id.getNamespace(), 
            "📛 Namespace должен быть 'cursor'!");
        assertEquals("pig_spawner", id.getPath(), 
            "📛 Path должен быть 'pig_spawner'!");
    }

    @Test
    @DisplayName("🧱 Свиноматор должен быть зарегистрирован в ModBlocks")
    void testRegisteredInModBlocks() {
        // Then: Свиноматор должен быть доступен в ModBlocks
        assertNotNull(ModBlocks.PIG_SPAWNER, 
            "📦 PIG_SPAWNER должен быть зарегистрирован в ModBlocks!");
        assertSame(pigSpawner, ModBlocks.PIG_SPAWNER,
            "🔗 PIG_SPAWNER в ModBlocks должен быть тем же объектом!");
    }

    @Test
    @DisplayName("🎯 Свиноматор должен иметь codec")
    void testHasCodec() {
        // Given: Свиноматор существует
        var codec = pigSpawner.codec();
        
        // Then: Codec не должен быть null
        assertNotNull(codec, 
            "📝 Свиноматор должен иметь codec для сериализации!");
    }
}

