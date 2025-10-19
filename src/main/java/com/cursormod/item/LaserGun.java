package com.cursormod.item;

import com.cursormod.Cursor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;

public class LaserGun extends Item {
    
    public LaserGun(Properties properties) {
        super(properties);
        Cursor.LOGGER.info("🔫 LaserGun created! A weapon that burns everything in its path!");
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (!level.isClientSide()) {
            // Получаем направление взгляда игрока
            Vec3 lookDirection = player.getLookAngle();
            Vec3 startPos = player.getEyePosition();
            
            // Выстреливаем лазер на расстояние 50 блоков
            double maxDistance = 50.0;
            Vec3 endPos = startPos.add(lookDirection.scale(maxDistance));
            
            // Проверяем столкновение с блоками
            BlockHitResult hitResult = level.clip(new ClipContext(
                startPos, endPos, 
                ClipContext.Block.OUTLINE, 
                ClipContext.Fluid.NONE, 
                player
            ));
            
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos hitPos = hitResult.getBlockPos();
                BlockState hitBlock = level.getBlockState(hitPos);
                
                Cursor.LOGGER.info("🔫 Laser hit block at {}: {}", hitPos, hitBlock.getBlock());
                
                // Поджигаем блок, если это возможно
                if (hitBlock.isAir() || hitBlock.getBlock() == Blocks.WATER || hitBlock.getBlock() == Blocks.LAVA) {
                    // Ставим огонь на место попадания
                    level.setBlock(hitPos, Blocks.FIRE.defaultBlockState(), 3);
                    Cursor.LOGGER.info("🔥 Block at {} set on fire!", hitPos);
                } else {
                    // Если блок нельзя поджечь, поджигаем блок рядом
                    BlockPos firePos = hitPos.relative(hitResult.getDirection());
                    if (level.getBlockState(firePos).isAir()) {
                        level.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 3);
                        Cursor.LOGGER.info("🔥 Block next to {} set on fire!", hitPos);
                    }
                }
                
                // Создаем частицы лазера
                createLaserParticles(level, startPos, hitResult.getLocation());
                
                // Играем звук выстрела
                player.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F);
                
                // Создаем событие
                level.gameEvent(player, GameEvent.PROJECTILE_SHOOT, hitPos);
                
            } else {
                // Лазер не попал ни во что, но всё равно создаем частицы
                createLaserParticles(level, startPos, endPos);
                player.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F);
                Cursor.LOGGER.info("🔫 Laser shot into the void!");
            }
            
            // Уменьшаем прочность предмета
            if (!player.getAbilities().instabuild) {
                itemStack.hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(hand);
                });
            }
        }
        
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
    
    private void createLaserParticles(Level level, Vec3 startPos, Vec3 endPos) {
        if (level instanceof ServerLevel serverLevel) {
            // Создаем частицы лазера по всей траектории
            Vec3 direction = endPos.subtract(startPos).normalize();
            double distance = startPos.distanceTo(endPos);
            int particleCount = (int) (distance * 2); // 2 частицы на блок
            
            for (int i = 0; i < particleCount; i++) {
                double progress = (double) i / particleCount;
                Vec3 particlePos = startPos.add(direction.scale(progress * distance));
                
                // Красные частицы для лазера
                serverLevel.sendParticles(ParticleTypes.FLAME, 
                    particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);
                serverLevel.sendParticles(ParticleTypes.LAVA, 
                    particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);
            }
            
            // Создаем взрыв частиц в точке попадания
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, 
                endPos.x, endPos.y, endPos.z, 5, 0.5, 0.5, 0.5, 0.1);
            serverLevel.sendParticles(ParticleTypes.FLAME, 
                endPos.x, endPos.y, endPos.z, 20, 1.0, 1.0, 1.0, 0.2);
        }
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 20; // Время перезарядки
    }
    
    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return net.minecraft.world.item.UseAnim.BOW; // Анимация как у лука
    }
}
