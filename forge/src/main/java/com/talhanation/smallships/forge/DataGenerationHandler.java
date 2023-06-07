package com.talhanation.smallships.forge;

import com.talhanation.smallships.SmallShipsMod;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.core.HolderLookup.Provider;
import java.util.concurrent.CompletableFuture;

import java.util.Set;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SmallShipsMod.MOD_ID)
public class DataGenerationHandler {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
        CompletableFuture<Provider> lookupProviderWithOwn = lookupProvider.thenApply(provider ->
                DatapackRegistriesGenerator.BUILDER.buildPatch(
                        RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), provider));


        generator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(output, lookupProvider, DatapackRegistriesGenerator.BUILDER,
                        Set.of(SmallShipsMod.MOD_ID)));
        //generator.addProvider(event.includeServer(),
                //new ModDamageTypeTagsGenerator(output, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(),
                new ModDamageTypeTagsGenerator(output, lookupProviderWithOwn, existingFileHelper));
    }
}


