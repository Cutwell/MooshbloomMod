package com.mooshbloommod.mobs.utils;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class BiomeSpawnHelper {

    public static final String[] MOOSHBLOOM_SPAWN_BIOMES = getBiomesListFromBiomes(new String[]{"minecraft:flower_forest"});

    private BiomeSpawnHelper() {
    }

    public static String[] getBiomesList(String[]... identifiers) {
        List<String> biomes = new ArrayList<>();
        List<String> types = new ArrayList<>();
        String[] flatted = Stream.of(identifiers).flatMap(Stream::of).toArray(String[]::new);
        for (String identifier : flatted) {
            String[] splitted = identifier.split(":");
            if (splitted.length == 2) {
                biomes.add(identifier);
            }
            if (splitted.length == 1) {
                types.add(identifier);
            }
        }
        return Stream.concat(biomes.stream(), types.stream()).toArray(String[]::new);
    }

    public static String[] getBiomesListFromBiomes(String[]... biomes) {
        return Stream.of(biomes).flatMap(Stream::of).toArray(String[]::new);
    }

    public static String[] getBiomesListFromBiomeTypes(BiomeDictionary.Type... types) {
        return Stream.of(types).flatMap(Stream::of).map(BiomeDictionary.Type::getName).toArray(String[]::new);
    }

    private static boolean isOverworld(Biome biome) {
        Set<Biome> biomesNether = BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER);
        Set<Biome> biomesEnd = BiomeDictionary.getBiomes(BiomeDictionary.Type.END);
        for (Biome biomeNether : biomesNether) {
            if (biome == biomeNether) {
                return false;
            }
        }
        for (Biome biomeEnd : biomesEnd) {
            if (biome == biomeEnd) {
                return false;
            }
        }
        return true;
    }

    private static void setSpawnBiomes(EntityType<?> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn, EntityClassification classification) {
        for (String identifier : spawnBiomes) {
            String[] splitted = identifier.split(":");
            if (splitted.length == 2) {
                Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(identifier));
                if (biome != null) {
                    biome.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
                }
            }
            if (splitted.length == 1) {
                Set<Biome> biomes = BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(identifier.toUpperCase()));
                for (Biome biome : biomes) {
                    if (isOverworld(biome)) {
                        biome.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
                    }
                }
            }
        }
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            boolean biomeCriteria = Arrays.asList(spawnBiomes).contains(ForgeRegistries.BIOMES.getKey(biome).toString());
            if (!biomeCriteria)
                continue;
            biome.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
        }
    }

    public static <T extends AnimalEntity> void setCreatureSpawnBiomes(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        setSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn, EntityClassification.CREATURE);
    }

    public static <T extends WaterMobEntity> void setWaterCreatureSpawnBiomes(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        setSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn, EntityClassification.WATER_CREATURE);
    }

    public static <T extends MonsterEntity> void setMonsterSpawnBiomes(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        setSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn, EntityClassification.MONSTER);
    }

    public static <T extends MobEntity> void setMobSpawnBiomes(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        setSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn, EntityClassification.MISC);
    }

    public static List<String> convertForConfig(String[] ary) {
        return Arrays.stream(ary).collect(Collectors.toList());
    }

}
