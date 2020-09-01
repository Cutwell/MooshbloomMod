package com.mooshbloommod.mobs.entity.ai.goal;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import com.mooshbloommod.mobs.entity.passive.MooshbloomEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.entity.passive.BeeEntity;

import java.util.Iterator;
import java.util.List;

public class MooshbloomHurtByTargetGoal extends HurtByTargetGoal {

    private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(BeeEntity.class, DataSerializers.VARINT);
    private final static EntityPredicate SEE_THROUGH_WALLS = (new EntityPredicate()).setLineOfSiteRequired();

    public MooshbloomHurtByTargetGoal(MooshbloomEntity cowIn) {
        super(cowIn);
    }

    @Override
    protected void alertOthers() {
        //double d0 = this.getTargetDistance();
        //AxisAlignedBB axisalignedbb = AxisAlignedBB.func_241549_a_(this.goalOwner.getPositionVec()).grow(d0, 10.0D, d0);
        //List<MobEntity> list = this.goalOwner.world.getLoadedEntitiesWithinAABB(BeeEntity.class, axisalignedbb);

        List<MobEntity> list = this.goalOwner.world.getTargettableEntitiesWithinAABB(BeeEntity.class, SEE_THROUGH_WALLS, this.goalOwner, this.goalOwner.getBoundingBox().grow(16.0D));

        if (list.isEmpty()) {
            return;
        }

        Iterator iterator = list.iterator();

        while(true) {
            MobEntity mobentity;
            while(true) {
                if (!iterator.hasNext()) {
                    return;
                }

                mobentity = (MobEntity)iterator.next();

                if (this.goalOwner != mobentity && mobentity.getAttackTarget() == null && !mobentity.isOnSameTeam(this.goalOwner.getRevengeTarget())) {
                    this.setAttackTarget(mobentity, this.goalOwner.getRevengeTarget());
                }
            }
        }
    }

    @Override
    protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
        if (mobIn != null && mobIn instanceof BeeEntity && this.goalOwner.canEntityBeSeen(targetIn)) {
            //mobIn.getDataManager().set(ANGER_TIME, 600);
            mobIn.setAttackTarget(targetIn);
        }
    }
}