package net.the_last_sword.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

//龙魂灯笼方块
public class DragonSoulLanternBlock extends LanternBlock {
    private static final VoxelShape HANGING_SHAPE = Block.box(4.5, 8, 4.5, 11.5, 16, 11.5);

    public DragonSoulLanternBlock() {
        super(net.the_last_sword.mv.RegIds.stamp(BlockBehaviour.Properties.of())
                .sound(SoundType.LANTERN)
                .strength(1f, 100f)
                .lightLevel(s -> 15)
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                //26.1: hasPostProcess(predicate) is postProcess(state, level, pos -> the position to post-process)
                .postProcess((bs, br, bp) -> bp)
                .emissiveRendering((bs, br, bp) -> true)
                .isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : super.getShape(state, world, pos, context);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    //26.1: getLightBlock(BlockState) is getLightDampening(BlockState)
    @Override
    public int getLightDampening(BlockState state) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}
