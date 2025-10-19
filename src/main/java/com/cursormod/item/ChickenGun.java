package com.cursormod.item;

import com.cursormod.Cursor;
import com.cursormod.entity.ExplodingChicken;
import com.cursormod.entity.ModEntities;
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

public class ChickenGun extends Item {
    
    public ChickenGun(Properties properties) {
        super(properties);
        Cursor.LOGGER.info("🐔🔫 ChickenGun created! A weapon that shoots exploding chickens!");
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (!level.isClientSide()) {
            // Создаем взрывающуюся курицу
            ExplodingChicken chicken = new ExplodingChicken(ModEntities.EXPLODING_CHICKEN, level);
            
            // Позиция перед игроком
            Vec3 lookDirection = player.getLookAngle();
            Vec3 spawnPos = player.getEyePosition().add(lookDirection.scale(1.5));
            
            chicken.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
            
            // Запускаем курицу в направлении взгляда игрока с большой скоростью
            double speed = 1.5;
            chicken.setDeltaMovement(
                lookDirection.x * speed,
                lookDirection.y * speed + 0.3, // Небольшой подъем
                lookDirection.z * speed
            );
            
            // Добавляем курицу в мир
            level.addFreshEntity(chicken);
            
            // Играем звук выстрела курицы
            level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.CHICKEN_HURT, SoundSource.PLAYERS, 1.0F, 0.5F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.3F, 2.0F);
            
            // Создаем частицы выстрела
            createShootParticles(level, player);
            
            // Уменьшаем прочность предмета
            if (!player.getAbilities().instabuild) {
                itemStack.hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(hand);
                });
            }
            
            Cursor.LOGGER.info("🐔🔫 Chicken gun fired! Exploding chicken launched!");
        }
        
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
    
    private void createShootParticles(Level level, Player player) {
        if (level instanceof ServerLevel serverLevel) {
            // Создаем частицы перьев и дыма из пушки
            Vec3 lookDirection = player.getLookAngle();
            Vec3 particlePos = player.getEyePosition().add(lookDirection.scale(1.5));
            
            serverLevel.sendParticles(ParticleTypes.POOF, 
                particlePos.x, particlePos.y, particlePos.z, 15, 0.2, 0.2, 0.2, 0.05);
            serverLevel.sendParticles(ParticleTypes.CLOUD, 
                particlePos.x, particlePos.y, particlePos.z, 10, 0.15, 0.15, 0.15, 0.03);
        }
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 30; // Время перезарядки
    }
    
    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return net.minecraft.world.item.UseAnim.BOW; // Анимация как у лука
    }
}
