package com.cursormod.events;

import com.cursormod.Cursor;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Обработчик событий для загорания свиней при приближении игрока
 * Теперь свиньи будут гореть как бекон на сковородке! 🔥🐷
 */
public class PigIgnitionHandler {
    
    private static final double IGNITION_DISTANCE = 3.0; // Дистанция загорания в блоках
    private static final int IGNITION_CHECK_INTERVAL = 20; // Проверяем каждую секунду (20 тиков)
    
    // Карта для хранения счётчиков для каждой свиньи
    private static final Map<UUID, Integer> pigCounters = new HashMap<>();
    
    /**
     * Регистрируем обработчик событий
     */
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(PigIgnitionHandler::onWorldTick);
        Cursor.LOGGER.info("🔥🐷 PigIgnitionHandler зарегистрирован! Свиньи теперь будут загораться при приближении игроков!");
    }
    
    /**
     * Обработчик тика мира
     */
    private static void onWorldTick(ServerLevel level) {
        // Проверяем каждую свинью в мире
        level.getAllEntities().forEach(entity -> {
            if (entity instanceof Pig pig) {
                checkPigIgnition(pig);
            }
        });
    }
    
    /**
     * Проверяем, нужно ли поджечь свинью
     */
    private static void checkPigIgnition(Pig pig) {
        UUID pigId = pig.getUUID();
        
        // Получаем счётчик для этой свиньи
        int ignitionCheckCounter = pigCounters.getOrDefault(pigId, 0);
        ignitionCheckCounter++;
        
        // Проверяем каждые IGNITION_CHECK_INTERVAL тиков
        if (ignitionCheckCounter >= IGNITION_CHECK_INTERVAL) {
            ignitionCheckCounter = 0;
            
            // Если свинья уже горит, не проверяем
            if (!pig.isOnFire()) {
                checkForNearbyPlayers(pig);
            }
        }
        
        // Сохраняем счётчик обратно
        pigCounters.put(pigId, ignitionCheckCounter);
    }
    
    /**
     * Проверяем, есть ли рядом игроки для загорания
     */
    private static void checkForNearbyPlayers(Pig pig) {
        // Ищем ближайшего игрока
        Player nearestPlayer = pig.level().getNearestPlayer(pig, IGNITION_DISTANCE);
        
        if (nearestPlayer != null) {
            double distance = pig.distanceTo(nearestPlayer);
            
            if (distance <= IGNITION_DISTANCE) {
                ignitePig(pig, nearestPlayer);
            }
        }
    }
    
    /**
     * Поджигаем свинью и добавляем эффекты
     */
    private static void ignitePig(Pig pig, Player player) {
        // Поджигаем свинью на 5 секунд (100 тиков)
        pig.setRemainingFireTicks(100);
        
        // Логируем событие
        Cursor.LOGGER.info("🔥🐷 Свинья загорелась от приближения игрока {}! Дистанция: {:.2f} блоков", 
            player.getName().getString(), pig.distanceTo(player));
        
        // Звуковые эффекты
        pig.playSound(net.minecraft.sounds.SoundEvents.PIG_DEATH, 1.0F, 1.5F);
        pig.playSound(net.minecraft.sounds.SoundEvents.FIRE_EXTINGUISH, 1.0F, 0.8F);
        
        // Добавляем частицы огня
        if (pig.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                net.minecraft.core.particles.ParticleTypes.FLAME,
                pig.getX(), 
                pig.getY() + 0.5, 
                pig.getZ(),
                10, // количество частиц
                0.5, 0.5, 0.5, // разброс
                0.1 // скорость
            );
        }
        
        // Заставляем свинью паниковать и бегать
        pig.getBrain().setMemoryWithExpiry(
            net.minecraft.world.entity.ai.memory.MemoryModuleType.IS_PANICKING, 
            true, 
            100L // паникуем 5 секунд
        );
    }
}
