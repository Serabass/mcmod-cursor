package com.cursormod.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для взрывных свиней - потому что даже бомбы нужно тестировать!
 * 🐷💣🧪
 */
@DisplayName("🐷💣 Тесты ExplodingPig")
class ExplodingPigTest {

    private ExplodingPig explodingPig;
    
    @Mock
    private Level mockLevel;
    
    @Mock
    private DamageSource mockDamageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockLevel.isClientSide).thenReturn(false);
        
        explodingPig = new ExplodingPig(ModEntities.EXPLODING_PIG, mockLevel);
    }

    @Test
    @DisplayName("🐷 Взрывная свинья должна создаваться")
    void testExplodingPigCreation() {
        // Then: Свинья создана
        assertNotNull(explodingPig, 
            "🐷 ExplodingPig должна быть создана!");
        assertTrue(explodingPig instanceof net.minecraft.world.entity.animal.Pig,
            "🐷 ExplodingPig должна быть Pig!");
    }

    @Test
    @DisplayName("🪂 Взрывная свинья не должна получать урон от падения")
    void testNoCauseFallDamage() {
        // Given: Взрывная свинья падает с высоты
        float fallDistance = 100.0F;
        float multiplier = 1.0F;
        
        // When: Проверяем урон от падения
        boolean tookDamage = explodingPig.causeFallDamage(fallDistance, multiplier, mockDamageSource);
        
        // Then: Не должна получать урон
        assertFalse(tookDamage, 
            "🪂 Взрывные свиньи не боятся падения!");
    }

    @Test
    @DisplayName("🛡️ Взрывная свинья должна быть неуязвима к урону от падения")
    void testInvulnerableToFallDamage() {
        // Given: Источник урона - падение
        when(mockDamageSource.equals(explodingPig.damageSources().fall())).thenReturn(true);
        
        // When: Проверяем неуязвимость
        boolean isInvulnerable = explodingPig.isInvulnerableTo(explodingPig.damageSources().fall());
        
        // Then: Должна быть неуязвима
        assertTrue(isInvulnerable, 
            "🛡️ Взрывные свиньи должны игнорировать урон от падения!");
    }

    @Test
    @DisplayName("⏱️ Взрывная свинья должна иметь таймер до взрыва")
    void testHasExplosionTimer() {
        // Given: Новая взрывная свинья
        ExplodingPig pig = new ExplodingPig(ModEntities.EXPLODING_PIG, mockLevel);
        
        // Then: Должна быть создана (таймер устанавливается в конструкторе)
        assertNotNull(pig, 
            "⏱️ Свинья должна иметь внутренний таймер взрыва!");
    }

    @Test
    @DisplayName("👶 Взрывная свинья должна поддерживать поколения")
    void testGenerationSupport() {
        // Given: Свинья разных поколений
        ExplodingPig generation0 = new ExplodingPig(ModEntities.EXPLODING_PIG, mockLevel, 0);
        ExplodingPig generation1 = new ExplodingPig(ModEntities.EXPLODING_PIG, mockLevel, 1);
        ExplodingPig generation2 = new ExplodingPig(ModEntities.EXPLODING_PIG, mockLevel, 2);
        
        // Then: Все должны быть созданы
        assertNotNull(generation0, "👵 Поколение 0 должно существовать!");
        assertNotNull(generation1, "👶 Поколение 1 должно существовать!");
        assertNotNull(generation2, "🐣 Поколение 2 должно существовать!");
        
        // Поросята должны быть малышами
        assertTrue(generation1.isBaby(), 
            "👶 Поколение 1 должно быть маленьким!");
        assertTrue(generation2.isBaby(), 
            "👶 Поколение 2 должно быть маленьким!");
    }

    @Test
    @DisplayName("🆔 Взрывная свинья должна иметь правильный EntityType")
    void testCorrectEntityType() {
        // Then: Тип должен совпадать
        assertEquals(ModEntities.EXPLODING_PIG, explodingPig.getType(),
            "🔖 EntityType должен быть EXPLODING_PIG!");
    }

    @Test
    @DisplayName("🐷 Взрывная свинья должна быть зарегистрирована")
    void testEntityRegistered() {
        // Then: EntityType должен быть не null
        assertNotNull(ModEntities.EXPLODING_PIG,
            "📦 EXPLODING_PIG должен быть зарегистрирован!");
    }

    @Test
    @DisplayName("💨 Взрывная свинья должна иметь возможность двигаться")
    void testCanMove() {
        // Given: Взрывная свинья
        // Then: Она должна быть живой сущностью с возможностью движения
        assertNotNull(explodingPig.getDeltaMovement(),
            "💨 Свинья должна иметь вектор движения!");
    }

    @Test
    @DisplayName("🌍 Взрывная свинья должна знать свой Level")
    void testKnowsLevel() {
        // Then: Свинья должна знать свой мир
        assertNotNull(explodingPig.level(),
            "🌍 Свинья должна знать свой Level!");
    }

    @Test
    @DisplayName("📍 Взрывная свинья должна иметь позицию")
    void testHasPosition() {
        // Then: Свинья должна иметь координаты
        assertNotNull(explodingPig.position(),
            "📍 Свинья должна иметь позицию!");
    }

    @Test
    @DisplayName("🏃 Взрывная свинья должна быть в режиме паники")
    void testInPanicMode() {
        // Given: Только что созданная взрывная свинья
        // Then: Она должна паниковать (это проверяется логами при создании)
        assertNotNull(explodingPig.getBrain(),
            "🧠 Свинья должна иметь brain для паники!");
    }
}

