package com.talhanation.smallships.world.client.forge;

import com.talhanation.smallships.SmallShipsMod;
import com.talhanation.smallships.world.damagesource.ModDamageSourceTypes;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SmallShipsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeBus {
    public ClientForgeBus() {
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::initRegisterInputEvents);
    }

    @SubscribeEvent
    public void initRegisterInputEvents(InputEvent.Key event) {
        Minecraft client = Minecraft.getInstance();
        com.talhanation.smallships.client.option.KeyEvent.onKeyInput(client);
    }

    //NEW 1.19.4 - hopefully works.
    @SubscribeEvent
    public void initDamageSources(LevelEvent.Load event){
        ModDamageSourceTypes.init(event.getLevel().registryAccess());
    }
}
