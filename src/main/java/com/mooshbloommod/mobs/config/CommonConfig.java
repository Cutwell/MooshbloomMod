package com.mooshbloommod.mobs.config;

import com.mooshbloommod.mobs.utils.BiomeSpawnHelper;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

final class CommonConfig {

    final String CATEGORY_GENERAL = "general";
    final String CATEGORY_ENTITIES = "entities";
    final String CATEGORY_ORE_GENERATION = "ore_generation";

    final ForgeConfigSpec.ConfigValue<List<String>> moobloomSpawnBiomes;
    final ForgeConfigSpec.IntValue moobloomWeight;
    final ForgeConfigSpec.IntValue moobloomGroupMin;
    final ForgeConfigSpec.IntValue moobloomGroupMax;


    private final int standardCowWeight = 8;


    CommonConfig(final ForgeConfigSpec.Builder builder) {


        builder.push(CATEGORY_ENTITIES);


        builder.push("moobloom");
        moobloomSpawnBiomes = builder
                .comment("Biome where entity Spawn")
                .define("spawnBiomes", BiomeSpawnHelper.convertForConfig(BiomeSpawnHelper.MOOBLOOM_SPAWN_BIOMES));
        moobloomWeight = builder
                .comment("Weight of entity in spawn")
                .defineInRange("entityWeight", standardCowWeight, 0, Integer.MAX_VALUE);
        moobloomGroupMin = builder
                .comment("Minimum number of entities in group")
                .defineInRange("entityGroupMin", 2, 0, Integer.MAX_VALUE);
        moobloomGroupMax = builder
                .comment("Maximum number of entities in group")
                .defineInRange("entityGroupMax", 4, 0, Integer.MAX_VALUE);
        builder.pop();



        builder.pop();
    }

}