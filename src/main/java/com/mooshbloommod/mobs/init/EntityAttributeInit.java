package com.mooshbloommod.mobs.init;

import com.mooshbloommod.mobs.entity.base.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;

public class EntityAttributeInit {

    public static void init() {

        registerAttributes(EntityTypesInit.MOOBLOOM_REGISTRY_OBJECT.get(), MooshbloomModBaseCowEntity.registerAttributes());

    }
    
    private static void registerAttributes(EntityType<? extends LivingEntity> entity, AttributeModifierMap.MutableAttribute attributes){
        GlobalEntityTypeAttributes.put(entity, attributes.func_233813_a_());
    }
}
