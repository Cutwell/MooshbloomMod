package com.mooshbloommod.mobs.item;

import com.mooshbloommod.mobs.MooshbloomMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MooshbloomModItemGroup extends ItemGroup {

    public static final MooshbloomModItemGroup instance = new MooshbloomModItemGroup(ItemGroup.GROUPS.length, MooshbloomMod.MOD_ID);

    private MooshbloomModItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.SUNFLOWER);
    }

}
