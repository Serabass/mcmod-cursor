package com.cursormod.entity;

import com.cursormod.Cursor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.ChatFormatting;

public class ExplodingPig extends Pig {
    
    private static final EntityDataAccessor<Integer> FUSE_TIME = 
        SynchedEntityData.defineId(ExplodingPig.class, EntityDataSerializers.INT);
    
    private int ticksUntilExplosion;
    private boolean isExploding = false;
    
    public ExplodingPig(EntityType<? extends Pig> entityType, Level level) {
        super(entityType, level);
        // Рандомное время до взрыва от 2 до 10 секунд (40-200 тиков)
        this.ticksUntilExplosion = 40 + level.random.nextInt(161);
        Cursor.LOGGER.info("🐷💣 ExplodingPig created! Will explode in {} ticks!", ticksUntilExplosion);
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FUSE_TIME, 0);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide() && !isExploding) {
            ticksUntilExplosion--;
            
            // Обновляем синхронизированные данные для клиента
            this.entityData.set(FUSE_TIME, ticksUntilExplosion);
            
            // Свинья бегает как ненормальная - увеличиваем скорость
            if (this.onGround()) {
                // Случайное направление движения
                if (this.random.nextInt(10) == 0) {
                    double randomX = (this.random.nextDouble() - 0.5) * 2.0;
                    double randomZ = (this.random.nextDouble() - 0.5) * 2.0;
                    this.setDeltaMovement(randomX, 0.5, randomZ);
                }
            }
            
            // Создаем частицы дыма когда близко к взрыву
            if (ticksUntilExplosion < 40) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SMOKE, 
                        this.getX(), this.getY() + 0.5, this.getZ(), 
                        2, 0.1, 0.1, 0.1, 0.01);
                }
            }
            
            // Взрыв!
            if (ticksUntilExplosion <= 0) {
                explode();
            }
        }
        
        // На клиенте создаем частицы
        if (this.level().isClientSide()) {
            int fuseTime = this.entityData.get(FUSE_TIME);
            if (fuseTime < 40 && fuseTime > 0) {
                // Красные частицы когда близко к взрыву
                this.level().addParticle(ParticleTypes.FLAME, 
                    this.getX() + (this.random.nextDouble() - 0.5) * 0.5,
                    this.getY() + this.random.nextDouble() * 0.5,
                    this.getZ() + (this.random.nextDouble() - 0.5) * 0.5,
                    0, 0, 0);
            }
        }
    }
    
    private void explode() {
        if (!this.level().isClientSide() && !isExploding) {
            isExploding = true;
            
            Cursor.LOGGER.info("🐷💥 ExplodingPig exploded at {}", this.blockPosition());
            
            // Создаем взрыв (сила 2.0 - как у крипера)
            this.level().explode(
                this, 
                this.getX(), 
                this.getY(), 
                this.getZ(), 
                2.0F, 
                Level.ExplosionInteraction.MOB
            );
            
            // Создаем много частиц
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, 
                    this.getX(), this.getY(), this.getZ(), 
                    1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.FLAME, 
                    this.getX(), this.getY(), this.getZ(), 
                    50, 1.0, 1.0, 1.0, 0.2);
            }
            
            // Удаляем свинью
            this.discard();
        }
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TicksUntilExplosion", this.ticksUntilExplosion);
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.ticksUntilExplosion = compound.getInt("TicksUntilExplosion");
    }
    
    @Override
    public boolean isPushable() {
        return false; // Нельзя толкать взрывающуюся свинью!
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Pig.createAttributes()
            .add(Attributes.MAX_HEALTH, 10.0)
            .add(Attributes.MOVEMENT_SPEED, 0.35); // Быстрее обычной свиньи!
    }
    
    @Override
    public Component getName() {
        // Получаем время до взрыва в секундах
        int fuseTime = this.entityData.get(FUSE_TIME);
        double secondsLeft = fuseTime / 20.0; // 20 тиков = 1 секунда
        
        // Форматируем время с одним знаком после запятой
        String timeString = String.format("%.1f", secondsLeft);
        
        // Выбираем цвет в зависимости от оставшегося времени
        ChatFormatting color;
        if (secondsLeft > 5.0) {
            color = ChatFormatting.GREEN; // Зелёный - много времени
        } else if (secondsLeft > 2.0) {
            color = ChatFormatting.YELLOW; // Жёлтый - среднее время
        } else {
            color = ChatFormatting.RED; // Красный - мало времени!
        }
        
        // Возвращаем имя с таймером и эмодзи бомбы
        return Component.literal("💣 " + timeString + "s").withStyle(color);
    }
    
    @Override
    public boolean hasCustomName() {
        return true; // Всегда показываем имя
    }
    
    @Override
    public boolean isCustomNameVisible() {
        return true; // Имя всегда видно
    }
}

