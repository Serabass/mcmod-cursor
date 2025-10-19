package com.cursormod.entity;

import com.cursormod.Cursor;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.AABB;

public class CubeMob extends PathfinderMob {
    
    private float scale = 1.0f; // Текущий масштаб моба
    
    public CubeMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        
        // Устанавливаем начальный размер (100%)
        this.scale = 1.0f;
        
        Cursor.LOGGER.info("🔷 CubeMob created! A new cubic friend has spawned! Size: {:.2f}", this.scale);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }
    
    @Override
    protected void registerGoals() {
        // НЕ плаваем в воде - боимся её!
        // this.goalSelector.addGoal(0, new FloatGoal(this));
        
        // Избегаем воды как чумы!
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        
        // Случайное блуждание только по суше
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        
        // Смотреть на игрока
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.COW_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }
    
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }
    
    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        
        if (hurt) {
            // Обновляем размер после получения урона
            updateSize();
            
            Cursor.LOGGER.info("🔷 CubeMob hurt! New size: {:.2f} (Health: {}/{})", 
                getScale(), (int)this.getHealth(), (int)this.getMaxHealth());
        }
        
        return hurt;
    }
    
    private void updateSize() {
        float healthPercent = this.getHealth() / this.getMaxHealth();
        this.scale = 0.1f + (healthPercent * 0.9f); // От 10% до 100%
        
        Cursor.LOGGER.info("🔷 CubeMob resized to {:.2f} (Health: {:.1f}%)", 
            this.scale, healthPercent * 100);
    }
    
    public float getScale() {
        return this.scale;
    }
    
    @Override
    public EntityDimensions getDimensions(Pose pose) {
        EntityDimensions baseDimensions = super.getDimensions(pose);
        return baseDimensions.scale(this.scale);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Проверяем, не в воде ли мы
        if (this.isInWater()) {
            Cursor.LOGGER.warn("💀 CubeMob at {} is in WATER! This is terrifying! Health: {}/{}", 
                this.blockPosition(), (int)this.getHealth(), (int)this.getMaxHealth());
            
            // Убиваем моба, если он в воде
            this.hurt(this.damageSources().drown(), 1000.0f);
            Cursor.LOGGER.error("💀 CubeMob died from water fear! RIP cubic friend...");
        }
        
        // Логируем каждые 100 тиков (5 секунд) для отладки
        if (this.tickCount % 100 == 0) {
            Cursor.LOGGER.info("🔷 CubeMob at {} is alive! Health: {}/{} Size: {:.2f}", 
                this.blockPosition(), (int)this.getHealth(), (int)this.getMaxHealth(), getScale());
        }
    }
}
