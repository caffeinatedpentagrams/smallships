package com.talhanation.smallships.forge;

import com.talhanation.smallships.SmallShipsDamageTypes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypesGenerator {
    protected static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SmallShipsDamageTypes.CANNONBALL, new DamageType("cannonball", 0.4F));
        context.register(SmallShipsDamageTypes.DAMAGE_SOURCE_SHIP, new DamageType("damage_source_ship", 0.4F));
    }
}
