package com.cursormod.client;

import com.cursormod.Cursor;
import com.cursormod.entity.ThorHammerProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ThorHammerRenderer extends ThrownItemRenderer<ThorHammerProjectile> {
    
    public ThorHammerRenderer(EntityRendererProvider.Context context) {
        super(context, 1.0f, true);
        Cursor.LOGGER.info("🔨⚡ ThorHammerRenderer created! Ready to render flying hammers!");
    }
}
