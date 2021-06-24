package io.github.foundationgames.phonos.mixin;

import io.github.foundationgames.phonos.block.OxidizableCopperSpeakerBlock;
import io.github.foundationgames.phonos.item.NoteBlockTunerItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Oxidizable.class)
public class OxidizableMixin {
    @Inject(method = "getDecreasedOxidiationState", at = @At("HEAD"), cancellable = true)
    public void getDecreasedOxidationState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
    	if(state.getBlock() instanceof OxidizableCopperSpeakerBlock) {
    		cir.setReturnValue(((OxidizableCopperSpeakerBlock) state.getBlock()).getPrevState(state));
    	}
    }
}
