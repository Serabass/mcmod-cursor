package com.cursormod.fluid;

import com.cursormod.Cursor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class ModFluids {
    
    // Жидкое Разочарование - для тех, кто устал от оптимизма
    public static FlowingFluid FLOWING_DISAPPOINTMENT;
    public static FlowingFluid DISAPPOINTMENT;
    
    public static Fluid register(String name, Fluid fluid) {
        return Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(Cursor.MOD_ID, name), fluid);
    }
    
    public static void registerModFluids() {
        DISAPPOINTMENT = (FlowingFluid) register("disappointment", 
            new DisappointmentFluid.Still());
        FLOWING_DISAPPOINTMENT = (FlowingFluid) register("flowing_disappointment", 
            new DisappointmentFluid.Flowing());
            
        Cursor.LOGGER.info("💧 Зарегистрированы жидкости разочарования. Как символично...");
    }
}

