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
import net.minecraft.world.phys.Vec3;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import com.cursormod.item.ModItems;

public class ThorHammerProjectile extends ThrowableItemProjectile {
    
    private boolean isReturning = false;
    private Vec3 startPosition;
    private static final double MAX_DISTANCE = 20.0; // Максимальная дальность полета 20 блоков
    private static final int MAX_FLIGHT_TIME = 200; // 10 секунд максимум
    
    public ThorHammerProjectile(EntityType<? extends ThorHammerProjectile> entityType, Level level) {
        super(entityType, level);
    }
    
    public ThorHammerProjectile(Level level, LivingEntity shooter) {
        super(ModEntities.THOR_HAMMER_PROJECTILE, shooter, level);
        this.startPosition = shooter.position();
    }
    
    @Override
    protected Item getDefaultItem() {
        return ModItems.THOR_HAMMER;
    }
    
    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        
        if (!this.level().isClientSide()) {
            // Наносим смертельный урон - молот Тора убивает с одного попадания!
            float damage = 1000.0F; // Смертельный урон - больше чем у любого моба
            hitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), damage);
            
            // Играем звук попадания
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), 
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 1.0F, 0.8F);
            
            // Создаем эффекты молнии
            createLightningEffects();
            
            // Начинаем возврат
            startReturn();
            
            Cursor.LOGGER.info("💀⚡ Thor's hammer OBLITERATED {} with {} damage! One hit kill!", 
                hitResult.getEntity().getType().getDescription().getString(), damage);
        }
    }
    
    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        
        if (!this.level().isClientSide()) {
            // Играем звук попадания в блок
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), 
                SoundEvents.ANVIL_LAND, SoundSource.NEUTRAL, 1.0F, 1.2F);
            
            // Создаем эффекты удара
            createHitEffects();
            
            // Начинаем возврат сразу после попадания в блок
            startReturn();
            
            Cursor.LOGGER.info("🔨 Thor's hammer hit block at: {}", this.blockPosition());
        }
    }
    
    private void createLightningEffects() {
        if (this.level() instanceof ServerLevel serverLevel) {
            // Молния и электрические искры
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, 
                this.getX(), this.getY(), this.getZ(), 30, 0.5, 0.5, 0.5, 0.2);
            serverLevel.sendParticles(ParticleTypes.CRIT, 
                this.getX(), this.getY(), this.getZ(), 15, 0.3, 0.3, 0.3, 0.1);
            serverLevel.sendParticles(ParticleTypes.ENCHANT, 
                this.getX(), this.getY(), this.getZ(), 10, 0.2, 0.2, 0.2, 0.05);
        }
    }
    
    private void createHitEffects() {
        if (this.level() instanceof ServerLevel serverLevel) {
            // Эффекты удара о блок
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, 
                this.getX(), this.getY(), this.getZ(), 5, 0.1, 0.1, 0.1, 0.1);
            serverLevel.sendParticles(ParticleTypes.CRIT, 
                this.getX(), this.getY(), this.getZ(), 8, 0.2, 0.2, 0.2, 0.1);
        }
    }
    
    private void startReturn() {
        this.isReturning = true;
        Cursor.LOGGER.info("🔨 Thor's hammer started returning to owner!");
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Создаем след из частиц за летящим молотом
        if (this.level() instanceof ServerLevel serverLevel && this.tickCount % 3 == 0) {
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, 
                this.getX(), this.getY(), this.getZ(), 2, 0.0, 0.0, 0.0, 0.0);
        }
        
        if (!this.level().isClientSide()) {
            // Проверяем, не улетел ли молот слишком далеко (20 блоков)
            if (!this.isReturning && this.startPosition != null) {
                double distanceFromStart = this.position().distanceTo(this.startPosition);
                if (distanceFromStart >= MAX_DISTANCE) {
                    Cursor.LOGGER.info("🔨 Thor's hammer reached max distance ({} blocks), returning!", MAX_DISTANCE);
                    startReturn();
                }
            }
            
            // Логика возврата
            if (this.isReturning && this.getOwner() instanceof Player player) {
                // Вычисляем направление к владельцу
                Vec3 ownerPos = player.position().add(0, player.getEyeHeight(), 0);
                Vec3 hammerPos = this.position();
                Vec3 direction = ownerPos.subtract(hammerPos).normalize();
                
                // Ускоряем молот к владельцу
                this.setDeltaMovement(direction.scale(1.2)); // Увеличиваем скорость возврата
                
                // Проверяем, достиг ли молот владельца
                if (this.distanceTo(player) < 2.0) {
                    // Возвращаем молот в инвентарь
                    if (!player.getInventory().add(new ItemStack(ModItems.THOR_HAMMER))) {
                        // Если инвентарь полон, выбрасываем предмет
                        player.drop(new ItemStack(ModItems.THOR_HAMMER), false);
                    }
                    
                    // Играем звук возврата
                    this.level().playSound(null, player.getX(), player.getY(), player.getZ(), 
                        SoundEvents.TRIDENT_RETURN, SoundSource.PLAYERS, 1.0F, 1.0F);
                    
                    // Создаем эффекты возврата
                    if (this.level() instanceof ServerLevel serverLevel) {
                        Vec3 pos = player.position();
                        serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, 
                            pos.x, pos.y + 1.0, pos.z, 25, 0.5, 0.5, 0.5, 0.1);
                        serverLevel.sendParticles(ParticleTypes.ENCHANT, 
                            pos.x, pos.y + 1.0, pos.z, 15, 0.3, 0.3, 0.3, 0.05);
                    }
                    
                    Cursor.LOGGER.info("⚡ Thor's hammer returned to {}!", player.getName().getString());
                    
                    // Удаляем снаряд
                    this.discard();
                    return;
                }
            }
            
            // Удаляем снаряд через максимальное время, если он не вернулся
            if (this.tickCount > MAX_FLIGHT_TIME) {
                Cursor.LOGGER.info("🔨 Thor's hammer timed out, returning!");
                if (!this.isReturning) {
                    startReturn();
                } else {
                    this.discard();
                }
            }
        }
    }
    
    @Override
    protected float getGravity() {
        return 0.0F; // Молот не падает под действием гравитации
    }
}
