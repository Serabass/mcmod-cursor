package com.cursormod.item;

import com.cursormod.Cursor;
import com.cursormod.entity.ThorHammerProjectile;
import com.cursormod.entity.ModEntities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class ThorHammerItem extends Item {
    
    public ThorHammerItem(Properties properties) {
        super(properties); // Используем свойства как есть
        Cursor.LOGGER.info("🔨 ThorHammerItem created! By the power of Asgard!");
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (!level.isClientSide()) {
            // Создаем снаряд молота
            ThorHammerProjectile hammerProjectile = new ThorHammerProjectile(level, player);
            
            // Устанавливаем позицию и направление
            Vec3 lookAngle = player.getLookAngle();
            hammerProjectile.shoot(lookAngle.x, lookAngle.y, lookAngle.z, 1.5F, 1.0F);
            
            // Добавляем снаряд в мир
            level.addFreshEntity(hammerProjectile);
            
            // Играем звук броска
            level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 0.8F);
            
            // Создаем эффекты молнии
            if (level instanceof ServerLevel serverLevel) {
                Vec3 pos = player.position();
                serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, 
                    pos.x, pos.y + 1.0, pos.z, 20, 0.5, 0.5, 0.5, 0.1);
                serverLevel.sendParticles(ParticleTypes.CRIT, 
                    pos.x, pos.y + 1.0, pos.z, 10, 0.3, 0.3, 0.3, 0.05);
            }
            
            // Убираем предмет из руки (временно)
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            
            Cursor.LOGGER.info("⚡ {} threw Thor's hammer! May the gods be with you!", 
                player.getName().getString());
        }
        
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true; // Молот можно зачаровать
    }
    
    @Override
    public int getEnchantmentValue() {
        return 15; // Высокая ценность для зачарований
    }
}
