package com.mooshbloommod.mobs.init;

import com.mooshbloommod.mobs.MooshbloomMod;
import com.mooshbloommod.mobs.item.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MooshbloomMod.MOD_ID);

    private static final ItemGroup itemGroup = MooshbloomModItemGroup.instance;


    // SPAWN EGGS
    private static final Item.Properties spawnEggProps = new Item.Properties().group(itemGroup);

    public static final RegistryObject<ModdedSpawnEggItem> MOOSHBLOOM_SPAWN_EGG = registerSpawnEgg(EntityTypesInit.MOOSHBLOOM_REGISTRY_NAME, EntityTypesInit.MOOSHBLOOM_REGISTRY_OBJECT, 0xfaca00, 0xf7edc1);

    private static RegistryObject<ModdedSpawnEggItem> registerSpawnEgg(String entityRegistryName, RegistryObject<? extends EntityType<?>> entity, int primaryColor, int secondaryColor) {
        return ITEMS.register(entityRegistryName + "_spawn_egg", () -> new ModdedSpawnEggItem(entity, primaryColor, secondaryColor, spawnEggProps));
    }

}
