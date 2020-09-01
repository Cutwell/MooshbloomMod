package com.mooshbloommod.mobs;

import com.mooshbloommod.mobs.init.*;
import net.minecraft.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mooshbloommod.mobs.config.ConfigHolder;
import com.mooshbloommod.mobs.entity.EntitySpawn;

/* TODO
 * Opzione attiva/disattiva extra tab
 * Opzione delay spawn trader
 */

@Mod(MooshbloomMod.MOD_ID)
public class MooshbloomMod {

    public static final String MOD_ID = "mooshbloommod";
    private static final Logger LOGGER = LogManager.getLogger("Mooshbloom");

    public MooshbloomMod() {
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        EntityTypesInit.ENTITY_TYPES.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);

        modEventBus.register(this);
        modEventBus.addListener(this::setup);

        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC);
        LOGGER.info("Mod loaded! Enjoy :D");
    }


    private void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {

            EntitySpawn.init();

        });
    }


}
