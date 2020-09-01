package com.mooshbloommod.mobs.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.mooshbloommod.mobs.entity.base.MooshbloomModBaseCowEntity;

import java.text.MessageFormat;

@OnlyIn(Dist.CLIENT)
public class MooshbloomModCowRenderer extends MobRenderer<MooshbloomModBaseCowEntity<? extends CowEntity>, CowModel<MooshbloomModBaseCowEntity<? extends CowEntity>>> {

    private final String registryName;

    public MooshbloomModCowRenderer(EntityRendererManager renderManagerIn, String registryName) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
        this.registryName = registryName;
    }

    @Override
    public ResourceLocation getEntityTexture(MooshbloomModBaseCowEntity<? extends CowEntity> entity) {
        String resourceTexture = MessageFormat.format("mooshbloommod:textures/mobs/cow/{0}/{0}.png", this.registryName);
        String resourceTextureBlink = MessageFormat.format("mooshbloommod:textures/mobs/cow/{0}/{0}_blink.png", this.registryName);
        ResourceLocation texture = new ResourceLocation(resourceTexture);
        ResourceLocation textureBlink = new ResourceLocation(resourceTextureBlink);
        return entity.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
    }
}
