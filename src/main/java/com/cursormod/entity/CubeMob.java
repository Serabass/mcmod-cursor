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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CubeMob extends PathfinderMob {
    
    public CubeMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        Cursor.LOGGER.info("🔷 CubeMob created! A new cubic friend has spawned!");
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }
    
    @Override
    protected void registerGoals() {
        // Плавание в воде
        this.goalSelector.addGoal(0, new FloatGoal(this));
        
        // Случайное блуждание по суше
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        
        // Случайное блуждание в воде
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        
        // Смотреть на игрока
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
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
    public void tick() {
        super.tick();
        
        // Логируем каждые 100 тиков (5 секунд) для отладки
        if (this.tickCount % 100 == 0) {
            Cursor.LOGGER.info("🔷 CubeMob at {} is alive! Health: {}/{}", 
                this.blockPosition(), (int)this.getHealth(), (int)this.getMaxHealth());
        }
    }
}
