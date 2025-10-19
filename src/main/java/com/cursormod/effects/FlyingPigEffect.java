package com.cursormod.effects;

import com.cursormod.Cursor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

public class FlyingPigEffect extends MobEffect {
    
    public static final MobEffect FLYING_PIG_EFFECT = Registry.register(
        BuiltInRegistries.MOB_EFFECT,
        new ResourceLocation(Cursor.MOD_ID, "flying_pig"),
        new FlyingPigEffect()
    );
    
    public FlyingPigEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF69B4); // Розовый цвет
        Cursor.LOGGER.info("🐷 FlyingPigEffect created! Pigs will soar through the skies!");
    }
    
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Pig pig) {
            // Делаем свинью летающей
            pig.setNoGravity(true);
            
            // Добавляем вертикальное движение вверх
            Vec3 movement = pig.getDeltaMovement();
            pig.setDeltaMovement(movement.x, 0.2, movement.z); // Постоянно поднимаемся
            
            // Создаем частицы крыльев
            if (pig.level() instanceof ServerLevel serverLevel) {
                Vec3 pos = pig.position();
                // Частицы вокруг свиньи для эффекта крыльев
                serverLevel.sendParticles(ParticleTypes.CLOUD, 
                    pos.x, pos.y + 0.5, pos.z, 3, 0.3, 0.1, 0.3, 0.05);
                serverLevel.sendParticles(ParticleTypes.ENCHANT, 
                    pos.x, pos.y + 0.5, pos.z, 2, 0.2, 0.1, 0.2, 0.02);
            }
            
            // Логируем каждые 100 тиков (5 секунд)
            if (pig.tickCount % 100 == 0) {
                Cursor.LOGGER.info("🐷 Pig {} is flying! Position: {:.2f}, {:.2f}, {:.2f}", 
                    pig.getName().getString(), pig.getX(), pig.getY(), pig.getZ());
            }
        }
    }
    
    // Эффект применяется каждый тик
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
    
    public String getDescriptionId() {
        return "effect.cursor.flying_pig";
    }
}
