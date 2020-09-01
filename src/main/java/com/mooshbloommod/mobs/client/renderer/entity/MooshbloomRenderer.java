package com.mooshbloommod.mobs.client.renderer.entity;

import com.google.common.collect.Maps;
import com.mooshbloommod.mobs.entity.passive.MooshbloomEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.mooshbloommod.mobs.client.renderer.entity.layers.MooshbloomLayer;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class MooshbloomRenderer extends MobRenderer<MooshbloomEntity, CowModel<MooshbloomEntity>> {

    public MooshbloomRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
        this.addLayer(new MooshbloomLayer<>(this));
    }

    private static final Map<MooshbloomEntity.Type, String> field_217774_a = Util.make(Maps.newHashMap(), (p_217773_0_) -> {
        p_217773_0_.put(MooshbloomEntity.Type.DANDELION, "dandelion");
        p_217773_0_.put(MooshbloomEntity.Type.POPPY, "poppy");
    });

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(MooshbloomEntity entity) {

        String type = field_217774_a.get(entity.getMoobloomType());

        ResourceLocation texture = new ResourceLocation("mooshbloommod:textures/mobs/cow/mooshbloom/mooshbloom_" + type + ".png");
        ResourceLocation textureBlink = new ResourceLocation("mooshbloommod:textures/mobs/cow/mooshbloom/mooshbloom_" + type + "_blink.png");

        return entity.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
    }
}