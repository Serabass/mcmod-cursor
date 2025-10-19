package com.cursormod.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

/**
 * Рендерер полос здоровья над мобами.
 * Потому что каждый должен знать, насколько близок к смерти! 💀
 */
public class HealthBarRenderer {
    
    private static final float BAR_WIDTH = 0.5f;
    private static final float BAR_HEIGHT = 0.08f;
    private static final float BAR_Y_OFFSET = 0.5f;
    private static final double MAX_RENDER_DISTANCE = 32.0; // Не будем показывать за 32 блока
    
    /**
     * Регистрирует рендерер в системе событий Fabric
     */
    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(HealthBarRenderer::renderHealthBars);
    }
    
    /**
     * Отрисовывает полосы здоровья для всех живых сущностей
     */
    private static void renderHealthBars(WorldRenderContext context) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) {
            return;
        }
        
        Vec3 cameraPos = context.camera().getPosition();
        PoseStack poseStack = context.matrixStack();
        
        // Проходимся по всем живым сущностям в мире
        for (LivingEntity entity : mc.level.getEntitiesOfClass(LivingEntity.class, 
                mc.player.getBoundingBox().inflate(MAX_RENDER_DISTANCE))) {
            
            // Не показываем для игрока (у него свой UI)
            if (entity == mc.player) {
                continue;
            }
            
            // Не показываем мертвым (хотя это было бы цинично)
            if (!entity.isAlive()) {
                continue;
            }
            
            // Проверяем дистанцию
            Vec3 entityPos = entity.position();
            double distance = cameraPos.distanceTo(entityPos);
            if (distance > MAX_RENDER_DISTANCE) {
                continue;
            }
            
            renderHealthBar(poseStack, entity, cameraPos, context.camera().getYRot(), 
                           context.camera().getXRot());
        }
    }
    
    /**
     * Рисует полосу здоровья для конкретной сущности
     */
    private static void renderHealthBar(PoseStack poseStack, LivingEntity entity, 
                                       Vec3 cameraPos, float cameraYaw, float cameraPitch) {
        
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        float healthPercentage = Math.min(health / maxHealth, 1.0f);
        
        // Позиция над головой моба
        Vec3 entityPos = entity.position();
        double x = entityPos.x - cameraPos.x;
        double y = entityPos.y + entity.getBbHeight() + BAR_Y_OFFSET - cameraPos.y;
        double z = entityPos.z - cameraPos.z;
        
        poseStack.pushPose();
        
        // Перемещаемся к позиции над мобом
        poseStack.translate(x, y, z);
        
        // Поворачиваем чтобы полоса всегда смотрела на камеру
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-cameraYaw));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(cameraPitch));
        
        // Масштабируем полосу (чем дальше, тем меньше не делаем - пусть будет одинаковая)
        float scale = 0.025f;
        poseStack.scale(-scale, -scale, scale);
        
        Matrix4f matrix = poseStack.last().pose();
        
        // Настройка рендеринга
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableDepthTest(); // Всегда видна поверх блоков
        
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        
        // Черный фон
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        float bgWidth = BAR_WIDTH / scale;
        float bgHeight = BAR_HEIGHT / scale;
        
        addQuad(buffer, matrix, -bgWidth/2, 0, bgWidth/2, bgHeight, 
               0, 0, 0, 0.5f);
        tesselator.end();
        
        // Красный фон (потерянное здоровье)
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        float padding = bgWidth * 0.05f;
        float innerWidth = bgWidth - padding * 2;
        float innerHeight = bgHeight - padding * 2;
        
        addQuad(buffer, matrix, 
               -innerWidth/2, padding, 
               innerWidth/2, bgHeight - padding,
               0.8f, 0.0f, 0.0f, 0.8f);
        tesselator.end();
        
        // Зеленая полоса (текущее здоровье)
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        float healthWidth = innerWidth * healthPercentage;
        
        // Цвет меняется от зеленого к желтому к красному в зависимости от здоровья
        float r = healthPercentage < 0.5f ? 1.0f : 1.0f - (healthPercentage - 0.5f) * 2.0f;
        float g = healthPercentage > 0.5f ? 1.0f : healthPercentage * 2.0f;
        float b = 0.0f;
        
        addQuad(buffer, matrix,
               -innerWidth/2, padding,
               -innerWidth/2 + healthWidth, bgHeight - padding,
               r, g, b, 0.9f);
        tesselator.end();
        
        // Восстанавливаем настройки рендеринга
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        
        poseStack.popPose();
    }
    
    /**
     * Добавляет прямоугольник в буфер
     */
    private static void addQuad(BufferBuilder buffer, Matrix4f matrix, 
                               float x1, float y1, float x2, float y2,
                               float r, float g, float b, float a) {
        buffer.vertex(matrix, x1, y1, 0).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, x1, y2, 0).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, x2, y2, 0).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, x2, y1, 0).color(r, g, b, a).endVertex();
    }
}

