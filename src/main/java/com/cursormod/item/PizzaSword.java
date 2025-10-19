package com.cursormod.item;

import com.cursormod.Cursor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

public class PizzaSword extends SwordItem {
    
    public PizzaSword(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        Cursor.LOGGER.info("🍕 PizzaSword created! A delicious weapon that feeds and fights!");
    }
    
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Восстанавливаем голод при ударе
        if (attacker instanceof Player player) {
            int hungerToRestore = 2; // Восстанавливаем 2 единицы голода
            float saturationToRestore = 0.5f; // И немного насыщения
            
            player.getFoodData().eat(hungerToRestore, saturationToRestore);
            
            Cursor.LOGGER.info("🍕 Player {} ate some pizza! Hunger: {}/20", 
                player.getName().getString(), player.getFoodData().getFoodLevel());
            
            // Создаем частицы пиццы
            if (player.level() instanceof ServerLevel serverLevel) {
                Vec3 pos = target.position();
                serverLevel.sendParticles(ParticleTypes.ITEM_SLIME, 
                    pos.x, pos.y + 1.0, pos.z, 10, 0.5, 0.5, 0.5, 0.1);
            }
            
            // Играем звук поедания
            attacker.playSound(SoundEvents.PLAYER_BURP, 1.0F, 1.0F);
        }
        
        return super.hurtEnemy(stack, target, attacker);
    }
    
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT; // Анимация поедания
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 16; // Быстрое поедание
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (player.getFoodData().needsFood()) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }
    
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            // Восстанавливаем больше голода при использовании
            int hungerToRestore = 6;
            float saturationToRestore = 1.2f;
            
            player.getFoodData().eat(hungerToRestore, saturationToRestore);
            
            Cursor.LOGGER.info("🍕 Player {} finished eating pizza! Hunger: {}/20", 
                player.getName().getString(), player.getFoodData().getFoodLevel());
            
            // Создаем частицы пиццы
            if (level instanceof ServerLevel serverLevel) {
                Vec3 pos = player.position();
                serverLevel.sendParticles(ParticleTypes.ITEM_SLIME, 
                    pos.x, pos.y + 1.0, pos.z, 20, 1.0, 1.0, 1.0, 0.2);
            }
            
            // Играем звук поедания
            player.playSound(SoundEvents.PLAYER_BURP, 1.0F, 1.0F);
            level.gameEvent(livingEntity, GameEvent.EAT, livingEntity.position());
        }
        
        return super.finishUsingItem(stack, level, livingEntity);
    }
    
    @Override
    public boolean isEdible() {
        return true; // Пицца-меч съедобен!
    }
    
    @Override
    public FoodProperties getFoodProperties() {
        return new FoodProperties.Builder()
            .nutrition(6) // 6 единиц голода
            .saturationMod(1.2f) // 1.2 насыщения
            .build();
    }
}
