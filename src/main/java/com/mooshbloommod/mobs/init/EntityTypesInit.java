package com.mooshbloommod.mobs.init;

import com.mooshbloommod.mobs.MooshbloomMod;

import com.mooshbloommod.mobs.entity.passive.*;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class EntityTypesInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MooshbloomMod.MOD_ID);


    public static final String MOOSHBLOOM_REGISTRY_NAME = "mooshbloom";
    public static final RegistryObject<EntityType<MooshbloomEntity>> MOOSHBLOOM_REGISTRY_OBJECT = ENTITY_TYPES.register(
            MOOSHBLOOM_REGISTRY_NAME,
            () -> EntityType.Builder.create(MooshbloomEntity::new, EntityClassification.CREATURE)
                    .size(EntityType.COW.getWidth(), EntityType.COW.getHeight())
                    .build(new ResourceLocation(MooshbloomMod.MOD_ID, MOOSHBLOOM_REGISTRY_NAME).toString())
    );

}


