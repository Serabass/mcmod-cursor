package com.cursormod.entity;

import com.cursormod.Cursor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;

public class ExplodingPig extends Pig {
    
    private static final EntityDataAccessor<Integer> FUSE_TIME = 
        SynchedEntityData.defineId(ExplodingPig.class, EntityDataSerializers.INT);
    
    private int ticksUntilExplosion;
    private boolean isExploding = false;
    private boolean hasSpawnedPiglets = false; // Флаг, чтобы не спавнить поросят дважды
    private int ticksAlive = 0; // Счётчик времени жизни свиньи
    private int generation = 0; // Поколение свиньи (0 - мама, 1 - поросёнок, 2 - поросёнок поросёнка)
    
    public ExplodingPig(EntityType<? extends Pig> entityType, Level level) {
        super(entityType, level);
        // Рандомное время до взрыва от 2 до 10 секунд (40-200 тиков)
        this.ticksUntilExplosion = 40 + level.random.nextInt(161);
        
        // Включаем режим паники - свинья будет бегать как ненормальная!
        this.brain.setMemoryWithExpiry(
            net.minecraft.world.entity.ai.memory.MemoryModuleType.IS_PANICKING, 
            true, 
            (long)ticksUntilExplosion
        );
        
        Cursor.LOGGER.info("🐷💣 ExplodingPig created! Will explode in {} ticks!", ticksUntilExplosion);
    }
    
    /**
     * Взрывные свиньи не получают урон от падения - они слишком крутые для этого!
     */
    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, net.minecraft.world.damagesource.DamageSource damageSource) {
        // Возвращаем false - нет урона от падения!
        return false;
    }
    
    /**
     * Дополнительная защита - иммунитет к урону от падения
     */
    @Override
    public boolean isInvulnerableTo(net.minecraft.world.damagesource.DamageSource source) {
        // Проверяем, является ли урон от падения
        if (source == this.damageSources().fall()) {
            return true; // Игнорируем урон от падения
        }
        return super.isInvulnerableTo(source);
    }
    
    // Конструктор для поросят с указанием поколения
    public ExplodingPig(EntityType<? extends Pig> entityType, Level level, int generation) {
        this(entityType, level);
        this.generation = generation;
        this.setBaby(true); // Делаем маленьким
        Cursor.LOGGER.info("🐖💣 ExplodingPig generation {} created!", generation);
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FUSE_TIME, 0);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide() && !isExploding) {
            ticksUntilExplosion--;
            ticksAlive++; // Увеличиваем счётчик времени жизни
            
            // Обновляем синхронизированные данные для клиента
            this.entityData.set(FUSE_TIME, ticksUntilExplosion);
            
            // Спавним поросят через 40 тиков (2 секунды) после создания
            if (!hasSpawnedPiglets && ticksAlive >= 40) {
                spawnPiglets();
                hasSpawnedPiglets = true;
            }
            
            // Создаем частицы дыма когда близко к взрыву
            if (ticksUntilExplosion < 40) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SMOKE, 
                        this.getX(), this.getY() + 0.5, this.getZ(), 
                        2, 0.1, 0.1, 0.1, 0.01);
                }
            }
            
            // Взрыв!
            if (ticksUntilExplosion <= 0) {
                explode();
            }
        }
        
        // На клиенте создаем частицы
        if (this.level().isClientSide()) {
            int fuseTime = this.entityData.get(FUSE_TIME);
            if (fuseTime < 40 && fuseTime > 0) {
                // Красные частицы когда близко к взрыву
                this.level().addParticle(ParticleTypes.FLAME, 
                    this.getX() + (this.random.nextDouble() - 0.5) * 0.5,
                    this.getY() + this.random.nextDouble() * 0.5,
                    this.getZ() + (this.random.nextDouble() - 0.5) * 0.5,
                    0, 0, 0);
            }
        }
    }
    
    private void spawnPiglets() {
        // Определяем количество поросят в зависимости от поколения
        int pigletCount = 0;
        boolean shouldSpawn = false;
        
        if (generation == 0) {
            // Свинья-мама: 50% шанс спавна 3-7 поросят
            if (this.random.nextBoolean()) {
                pigletCount = 3 + this.random.nextInt(5);
                shouldSpawn = true;
            }
        } else if (generation == 1) {
            // Поросёнок: 30% шанс спавна 1-2 маленьких поросят
            if (this.random.nextFloat() < 0.3f) {
                pigletCount = 1 + this.random.nextInt(2);
                shouldSpawn = true;
            }
        }
        // generation >= 2 не спавнят поросят (ограничение глубины)
        
        if (shouldSpawn && pigletCount > 0) {
            Cursor.LOGGER.info("🐷👶 ExplodingPig generation {} spawning {} piglets!", generation, pigletCount);
            
            for (int i = 0; i < pigletCount; i++) {
                // Создаем поросенка следующего поколения
                ExplodingPig piglet = new ExplodingPig(ModEntities.EXPLODING_PIG, this.level(), generation + 1);
                
                // Позиция рядом со свиньей-мамой
                double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
                double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;
                piglet.setPos(
                    this.getX() + offsetX,
                    this.getY(),
                    this.getZ() + offsetZ
                );
                
                // Даем поросенку начальную скорость (разбегаются в стороны)
                piglet.setDeltaMovement(
                    offsetX * 0.3,
                    0.3,
                    offsetZ * 0.3
                );
                
                // Добавляем в мир
                this.level().addFreshEntity(piglet);
                
                // Звук свиньи (выше тон для поросят)
                float pitch = 1.5F + (generation * 0.3F); // Чем меньше поросёнок, тем выше голос
                piglet.playSound(net.minecraft.sounds.SoundEvents.PIG_AMBIENT, 1.0F, pitch);
            }
            
            // Звук свиньи-родителя
            this.playSound(net.minecraft.sounds.SoundEvents.PIG_AMBIENT, 1.5F, 0.8F + (generation * 0.2F));
            
            // Создаем частицы сердечек (как при размножении)
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HEART, 
                    this.getX(), this.getY() + 0.5 + (generation * 0.2), this.getZ(), 
                    5 + (3 - generation) * 2, 0.5, 0.5, 0.5, 0.1);
            }
        }
    }
    
    private void explode() {
        if (!this.level().isClientSide() && !isExploding) {
            isExploding = true;
            
            Cursor.LOGGER.info("🐷💥 ExplodingPig exploded at {}", this.blockPosition());
            
            // Создаем взрыв (сила 2.0 - как у крипера)
            this.level().explode(
                this, 
                this.getX(), 
                this.getY(), 
                this.getZ(), 
                2.0F, 
                Level.ExplosionInteraction.MOB
            );
            
            // Создаем много частиц
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, 
                    this.getX(), this.getY(), this.getZ(), 
                    1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.FLAME, 
                    this.getX(), this.getY(), this.getZ(), 
                    50, 1.0, 1.0, 1.0, 0.2);
            }
            
            // Создаем огонь после взрыва! 🔥
            createFireAfterExplosion();
            
            // Удаляем свинью
            this.discard();
        }
    }
    
    /**
     * Создает огонь после взрыва свиньи! 🔥
     * Иногда оставляет огонь в радиусе взрыва для дополнительного хаоса!
     */
    private void createFireAfterExplosion() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        
        // Шанс создать огонь зависит от поколения свиньи
        // Поколение 0 (мама): 30% шанс
        // Поколение 1 (поросенок): 50% шанс  
        // Поколение 2+ (поросенок поросенка): 70% шанс
        float fireChance = 0.3f + (generation * 0.2f);
        fireChance = Math.min(fireChance, 0.7f); // Максимум 70%
        
        if (this.random.nextFloat() < fireChance) {
            Cursor.LOGGER.info("🔥 ExplodingPig generation {} left fire behind! Chaos level: {}%", 
                generation, (int)(fireChance * 100));
            
            // Количество огня зависит от поколения
            // Поколение 0: 1-3 блока огня
            // Поколение 1: 2-4 блока огня
            // Поколение 2+: 3-6 блоков огня
            int minFire = 1 + generation;
            int maxFire = 3 + (generation * 2);
            int fireBlocks = minFire + this.random.nextInt(maxFire - minFire + 1);
            
            for (int i = 0; i < fireBlocks; i++) {
                // Выбираем случайную позицию в радиусе 3 блоков от взрыва
                int offsetX = this.random.nextInt(7) - 3; // -3 до +3
                int offsetZ = this.random.nextInt(7) - 3; // -3 до +3
                
                BlockPos firePos = this.blockPosition().offset(offsetX, 0, offsetZ);
                
                // Проверяем, можно ли поставить огонь в эту позицию
                if (canPlaceFireAt(firePos)) {
                    // Ставим огонь!
                    serverLevel.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 3);
                    
                    // Создаем частицы огня
                    serverLevel.sendParticles(ParticleTypes.FLAME, 
                        firePos.getX() + 0.5, firePos.getY() + 0.5, firePos.getZ() + 0.5, 
                        10, 0.2, 0.2, 0.2, 0.1);
                    
                    Cursor.LOGGER.info("🔥 Fire placed at {}", firePos);
                }
            }
        }
    }
    
    /**
     * Проверяет, можно ли поставить огонь в указанной позиции
     */
    private boolean canPlaceFireAt(BlockPos pos) {
        // Проверяем, что блок в этой позиции можно заменить огнем
        if (!this.level().getBlockState(pos).isAir()) {
            return false;
        }
        
        // Проверяем, что под огнем есть твердый блок
        BlockPos belowPos = pos.below();
        return this.level().getBlockState(belowPos).isSolidRender(this.level(), belowPos);
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TicksUntilExplosion", this.ticksUntilExplosion);
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.ticksUntilExplosion = compound.getInt("TicksUntilExplosion");
    }
    
    @Override
    public boolean isPushable() {
        return false; // Нельзя толкать взрывающуюся свинью!
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Pig.createAttributes()
            .add(Attributes.MAX_HEALTH, 10.0)
            .add(Attributes.MOVEMENT_SPEED, 0.35); // Быстрее обычной свиньи!
    }
    
    @Override
    public Component getName() {
        // Получаем время до взрыва в секундах
        int fuseTime = this.entityData.get(FUSE_TIME);
        double secondsLeft = fuseTime / 20.0; // 20 тиков = 1 секунда
        
        // Форматируем время с одним знаком после запятой
        String timeString = String.format("%.1f", secondsLeft);
        
        // Выбираем цвет в зависимости от оставшегося времени
        ChatFormatting color;
        if (secondsLeft > 5.0) {
            color = ChatFormatting.GREEN; // Зелёный - много времени
        } else if (secondsLeft > 2.0) {
            color = ChatFormatting.YELLOW; // Жёлтый - среднее время
        } else {
            color = ChatFormatting.RED; // Красный - мало времени!
        }
        
        // Возвращаем имя с таймером и эмодзи бомбы
        return Component.literal("💣 " + timeString + "s").withStyle(color);
    }
    
    @Override
    public boolean hasCustomName() {
        return true; // Всегда показываем имя
    }
    
    @Override
    public boolean isCustomNameVisible() {
        return true; // Имя всегда видно
    }
}

