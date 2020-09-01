
package com.mooshbloommod.mobs.entity.passive;

import com.mooshbloommod.mobs.entity.ai.goal.MooshbloomHurtByTargetGoal;
import com.mooshbloommod.mobs.entity.ai.goal.MoveToBeeNestGoal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.fml.network.NetworkHooks;
import com.mooshbloommod.mobs.entity.ai.goal.MooshbloomPlaceBlockGoal;
import com.mooshbloommod.mobs.entity.base.MooshbloomModBaseCowEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class MooshbloomEntity extends MooshbloomModBaseCowEntity<MooshbloomEntity> implements IAngerable, IForgeShearable {

    public MooshbloomEntity(EntityType<MooshbloomEntity> type, World world) {
        super(type, world);
    }

    private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(MooshbloomEntity.class, DataSerializers.VARINT);
    private static final RangedInteger field_234180_bw_ = TickRangeConverter.convertRange(20, 39);
    private UUID lastHurtBy;
    // store UUID of last lightening bolt to hit
    private UUID lightningUUID;

    private static final DataParameter<String> MOOBLOOM_TYPE = EntityDataManager.createKey(MooshbloomEntity.class, DataSerializers.STRING);

    public boolean setMoobloomAttacker(Entity attacker) {
        this.func_230260_a__(400 + this.rand.nextInt(400));
        if (attacker instanceof LivingEntity) {
            this.setRevengeTarget((LivingEntity) attacker);
        }
        return true;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // anger bees when attacked
        this.targetSelector.addGoal(1, (new MooshbloomHurtByTargetGoal(this)).setCallsForHelp(new Class[0]));
        // move towards bee nests
        this.goalSelector.addGoal(2, new MoveToBeeNestGoal(this, 1.0D));
        // place flowers
        this.goalSelector.addGoal(3, new MooshbloomPlaceBlockGoal(this));
    }

    public boolean isShearable(@javax.annotation.Nonnull ItemStack item, World world, BlockPos pos) {
        return this.isAlive() && !this.isChild();
    }

    @Nonnull
    public java.util.List<ItemStack> onSheared(@Nullable PlayerEntity player, @javax.annotation.Nonnull ItemStack item, World world, BlockPos pos, int fortune) {

        // replaces mooshbloom with cow
        // and drops flowers of mooshbloom type

        world.playMovingSound(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!this.world.isRemote) {
            ((ServerWorld) this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            CowEntity cowentity = EntityType.COW.create(this.world);
            cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            cowentity.setHealth(this.getHealth());
            cowentity.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }
            if (this.isNoDespawnRequired()) {
                cowentity.enablePersistence();
            }
            cowentity.setInvulnerable(this.isInvulnerable());
            this.world.addEntity(cowentity);
            java.util.List<ItemStack> ret = new java.util.ArrayList<>();
            for (int i = 0; i < 5; ++i) {
                ret.add(new ItemStack(this.getMoobloomType().renderState.getBlock()));
            }

            return ret;
         }
        return java.util.Collections.emptyList();
    }

    // milk with glass bottle to get honey bottle
    public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
        if (itemstack.getItem() == Items.GLASS_BOTTLE && !this.isChild()) {
            p_230254_1_.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.func_241445_a_(itemstack, p_230254_1_, Items.HONEY_BOTTLE.getDefaultInstance());
            p_230254_1_.setHeldItem(p_230254_2_, itemstack1);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.func_230254_b_(p_230254_1_, p_230254_2_);
        }
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        UUID uuid = lightningBolt.getUniqueID();
        if (!uuid.equals(this.lightningUUID)) {

            // invert type
            if (this.getMoobloomType() == Type.DANDELION) {
                this.setMoobloomType(Type.POPPY);
            }
            else {
                this.setMoobloomType(Type.DANDELION);
            }

            this.lightningUUID = uuid;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }

    }

    protected void registerData() {
        super.registerData();

        // random assignment
        int i = (int) (Math.random() * 2);
        Type type;

        if (i == 0) {
            type = Type.POPPY;
        }
        else {
            type = Type.DANDELION;
        }

        this.dataManager.register(MOOBLOOM_TYPE, type.name);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("Type", this.getMoobloomType().name);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setMoobloomType(MooshbloomEntity.Type.getTypeByName(compound.getString("Type")));
    }


    private void setMoobloomType(MooshbloomEntity.Type typeIn) {
        this.dataManager.set(MOOBLOOM_TYPE, typeIn.name);
    }

    public MooshbloomEntity.Type getMoobloomType() {
        return MooshbloomEntity.Type.getTypeByName(this.dataManager.get(MOOBLOOM_TYPE));
    }

    @Override
    public MooshbloomEntity createChild(AgeableEntity ageable) {
        MooshbloomEntity moobloomentity = (MooshbloomEntity) getType().create(this.world);
        moobloomentity.setMoobloomType(this.func_213445_a((MooshbloomEntity)ageable));
        return moobloomentity;
    }


    // find closest dandelion and poppy flowers
    //
    private BlockPos getClosestFlower(MooshbloomEntity parent, Block target) {
        // check all blocks in area
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(MathHelper.floor(parent.getPosX() - 16.0D), MathHelper.floor(parent.getPosY() - 16.0D), MathHelper.floor(parent.getPosZ() - 16.0D), MathHelper.floor(parent.getPosX() + 16.0D), MathHelper.floor(parent.getPosY()), MathHelper.floor(parent.getPosZ() + 16.0D))) {
            // if block is target
            if (parent.world.getBlockState(blockpos).getBlock().getClass().equals(target.getClass())) {
                return blockpos;
            }
        }
        return null;
    }

    // decide flower type
    private MooshbloomEntity.Type func_213445_a(MooshbloomEntity p_213445_1_) {
        BlockPos blockpos_poppy = null;
        BlockPos blockpos_dandelion = null;

        blockpos_poppy = getClosestFlower(p_213445_1_, Blocks.POPPY);
        blockpos_dandelion = getClosestFlower(p_213445_1_, Blocks.DANDELION);

        if (blockpos_poppy != null && blockpos_dandelion != null) {
            // closest flower if both flowers present
            BlockPos current_pos = p_213445_1_.func_233580_cy_();

            if (blockpos_dandelion.distanceSq(current_pos) > blockpos_poppy.distanceSq(current_pos)) {
                return Type.POPPY;
            }
            else {
                return Type.DANDELION;
            }
        }

        else if (blockpos_dandelion == null) {
            // no competition
            return Type.POPPY;
        }

        else if (blockpos_poppy == null) {
            // no competition
            return Type.DANDELION;
        }

        else {
            // else, parent type if no nearby flowers
            return this.getMoobloomType();
        }
    }

    public static enum Type {
        POPPY("poppy", Blocks.POPPY.getDefaultState()),
        DANDELION("dandelion", Blocks.DANDELION.getDefaultState());

        private final String name;
        private final BlockState renderState;

        private Type(String nameIn, BlockState renderStateIn) {
            this.name = nameIn;
            this.renderState = renderStateIn;
        }

        /**
         * A block state that is rendered on the back of the mooshroom.
         */
        @OnlyIn(Dist.CLIENT)
        public BlockState getRenderState() {
            return this.renderState;
        }

        private static MooshbloomEntity.Type getTypeByName(String nameIn) {
            for(MooshbloomEntity.Type moobloomentity$type : values()) {
                if (moobloomentity$type.name.equals(nameIn)) {
                    return moobloomentity$type;
                }
            }

            return POPPY;
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public int func_230256_F__() {
        return this.dataManager.get(ANGER_TIME);
    }

    @Override
    public void func_230260_a__(int p_230260_1_) {
        this.dataManager.set(ANGER_TIME, p_230260_1_);
    }

    @Nullable
    @Override
    public UUID func_230257_G__() {
        return null;
    }

    @Override
    public void func_230259_a_(@Nullable UUID uuid) {
        this.lastHurtBy = uuid;
    }

    @Override
    public void func_230258_H__() {
        this.func_230260_a__(field_234180_bw_.func_233018_a_(this.rand));
    }
}