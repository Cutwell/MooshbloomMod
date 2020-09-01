package com.mooshbloommod.mobs.entity;

import com.mooshbloommod.mobs.config.MooshbloomModConfig;
import com.mooshbloommod.mobs.init.EntityTypesInit;
import com.mooshbloommod.mobs.utils.BiomeSpawnHelper;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.gen.Heightmap;

public class EntitySpawn {


    public static void init() {

        registerAnimalEntitySpawn(EntityTypesInit.MOOBLOOM_REGISTRY_OBJECT.get(), MooshbloomModConfig.moobloomSpawnBiomes.toArray(new String[0]), MooshbloomModConfig.moobloomWeight, MooshbloomModConfig.moobloomGroupMin, MooshbloomModConfig.moobloomGroupMax);

    }

    private static <T extends AnimalEntity> void registerAnimalEntitySpawn(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        BiomeSpawnHelper.setCreatureSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn);
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
    }


}
