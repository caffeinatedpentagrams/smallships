package com.talhanation.smallships.world.damagesource;

import com.ibm.icu.util.CodePointTrie;
import com.talhanation.smallships.SmallShipsDamageTypes;
import com.talhanation.smallships.world.entity.projectile.AbstractCannonBall;
import com.talhanation.smallships.world.entity.ship.Ship;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("DataFlowIssue")
public class ModDamageSourceTypes {

    public static DamageSource CANNONBALL;
    public static DamageSource DAMAGE_SOURCE_SHIP;
    public static void init(RegistryAccess registryAccess) {
        CANNONBALL = getDamageSource(registryAccess, SmallShipsDamageTypes.CANNONBALL);
        DAMAGE_SOURCE_SHIP = getDamageSource(registryAccess, SmallShipsDamageTypes.DAMAGE_SOURCE_SHIP);
    }

    private static DamageSource getDamageSource(RegistryAccess registryAccess, ResourceKey<DamageType> key) {
        return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }

    private static DamageSource getEntityDamageSource(RegistryAccess registryAccess, ResourceKey<DamageType> key, Entity directEntity, Entity causingEntity) {
        return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), directEntity, causingEntity);
    }

    public static DamageSource cannonBall(Entity entity, Entity owner) {
        return getEntityDamageSource(entity.level.registryAccess(), SmallShipsDamageTypes.CANNONBALL, entity, owner);
    }

    public static DamageSource shipCollision(Entity entity, Entity owner) {
        return getEntityDamageSource(entity.level.registryAccess(), SmallShipsDamageTypes.DAMAGE_SOURCE_SHIP, entity, owner);
    }
    /*public static DamageSource cannonBall(AbstractCannonBall cannonBall, @Nullable Entity owner) {
        return new DamageSource(, cannonBall, owner);
        //return ((DamageSourceAccessor)new IndirectEntityDamageSource("cannonBall", cannonBall, owner).setProjectile().setExplosion()).callBypassArmor();
    }

    public static DamageSource shipCollision(Ship ship, @Nullable Entity owner) {

        //return ((DamageSourceAccessor)new IndirectEntityDamageSource("shipCollision", ship, owner)).callBypassArmor();
    }*/
}
