package io.github.foundationgames.phonos.block;

import java.util.Optional;
import java.util.Random;

import com.shnupbups.oxidizelib.OxidizeLib;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class OxidizableCopperSpeakerBlock extends LoudspeakerBlock implements Oxidizable {
    private final OxidizationLevel stage;

    public OxidizableCopperSpeakerBlock(Settings settings, OxidizationLevel state) {
	super(settings);
	this.stage = state;
    }
    
    @Override
    public Optional<BlockState> getDegradationResult(BlockState state) {
	return OxidizeLib.getIncreasedOxidizationState(state);
    }
    
    @Override
    public OxidizationLevel getDegradationLevel() {
	// TODO Auto-generated method stub
	return this.stage;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
	this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
	// only tick if we are not oxidized
	if (!this.stage.equals(OxidizationLevel.OXIDIZED))
	    return true;
	return false;
    }

}
