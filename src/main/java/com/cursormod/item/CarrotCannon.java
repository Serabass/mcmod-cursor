package com.cursormod.item;

import com.cursormod.Cursor;
import com.cursormod.entity.CarrotProjectile;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

public class CarrotCannon extends Item {
    
    public CarrotCannon(Properties properties) {
        super(properties);
        Cursor.LOGGER.info("🥕 CarrotCannon created! A weapon that shoots carrots at enemies!");
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (!level.isClientSide()) {
            // Создаем снаряд морковки
            CarrotProjectile carrotProjectile = new CarrotProjectile(level, player);
            
            // Направляем снаряд в сторону взгляда игрока
            Vec3 lookDirection = player.getLookAngle();
            carrotProjectile.shoot(lookDirection.x, lookDirection.y, lookDirection.z, 2.0F, 1.0F);
            
            // Добавляем снаряд в мир
            level.addFreshEntity(carrotProjectile);
            
            // Играем звук выстрела
            level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
            
            // Создаем частицы выстрела
            createShootParticles(level, player);
            
            // Уменьшаем прочность предмета
            if (!player.getAbilities().instabuild) {
                itemStack.hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(hand);
                });
            }
            
            Cursor.LOGGER.info("🥕 Carrot cannon fired! Carrot projectile launched!");
        }
        
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
    
    private void createShootParticles(Level level, Player player) {
        if (level instanceof ServerLevel serverLevel) {
            // Создаем частицы дыма из пушки
            Vec3 lookDirection = player.getLookAngle();
            Vec3 particlePos = player.getEyePosition().add(lookDirection.scale(1.5));
            
            serverLevel.sendParticles(ParticleTypes.SMOKE, 
                particlePos.x, particlePos.y, particlePos.z, 10, 0.1, 0.1, 0.1, 0.05);
            serverLevel.sendParticles(ParticleTypes.FLAME, 
                particlePos.x, particlePos.y, particlePos.z, 5, 0.05, 0.05, 0.05, 0.02);
        }
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 40; // Время перезарядки
    }
    
    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return net.minecraft.world.item.UseAnim.BOW; // Анимация как у лука
    }
}
