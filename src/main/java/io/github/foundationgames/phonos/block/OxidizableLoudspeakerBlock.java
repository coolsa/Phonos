package io.github.foundationgames.phonos.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

// Yes, i did this with incomplete Yarn

public class OxidizableLoudspeakerBlock extends LoudspeakerBlock implements Oxidizable {
    private final BlockState nextOxidizedState;
    private final OxidizationLevel stage;

    public OxidizableLoudspeakerBlock(Settings settings, OxidizationLevel stage, BlockState nextState) {
        super(settings);
        this.nextOxidizedState = nextState != null ? nextState : this.getDefaultState();
        this.stage = stage;
    }

    @Override
    public Optional<BlockState> getDegradationResult(BlockState state) {
        return Optional.ofNullable(nextOxidizedState);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

	@Override
	public OxidizationLevel getDegradationLevel() {
		// TODO Auto-generated method stub
		return this.stage;
	}
}
