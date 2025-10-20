package com.cursormod.entity;

import com.cursormod.Cursor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class FirePotatoProjectile extends ThrowableItemProjectile {
    
    public FirePotatoProjectile(EntityType<? extends FirePotatoProjectile> entityType, Level level) {
        super(entityType, level);
    }
    
    public FirePotatoProjectile(Level level, LivingEntity shooter) {
        super(ModEntities.FIRE_POTATO_PROJECTILE, shooter, level);
    }
    
    @Override
    protected Item getDefaultItem() {
        return Items.BAKED_POTATO;
    }
    
    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!this.level().isClientSide()) {
            // Наносим урон и поджигаем цель
            float damage = 6.0F;
            hitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), damage);
            hitResult.getEntity().setSecondsOnFire(5);
            
            // Звук поджаривания
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.9F, 1.0F);
            
            createFlameParticles(10, 0.15, 0.15, 0.15, 0.02);
            Cursor.LOGGER.info("🔥🥔 FirePotato hit entity: {} for {} damage!", 
                hitResult.getEntity().getType().getDescription().getString(), damage);
        }
    }
    
    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            // Пытаемся поджечь блок в точке попадания
            BlockPos pos = this.blockPosition();
            if (this.level().isEmptyBlock(pos)) {
                BlockState fire = Blocks.FIRE.defaultBlockState();
                if (this.level().setBlock(pos, fire, 11)) {
                    this.level().playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 0.7F, 1.0F);
                }
            }
            createFlameParticles(20, 0.25, 0.25, 0.25, 0.03);
        }
    }
    
    private void createFlameParticles(int count, double dx, double dy, double dz, double speed) {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SMALL_FLAME,
                this.getX(), this.getY(), this.getZ(), count, dx, dy, dz, speed);
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                this.getX(), this.getY(), this.getZ(), Math.max(3, count / 3), dx, dy, dz, speed / 2);
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel serverLevel && this.tickCount % 2 == 0) {
            serverLevel.sendParticles(ParticleTypes.SMALL_FLAME,
                this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
        }
        if (this.tickCount > 100) {
            this.discard();
        }
    }
}


