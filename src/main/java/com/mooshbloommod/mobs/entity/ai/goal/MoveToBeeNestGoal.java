package com.mooshbloommod.mobs.entity.ai.goal;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import com.mooshbloommod.mobs.entity.passive.MooshbloomEntity;

public class MoveToBeeNestGoal extends MoveToBlockGoal {
    private final MooshbloomEntity moobloom;

    public MoveToBeeNestGoal(MooshbloomEntity entity, double speedIn) {
        super(entity, speedIn, 32, 3);
        this.moobloom = entity;
        this.field_203112_e = -1;
    }

    public boolean shouldExecute() {
        return super.shouldExecute();
    }

    public boolean shouldContinueExecuting() {
        return this.timeoutCounter <= 600 && this.shouldMoveTo(this.moobloom.world, this.destinationBlock);
    }

    public boolean shouldMove() {
        return this.timeoutCounter % 40 == 0;
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).isIn(Blocks.BEE_NEST);
    }
}