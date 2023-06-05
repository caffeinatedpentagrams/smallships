package com.talhanation.smallships;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    protected static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SmallShipsDamageTypes.CANNONBALL, new DamageType("cannonball", 0.4F));
        context.register(SmallShipsDamageTypes.DAMAGE_SOURCE_SHIP, new DamageType("damage_source_ship", 0.4F));
    }
}
