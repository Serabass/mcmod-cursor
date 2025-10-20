package com.cursormod.client.renderer;

import com.cursormod.Cursor;
import com.cursormod.entity.FirePotatoProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class FirePotatoRenderer extends ThrownItemRenderer<FirePotatoProjectile> {
    public FirePotatoRenderer(EntityRendererProvider.Context context) {
        super(context);
        Cursor.LOGGER.info("🔥🥔 FirePotatoRenderer created!");
    }
}


