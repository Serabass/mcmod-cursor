package com.cursormod.client.renderer;

import com.cursormod.Cursor;
import com.cursormod.entity.CubeMob;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CubeMobRenderer extends MobRenderer<CubeMob, CowModel<CubeMob>> {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation(Cursor.MOD_ID, "textures/entity/cube_mob.png");
    
    public CubeMobRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.5f);
        Cursor.LOGGER.info("🔷 CubeMobRenderer created! Ready to render cubic friends!");
    }
    
    @Override
    public ResourceLocation getTextureLocation(CubeMob entity) {
        return TEXTURE;
    }
    
    @Override
    protected void scale(CubeMob entity, com.mojang.blaze3d.vertex.PoseStack poseStack, float partialTick) {
        float scale = entity.getScale();
        poseStack.scale(scale, scale, scale);
        // Убираем частые логи, чтобы не засорять консоль
        if (entity.tickCount % 100 == 0) {
            Cursor.LOGGER.info("🔷 Rendering CubeMob with scale: " + String.format("%.2f", scale));
        }
    }
}
