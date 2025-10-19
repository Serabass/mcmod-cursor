package com.cursormod.item;

import com.cursormod.Cursor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class RedBananaItem extends Item {
    public RedBananaItem(Properties properties) {
        super(properties);
        Cursor.LOGGER.info("🍌 RedBananaItem created! This is our custom red banana class!");
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        Cursor.LOGGER.info("🍌 RedBananaItem.finishUsingItem() called! User: {}, World: {}", 
            user.getName().getString(), world.dimension().location());
        
        ItemStack itemStack = super.finishUsingItem(stack, world, user);
        
        // Красный банан восстанавливает 6 единиц голода и 7.2 насыщения
        // Это больше чем обычный хлеб (5 голода, 6 насыщения)
        if (user instanceof Player player) {
            Cursor.LOGGER.info("🍌 Player {} ate red banana! Hunger: {}/20", 
                player.getName().getString(), player.getFoodData().getFoodLevel());
            
            if (!player.getAbilities().instabuild) {
                if (stack.isEmpty()) {
                    return new ItemStack(Items.AIR);
                }
                player.getInventory().add(new ItemStack(Items.AIR));
            }
        }
        
        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // Время поедания как у хлеба
    }
}