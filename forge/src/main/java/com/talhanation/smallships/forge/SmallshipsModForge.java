package com.talhanation.smallships.forge;

import com.talhanation.smallships.SmallShipsMod;
import com.talhanation.smallships.forge.client.ClientInitializer;
import com.talhanation.smallships.network.ModPackets;
import com.talhanation.smallships.forge.world.entity.ModEntityTypesImpl;
import com.talhanation.smallships.forge.world.inventory.ModMenuTypesImpl;
import com.talhanation.smallships.forge.world.item.ModItemsImpl;
import com.talhanation.smallships.forge.world.sound.ModSoundTypesImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SmallShipsMod.MOD_ID)
public class SmallshipsModForge {
    public SmallshipsModForge() {
        SmallShipsMod.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);

        ModItemsImpl.ITEMS.register(modEventBus);
        ModEntityTypesImpl.ENTITY_TYPES.register(modEventBus);
        ModMenuTypesImpl.MENU_TYPES.register(modEventBus);
        ModSoundTypesImpl.SOUND_EVENTS.register(modEventBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientInitializer::new);
    }

    private void setup(@SuppressWarnings("unused") FMLCommonSetupEvent event) {
        ModPackets.registerPackets();
    }
}
