package io.github.foundationgames.phonos.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class OxidizableCopperSpeakerBlock extends LoudspeakerBlock implements Oxidizable {
    public static final BiMap<Block, Block> OXIDATION_INCREASE = ImmutableBiMap.<Block, Block>builder()
	    .put(PhonosBlocks.COPPER_SPEAKER, PhonosBlocks.EXPOSED_COPPER_SPEAKER)
//	    .put(PhonosBlocks.EXPOSED_COPPER_SPEAKER, PhonosBlocks.WEATHERED_COPPER_SPEAKER)
//	    .put(PhonosBlocks.WEATHERED_COPPER_SPEAKER, PhonosBlocks.EXPOSED_COPPER_SPEAKER)
	    .build();
//    private final Block waxedBlock;
//    private final Block unwaxedBlock;
    public static final BiMap<Block, Block> OXIDIATION_DECREASE = OXIDATION_INCREASE.inverse();
    private final OxidizationLevel stage;

    public OxidizableCopperSpeakerBlock(Settings settings, OxidizationLevel stage) {
	super(settings);
	this.stage = stage;
    }

    public Optional<BlockState> getPrevState(BlockState state) {
	return Optional.ofNullable(OXIDIATION_DECREASE.get(state.getBlock()).getDefaultState());
    }

    @Override
    public Optional<BlockState> getDegradationResult(BlockState state) {
	return Optional.ofNullable(OXIDATION_INCREASE.get(state.getBlock()).getDefaultState());
    }

//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//    	if(hit.getSide() != Direction.UP) {
//	        if(player.getStackInHand(hand).getItem() instanceof AxeItem) {
//	            world.setBlockState(pos, prevOxidizedState);
//	            return ActionResult.success(world.isClient());
//	        } else if(player.getStackInHand(hand).getItem() instanceof HoneycombItem) {
//	            world.
//	            return ActionResult.success(world.isClient());
//	        }
//        }
//        return super.onUse(state, world, pos, player, hand, hit);
//    }

    @Override
    public OxidizationLevel getDegradationLevel() {
	return this.stage;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
	this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
	return true;
    }

}
