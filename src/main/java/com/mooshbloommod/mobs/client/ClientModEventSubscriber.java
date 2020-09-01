package com.mooshbloommod.mobs.client;

import com.mooshbloommod.mobs.MooshbloomMod;
import com.mooshbloommod.mobs.client.renderer.entity.*;
import com.mooshbloommod.mobs.init.EntityTypesInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@EventBusSubscriber(modid = MooshbloomMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber {

    private static final Logger LOGGER = LogManager.getLogger(MooshbloomMod.MOD_ID + " Client Mod Event Subscriber");

    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {

        registerEntityRenderer();

    }




    private static void registerEntityRenderer() {

        RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.MOOBLOOM_REGISTRY_OBJECT.get(), MooshbloomRenderer::new);


    }

}