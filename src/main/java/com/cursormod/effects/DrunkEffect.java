package com.cursormod.effects;

import com.cursormod.Cursor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DrunkEffect extends MobEffect {
    
    public static final MobEffect DRUNK_EFFECT = Registry.register(
        BuiltInRegistries.MOB_EFFECT,
        new ResourceLocation(Cursor.MOD_ID, "drunk"),
        new DrunkEffect()
    );
    
    public DrunkEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFD700); // Золотой цвет
        Cursor.LOGGER.info("🍺 DrunkEffect created! Time to get tipsy!");
    }
    
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // Инвертируем управление - вперед становится назад
            Vec3 movement = player.getDeltaMovement();
            
            // Получаем текущее направление взгляда
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            
            // Инвертируем движение
            double forward = -movement.z; // Вперед становится назад
            double sideways = -movement.x; // Влево становится вправо
            
            // Применяем инвертированное движение
            player.setDeltaMovement(sideways, movement.y, forward);
            
            // Добавляем случайное покачивание
            if (player.tickCount % 20 == 0) { // Каждую секунду
                float randomYaw = (float) (Math.random() * 10 - 5); // -5 до +5 градусов
                float randomPitch = (float) (Math.random() * 10 - 5);
                
                player.setYRot(yaw + randomYaw);
                player.setXRot(pitch + randomPitch);
                
                Cursor.LOGGER.info("🍺 Player {} is drunk! Movement inverted! Yaw: {:.2f}, Pitch: {:.2f}", 
                    player.getName().getString(), yaw + randomYaw, pitch + randomPitch);
            }
        }
    }
    
    // Убираем @Override, так как этот метод не переопределяется
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // Эффект применяется каждый тик
    }
    
    // Убираем @Override, так как этот метод не переопределяется
    public String getDescriptionId() {
        return "effect.cursor.drunk";
    }
}
