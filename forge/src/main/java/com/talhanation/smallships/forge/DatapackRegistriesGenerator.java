package com.talhanation.smallships.forge;

import com.talhanation.smallships.SmallShipsMod;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraft.core.HolderLookup.Provider;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class DatapackRegistriesGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, ModDamageTypesGenerator::bootstrap);

    public DatapackRegistriesGenerator(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(SmallShipsMod.MOD_ID));
    }
}
