package com.talhanation.smallships.world.entity.forge;

import com.talhanation.smallships.SmallShipsMod;
import com.talhanation.smallships.world.entity.projectile.CannonBallEntity;
import com.talhanation.smallships.world.entity.ship.BriggEntity;
import com.talhanation.smallships.world.entity.ship.CogEntity;
import com.talhanation.smallships.world.entity.ship.GalleyEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ModEntityTypesImpl {
    private static final Map<Class<? extends Entity>, RegistryObject<EntityType<? extends Entity>>> entries = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends Entity> EntityType<T> getEntityType(Class<T> entityClass) {
        return (EntityType<T>) entries.get(entityClass).get();
    }
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SmallShipsMod.MOD_ID);

    static {
        entries.put(CannonBallEntity.class, ENTITY_TYPES.register(CannonBallEntity.ID,
                () -> EntityType.Builder.of(CannonBallEntity::factory, MobCategory.MISC)
                        .sized(0.25F, 0.25F)
                        .clientTrackingRange(20)
                        .setUpdateInterval(10)
                        .setShouldReceiveVelocityUpdates(true)
                        .build(CannonBallEntity.ID)));

        entries.put(CogEntity.class, ENTITY_TYPES.register(CogEntity.ID,
                () -> EntityType.Builder.of(CogEntity::new, MobCategory.MISC)
                        .sized(3.5F, 1.25F)
                        .clientTrackingRange(20)
                        .setUpdateInterval(10)
                        .setShouldReceiveVelocityUpdates(true)
                        .build(CogEntity.ID)));

        entries.put(BriggEntity.class, ENTITY_TYPES.register(BriggEntity.ID,
                () -> EntityType.Builder.of(BriggEntity::new, MobCategory.MISC)
                        .sized(3.5F, 1.25F)
                        .clientTrackingRange(20)
                        .setUpdateInterval(10)
                        .setShouldReceiveVelocityUpdates(true)
                        .build(BriggEntity.ID)));

        entries.put(GalleyEntity.class, ENTITY_TYPES.register(GalleyEntity.ID,
                () -> EntityType.Builder.of(GalleyEntity::new, MobCategory.MISC)
                        .sized(3.5F, 1.25F)
                        .clientTrackingRange(20)
                        .setUpdateInterval(10)
                        .setShouldReceiveVelocityUpdates(true)
                        .build(GalleyEntity.ID)));
    }
}
