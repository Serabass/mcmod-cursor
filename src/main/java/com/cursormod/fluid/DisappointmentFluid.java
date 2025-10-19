package com.cursormod.fluid;

import com.cursormod.block.ModBlocks;
import com.cursormod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.util.RandomSource;

import org.jetbrains.annotations.Nullable;

/**
 * Жидкое Разочарование - темная, унылая жидкость для циников
 */
public abstract class DisappointmentFluid extends FlowingFluid {
    
    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_DISAPPOINTMENT;
    }
    
    @Override
    public Fluid getSource() {
        return ModFluids.DISAPPOINTMENT;
    }
    
    @Override
    public Item getBucket() {
        return ModItems.DISAPPOINTMENT_BUCKET;
    }
    
    @Override
    protected void animateTick(Level level, BlockPos pos, FluidState state, RandomSource random) {
        // Иногда появляются грустные частицы дыма
        if (!state.isSource() && !state.getValue(FALLING)) {
            if (random.nextInt(64) == 0) {
                level.playLocalSound(
                    pos.getX() + 0.5D,
                    pos.getY() + 0.5D,
                    pos.getZ() + 0.5D,
                    SoundEvents.WATER_AMBIENT,
                    SoundSource.BLOCKS,
                    random.nextFloat() * 0.25F + 0.75F,
                    random.nextFloat() + 0.5F,
                    false
                );
            }
        } else if (random.nextInt(10) == 0) {
            level.addParticle(
                ParticleTypes.SMOKE,
                pos.getX() + random.nextDouble(),
                pos.getY() + 1.1D,
                pos.getZ() + random.nextDouble(),
                0.0D, 0.0D, 0.0D
            );
        }
    }
    
    @Override
    @Nullable
    protected ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }
    
    @Override
    protected boolean canConvertToSource(Level level) {
        // Разочарование бесконечно, как и наши страдания
        return true;
    }
    
    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockEntity);
    }
    
    @Override
    protected int getSlopeFindDistance(LevelReader level) {
        return 4;
    }
    
    @Override
    protected int getDropOff(LevelReader level) {
        return 1;
    }
    
    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.DISAPPOINTMENT_BLOCK.defaultBlockState()
            .setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }
    
    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == ModFluids.DISAPPOINTMENT || fluid == ModFluids.FLOWING_DISAPPOINTMENT;
    }
    
    @Override
    public int getTickDelay(LevelReader level) {
        return 5;
    }
    
    @Override
    protected float getExplosionResistance() {
        // Разочарование устойчиво к взрывам - оно вечно
        return 100.0F;
    }
    
    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, 
                                       BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !isSame(fluid);
    }
    
    // Статичная версия жидкости
    public static class Still extends DisappointmentFluid {
        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
        
        @Override
        public int getAmount(FluidState state) {
            return 8;
        }
    }
    
    // Текущая версия жидкости
    public static class Flowing extends DisappointmentFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }
        
        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
        
        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }
    }
}

