package com.cursormod.block.entity;

import com.cursormod.Cursor;
import com.cursormod.entity.ExplodingPig;
import com.cursormod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Автоматический Свиноматор-3000 - теперь с таймером!
 * Спавнит взрывных свиней каждые N секунд
 */
public class PigSpawnerBlockEntity extends BlockEntity {
    
    private static final int SPAWN_INTERVAL = 100; // 5 секунд (100 тиков)
    private int tickCounter = 0;
    
    public PigSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PIG_SPAWNER_BLOCK_ENTITY, pos, state);
    }
    
    /**
     * Вызывается каждый тик (20 раз в секунду)
     */
    public static void tick(Level level, BlockPos pos, BlockState state, PigSpawnerBlockEntity blockEntity) {
        if (level.isClientSide) {
            return; // Работаем только на сервере
        }
        
        blockEntity.tickCounter++;
        
        // Каждые SPAWN_INTERVAL тиков спавним свиней
        if (blockEntity.tickCounter >= SPAWN_INTERVAL) {
            blockEntity.tickCounter = 0; // Сбрасываем счётчик
            blockEntity.spawnPigs(level, pos);
        }
    }
    
    /**
     * Спавн свиней - автоматический режим!
     */
    private void spawnPigs(Level level, BlockPos pos) {
        int pigCount = 2 + level.random.nextInt(3); // 2-4 свиньи
        
        Cursor.LOGGER.info("🐷🏭 PigSpawner автоматически активирован! Spawning " + pigCount + " explosive pigs!");
        
        for (int i = 0; i < pigCount; i++) {
            ExplodingPig pig = new ExplodingPig(ModEntities.EXPLODING_PIG, level);
            
            // Спавним вокруг устройства
            double offsetX = (level.random.nextDouble() - 0.5) * 3;
            double offsetZ = (level.random.nextDouble() - 0.5) * 3;
            
            pig.moveTo(
                pos.getX() + 0.5 + offsetX, 
                pos.getY() + 1, 
                pos.getZ() + 0.5 + offsetZ,
                level.random.nextFloat() * 360.0F, 
                0.0F
            );
            
            // Даем им случайную скорость для эффектности
            pig.setDeltaMovement(
                (level.random.nextDouble() - 0.5) * 0.3,
                0.3 + level.random.nextDouble() * 0.2,
                (level.random.nextDouble() - 0.5) * 0.3
            );
            
            level.addFreshEntity(pig);
            
            Cursor.LOGGER.info("🐷💣 Spawned explosive pig #" + (i+1) + " at " + pig.position());
        }
        
        // Звуковые эффекты
        level.playSound(null, pos, SoundEvents.PIGLIN_ANGRY, SoundSource.BLOCKS, 1.0F, 0.8F);
        level.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 0.7F, 1.5F);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.tickCounter = tag.getInt("TickCounter");
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("TickCounter", this.tickCounter);
    }
}

