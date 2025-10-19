package com.cursormod.item;

import com.cursormod.Cursor;
import com.cursormod.effects.DrunkEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class VodkaItem extends Item {
    
    public VodkaItem(Properties properties) {
        super(properties);
        Cursor.LOGGER.info("🍺 VodkaItem created! A drink that will make you see double!");
    }
    
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        Cursor.LOGGER.info("🍺 VodkaItem.finishUsingItem() called! User: {}, World: {}", 
            user.getName().getString(), world.dimension().location());
        
        ItemStack itemStack = super.finishUsingItem(stack, world, user);
        
        if (user instanceof Player player) {
            Cursor.LOGGER.info("🍺 Player {} drank vodka! Hunger: {}/20", 
                player.getName().getString(), player.getFoodData().getFoodLevel());
            
            // Добавляем эффект пьянства на 30 секунд (600 тиков)
            player.addEffect(new MobEffectInstance(DrunkEffect.DRUNK_EFFECT, 600, 0));
            
            // Добавляем тошноту для дополнительного эффекта
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0));
            
            // Создаем частицы алкоголя
            if (world instanceof ServerLevel serverLevel) {
                Vec3 pos = player.position();
                serverLevel.sendParticles(ParticleTypes.SMOKE, 
                    pos.x, pos.y + 1.0, pos.z, 15, 0.5, 0.5, 0.5, 0.1);
            }
            
            // Играем звук питья
            player.playSound(SoundEvents.PLAYER_BURP, 1.0F, 0.8F);
            world.gameEvent(user, GameEvent.EAT, user.position());
            
            if (!player.getAbilities().instabuild) {
                if (stack.isEmpty()) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        
        return itemStack;
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 40; // Время питья как у зелья
    }
    
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK; // Анимация питья
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (player.getFoodData().needsFood() || player.canEat(false)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }
    
    @Override
    public boolean isEdible() {
        return true; // Водка съедобна!
    }
    
    @Override
    public net.minecraft.world.food.FoodProperties getFoodProperties() {
        return new net.minecraft.world.food.FoodProperties.Builder()
            .nutrition(4) // 4 единицы голода
            .saturationMod(0.8f) // 0.8 насыщения
            .build();
    }
}
