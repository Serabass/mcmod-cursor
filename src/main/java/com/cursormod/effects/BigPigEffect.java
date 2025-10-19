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
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BigPigEffect extends MobEffect {
    
    public static final MobEffect BIG_PIG_EFFECT = Registry.register(
        BuiltInRegistries.MOB_EFFECT,
        new ResourceLocation(Cursor.MOD_ID, "big_pig"),
        new BigPigEffect()
    );
    
    private float scale = 1.0f; // Текущий размер свиньи
    
    public BigPigEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF4500); // Оранжевый цвет
        Cursor.LOGGER.info("🐷 BigPigEffect created! Pigs will grow big and run fast!");
    }
    
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Pig pig) {
            // Устанавливаем случайный размер при первом применении эффекта
            if (pig.tickCount % 1200 == 0) { // Каждую минуту меняем размер
                this.scale = 1.0f + (float)(Math.random() * 2.0); // От 1.0 до 3.0
                Cursor.LOGGER.info("🐷 Pig {} grew to size: {:.2f}!", 
                    pig.getName().getString(), this.scale);
            }
            
            // Увеличиваем скорость движения свиньи, добавляя к текущему движению
            Vec3 currentMovement = pig.getDeltaMovement();
            double speedMultiplier = 1.5; // Увеличиваем скорость в 1.5 раза
            pig.setDeltaMovement(
                currentMovement.x * speedMultiplier, 
                currentMovement.y, 
                currentMovement.z * speedMultiplier
            );
            
            // Создаем частицы роста
            if (pig.level() instanceof ServerLevel serverLevel) {
                Vec3 pos = pig.position();
                // Частицы роста вокруг свиньи
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, 
                    pos.x, pos.y + 0.5, pos.z, 2, 0.3, 0.1, 0.3, 0.05);
                serverLevel.sendParticles(ParticleTypes.ENCHANT, 
                    pos.x, pos.y + 0.5, pos.z, 1, 0.2, 0.1, 0.2, 0.02);
            }
            
            // Логируем каждые 100 тиков (5 секунд)
            if (pig.tickCount % 100 == 0) {
                Cursor.LOGGER.info("🐷 Pig {} is big and fast! Size: {:.2f}, Speed: {:.2f}", 
                    pig.getName().getString(), this.scale, 
                    pig.getAttributeValue(Attributes.MOVEMENT_SPEED));
            }
        }
    }
    
    // Эффект применяется каждый тик
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
    
    public String getDescriptionId() {
        return "effect.cursor.big_pig";
    }
}
