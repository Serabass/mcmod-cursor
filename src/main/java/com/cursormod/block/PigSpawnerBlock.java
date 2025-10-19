package com.cursormod.block;

import com.cursormod.Cursor;
import com.cursormod.block.entity.ModBlockEntities;
import com.cursormod.block.entity.PigSpawnerBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Свиноматор-3000 - для тех, кто устал от спокойствия
 * Теперь работает АВТОМАТИЧЕСКИ по таймеру!
 */
public class PigSpawnerBlock extends BaseEntityBlock {
    
    public static final MapCodec<PigSpawnerBlock> CODEC = simpleCodec(PigSpawnerBlock::new);
    
    public PigSpawnerBlock(Properties properties) {
        super(properties);
        Cursor.LOGGER.info("🐷🏭 PigSpawnerBlock created! Automatic mass pig production facility!");
    }
    
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PigSpawnerBlockEntity(pos, state);
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.PIG_SPAWNER_BLOCK_ENTITY, PigSpawnerBlockEntity::tick);
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    
    /**
     * Свиноматор-3000 НЕУЯЗВИМ к взрывам! Он как Nokia 3310 - вечный!
     */
    @Override
    public float getExplosionResistance() {
        return 3600000.0F; // Сопротивление взрыву больше, чем у коренной породы (bedrock = 3600000.0F)
    }
    
    /**
     * Блок не может быть уничтожен взрывом
     */
    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return false; // Не выпадает при взрыве
    }
    
    /**
     * Вызывается при размещении блока
     */
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        
        if (!level.isClientSide && !oldState.is(this)) {
            Cursor.LOGGER.info("🐷🏭 PigSpawner placed! Ready to spawn chaos! INDESTRUCTIBLE MODE ACTIVATED! 💪");
        }
    }
}

 