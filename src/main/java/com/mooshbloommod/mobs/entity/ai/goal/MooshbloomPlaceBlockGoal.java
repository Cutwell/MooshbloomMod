package com.mooshbloommod.mobs.entity.ai.goal;

import com.mooshbloommod.mobs.entity.passive.MooshbloomEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.util.BlockSnapshot;

public class MooshbloomPlaceBlockGoal extends Goal {
    private final MooshbloomEntity moobloom;

    public MooshbloomPlaceBlockGoal(MooshbloomEntity p_i45843_1_) {
        this.moobloom = p_i45843_1_;
    }

    public boolean shouldExecute() {
        return this.moobloom.getRNG().nextInt(2000) == 0;
    }

    public boolean canPlace(IWorldReader world, BlockState target, BlockPos targetPos, BlockState downTarget, BlockPos downTargetPos) {
        return !downTarget.isAir(world, downTargetPos) && downTarget.isOpaqueCube(world, downTargetPos) && target.isValidPosition(world, targetPos);
    }

    public void tick() {
        IWorld iworld = this.moobloom.world;
        int i = MathHelper.floor(this.moobloom.getPosX());
        int j = MathHelper.floor(this.moobloom.getPosY());
        int k = MathHelper.floor(this.moobloom.getPosZ());
        Block flower = this.moobloom.getMoobloomType().getRenderState().getBlock();
        BlockPos blockPos = new BlockPos(i, j, k);
        BlockState blockState = flower.getDefaultState();
        BlockPos blockDownPos = blockPos.down();
        BlockState blockDownState = iworld.getBlockState(blockDownPos);
        if (canPlace(iworld, blockState, blockPos, blockDownState, blockDownPos) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(moobloom, BlockSnapshot.create(iworld, blockDownPos), net.minecraft.util.Direction.UP)) {
            iworld.destroyBlock(blockPos, false);
            iworld.setBlockState(blockPos, blockState, 3);
        }
    }
}
