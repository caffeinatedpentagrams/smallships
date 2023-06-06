package com.talhanation.smallships;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTags extends TagsProvider<DamageType> {

    public ModDamageTypeTags(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, provider, SmallShipsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull Provider provider) {
        tag(DamageTypeTags.BYPASSES_ARMOR)
                .add(SmallShipsDamageTypes.CANNONBALL)
                .add(SmallShipsDamageTypes.DAMAGE_SOURCE_SHIP);

        tag(DamageTypeTags.IS_PROJECTILE)
                .add(SmallShipsDamageTypes.CANNONBALL);
        tag(DamageTypeTags.IS_EXPLOSION)
                .add(SmallShipsDamageTypes.CANNONBALL);
    }
}
