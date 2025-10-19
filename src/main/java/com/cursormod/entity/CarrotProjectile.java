package com.cursormod.entity;

import com.cursormod.Cursor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

public class CarrotProjectile extends ThrowableItemProjectile {
    
    public CarrotProjectile(EntityType<? extends CarrotProjectile> entityType, Level level) {
        super(entityType, level);
    }
    
    public CarrotProjectile(Level level, LivingEntity shooter) {
        super(ModEntities.CARROT_PROJECTILE, shooter, level);
    }
    
    @Override
    protected Item getDefaultItem() {
        return Items.CARROT;
    }
    
    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        
        if (!this.level().isClientSide()) {
            // Наносим урон цели
            float damage = 4.0F; // Урон морковки
            hitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), damage);
            
            // Играем звук попадания
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), 
                SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.NEUTRAL, 1.0F, 1.2F);
            
            // Создаем частицы попадания
            createHitParticles();
            
            Cursor.LOGGER.info("🥕 Carrot hit entity: {} for {} damage!", 
                hitResult.getEntity().getType().getDescription().getString(), damage);
        }
    }
    
    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        
        if (!this.level().isClientSide()) {
            // Играем звук попадания в блок
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), 
                SoundEvents.ROOTED_DIRT_BREAK, SoundSource.NEUTRAL, 0.5F, 1.0F);
            
            // Создаем частицы попадания
            createHitParticles();
            
            Cursor.LOGGER.info("🥕 Carrot hit block at: {}", this.blockPosition());
        }
    }
    
    private void createHitParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            // Создаем частицы морковки при попадании
            serverLevel.sendParticles(ParticleTypes.ITEM_SLIME, 
                this.getX(), this.getY(), this.getZ(), 8, 0.2, 0.2, 0.2, 0.1);
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, 
                this.getX(), this.getY(), this.getZ(), 3, 0.1, 0.1, 0.1, 0.05);
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Создаем след из частиц за летящей морковкой
        if (this.level() instanceof ServerLevel serverLevel && this.tickCount % 2 == 0) {
            serverLevel.sendParticles(ParticleTypes.ITEM_SLIME, 
                this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
        }
        
        // Удаляем снаряд через 5 секунд, если он не попал ни во что
        if (this.tickCount > 100) {
            this.discard();
        }
    }
}
