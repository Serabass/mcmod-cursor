package com.cursormod.events;

import com.cursormod.Cursor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.List;

public class ServerTickHandler {
    
    private static int tickCounter = 0;
    
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            
            // Проверяем каждые 20 тиков (1 секунда)
            if (tickCounter >= 20) {
                tickCounter = 0;
                
                // Проверяем все уровни
                for (ServerLevel level : server.getAllLevels()) {
                    checkForPlayersNearOakTrees(level);
                }
            }
        });
        
        Cursor.LOGGER.info("🔥 ServerTickHandler registered! Oak trees will catch fire when players get too close!");
    }
    
    private static void checkForPlayersNearOakTrees(ServerLevel level) {
        List<ServerPlayer> players = level.players();
        
        for (ServerPlayer player : players) {
            BlockPos playerPos = player.blockPosition();
            
            // Проверяем область 6x6x6 вокруг игрока (3 блока в каждую сторону)
            for (int x = -3; x <= 3; x++) {
                for (int y = -3; y <= 3; y++) {
                    for (int z = -3; z <= 3; z++) {
                        BlockPos checkPos = playerPos.offset(x, y, z);
                        BlockState blockState = level.getBlockState(checkPos);
                        
                        // Проверяем, является ли блок дубовым бревном или листьями
                        if (isOakBlock(blockState)) {
                            double distance = Math.sqrt(playerPos.distSqr(checkPos));
                            
                            if (distance <= 3.0) {
                                // Поджигаем дуб!
                                Cursor.LOGGER.info("🔥 Player {} is too close to oak tree at {}! Distance: {:.2f}", 
                                    player.getName().getString(), checkPos, distance);
                                
                                // Ставим огонь рядом с дубом
                                BlockPos firePos = checkPos.above();
                                if (level.getBlockState(firePos).isAir()) {
                                    level.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 3);
                                }
                                
                                // Также поджигаем сам блок дуба
                                level.setBlock(checkPos, Blocks.FIRE.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static boolean isOakBlock(BlockState blockState) {
        return blockState.is(Blocks.OAK_LOG) || 
               blockState.is(Blocks.OAK_WOOD) || 
               blockState.is(Blocks.OAK_LEAVES) ||
               blockState.is(Blocks.OAK_PLANKS) ||
               blockState.is(Blocks.OAK_SLAB) ||
               blockState.is(Blocks.OAK_STAIRS) ||
               blockState.is(Blocks.OAK_FENCE) ||
               blockState.is(Blocks.OAK_FENCE_GATE) ||
               blockState.is(Blocks.OAK_DOOR) ||
               blockState.is(Blocks.OAK_TRAPDOOR) ||
               blockState.is(Blocks.OAK_PRESSURE_PLATE) ||
               blockState.is(Blocks.OAK_BUTTON);
    }
}
