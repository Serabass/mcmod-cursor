package com.cursormod.item;

import com.cursormod.Cursor;
import com.cursormod.entity.FirePotatoProjectile;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FirePotatoGun extends Item {
    
    public FirePotatoGun(Properties properties) {
        super(properties);
        Cursor.LOGGER.info("🔥🥔 FirePotatoGun created! Time to bake some mobs!");
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (!level.isClientSide()) {
            FirePotatoProjectile projectile = new FirePotatoProjectile(level, player);
            Vec3 dir = player.getLookAngle();
            projectile.shoot(dir.x, dir.y, dir.z, 2.2F, 0.8F);
            level.addFreshEntity(projectile);
            
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            createMuzzleParticles(level, player);
            
            if (!player.getAbilities().instabuild) {
                itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
            
            Cursor.LOGGER.info("🔥🥔 FirePotatoGun fired!");
        }
        
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
    
    private void createMuzzleParticles(Level level, Player player) {
        if (level instanceof ServerLevel serverLevel) {
            Vec3 look = player.getLookAngle();
            Vec3 pos = player.getEyePosition().add(look.scale(1.5));
            serverLevel.sendParticles(ParticleTypes.SMALL_FLAME,
                pos.x, pos.y, pos.z, 8, 0.05, 0.05, 0.05, 0.02);
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                pos.x, pos.y, pos.z, 6, 0.05, 0.05, 0.05, 0.02);
        }
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 35;
    }
    
    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return net.minecraft.world.item.UseAnim.BOW;
    }
}


