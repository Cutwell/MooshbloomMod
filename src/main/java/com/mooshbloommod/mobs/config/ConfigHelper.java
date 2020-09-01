package com.mooshbloommod.mobs.config;

/**
 * This bakes the config values to normal fields
 *
 * @author Cadiboo
 * It can be merged into the main ConfigMod class, but is separate because of personal preference and to keep the code organised
 */
public final class ConfigHelper {

    public static void bakeClient(final net.minecraftforge.fml.config.ModConfig config) {
        MooshbloomModConfig.showDescription = ConfigHolder.CLIENT.showDescription.get();
    }

    public static void bakeCommon(final net.minecraftforge.fml.config.ModConfig config) {

        MooshbloomModConfig.moobloomSpawnBiomes = ConfigHolder.COMMON.moobloomSpawnBiomes.get();
        MooshbloomModConfig.moobloomWeight = ConfigHolder.COMMON.moobloomWeight.get();
        MooshbloomModConfig.moobloomGroupMin = ConfigHolder.COMMON.moobloomGroupMin.get();
        MooshbloomModConfig.moobloomGroupMax = ConfigHolder.COMMON.moobloomGroupMax.get();

    }

    public static void bakeServer(final net.minecraftforge.fml.config.ModConfig config) {
    }

}