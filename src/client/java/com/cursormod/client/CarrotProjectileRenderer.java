package com.cursormod.client;

import com.cursormod.Cursor;
import com.cursormod.entity.CarrotProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class CarrotProjectileRenderer extends ThrownItemRenderer<CarrotProjectile> {
    
    public CarrotProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        Cursor.LOGGER.info("🥕 CarrotProjectileRenderer created! Ready to render flying carrots!");
    }
}
