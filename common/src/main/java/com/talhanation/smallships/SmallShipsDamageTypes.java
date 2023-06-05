package com.talhanation.smallships;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class SmallShipsDamageTypes {
    public static final ResourceKey<DamageType> CANNONBALL = register("cannonball");
    public static final ResourceKey<DamageType> DAMAGE_SOURCE_SHIP = register("damage_source_ship");

    private static ResourceKey<DamageType> register(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(SmallShipsMod.MOD_ID, name));
    }
}