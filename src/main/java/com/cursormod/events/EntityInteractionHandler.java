package com.cursormod.events;

import com.cursormod.Cursor;
import com.cursormod.effects.BigPigEffect;
import com.cursormod.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.minecraft.sounds.SoundEvents;

public class EntityInteractionHandler {
    
    public static void register() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClientSide()) {
                return InteractionResult.PASS; // Не обрабатываем на клиенте
            }
            
            ItemStack itemStack = player.getItemInHand(hand);
            
            // Проверяем, что это водка и правый клик на свинью
            if (itemStack.getItem() == ModItems.VODKA && entity instanceof Pig pig) {
                Cursor.LOGGER.info("🐷 Player {} is giving vodka to pig {}!", 
                    player.getName().getString(), pig.getName().getString());
                
                // Добавляем эффект большой свиньи на 60 секунд (1200 тиков)
                pig.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    BigPigEffect.BIG_PIG_EFFECT, 1200, 0));
                
                // Создаем магические частицы
                if (world instanceof ServerLevel serverLevel) {
                    Vec3 pos = pig.position();
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, 
                        pos.x, pos.y + 1.0, pos.z, 20, 0.5, 0.5, 0.5, 0.1);
                    serverLevel.sendParticles(ParticleTypes.CLOUD, 
                        pos.x, pos.y + 1.0, pos.z, 10, 0.3, 0.3, 0.3, 0.05);
                }
                
                // Играем звук свиньи
                pig.playSound(SoundEvents.PIG_AMBIENT, 1.0F, 1.2F);
                
                // Уменьшаем количество водки в стаке
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                    if (itemStack.isEmpty()) {
                        player.setItemInHand(hand, new ItemStack(net.minecraft.world.item.Items.GLASS_BOTTLE));
                    } else {
                        player.getInventory().add(new ItemStack(net.minecraft.world.item.Items.GLASS_BOTTLE));
                    }
                }
                
                Cursor.LOGGER.info("🐷 Pig {} is now big and fast!", pig.getName().getString());
                return InteractionResult.SUCCESS;
            }
            
            return InteractionResult.PASS;
        });
        
        Cursor.LOGGER.info("🐷 EntityInteractionHandler registered! Right-click vodka on pigs to make them big and fast!");
    }
}
