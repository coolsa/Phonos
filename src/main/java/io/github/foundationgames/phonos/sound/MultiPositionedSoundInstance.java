package io.github.foundationgames.phonos.sound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class MultiPositionedSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
    private final List<Long> positions;
    private double x;
    private double y;
    private double z;
    private float trueVol;
    private PlayerEntity player = MinecraftClient.getInstance().player;

    private boolean done;

    public MultiPositionedSoundInstance(List<Long> positions, SoundEvent sound, float volume, float pitch) {
        this(positions, sound.getId(), volume, pitch);
    }

    public MultiPositionedSoundInstance(List<Long> positions, Identifier sound, float volume, float pitch) {
        super(sound, SoundCategory.RECORDS);
        this.volume = volume;
        this.trueVol = volume;
        this.pitch = pitch;
        this.positions = positions;
        updatePosition();
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    private void updatePosition() {
        BlockPos.Mutable mpos = new BlockPos.Mutable();
		Vec3d pos = new Vec3d(this.player.getX(), this.player.getEyeY(), this.player.getZ());
		// start keeping track of average, weighted, positional vectors.
		double dirX = 0;
		double dirY = 0;
		double dirZ = 0;
		double weightTotal = 0;
//		int spot = 0;
//		boolean close = false;
        for(long l : positions) {
            mpos.set(l);
			// start by getting the squared distance between the player and the block.
			double squareDistance = mpos.getSquaredDistance(pos.x-0.5, pos.y, pos.z-0.5, false);
			double weight = 2.0 / (squareDistance);
			if(squareDistance > 64*64*64) continue;
//			if(close && squareDistance >= 64) continue;
			if(squareDistance <= 64) {
//				System.out.println(mpos);
				weight *= 64.0/squareDistance;
//				if(!close)
//					weightTotal *= Math.pow(squareDistance/8.0,spot);
//				close = true;
			}
//			else if(close) weight *= 8.0/squareDistance;
			dirX = dirX * weightTotal + mpos.getX() * weight;
			dirY = dirY * weightTotal + mpos.getY() * weight;
			dirZ = dirZ * weightTotal + mpos.getZ() * weight;
			weightTotal += weight;
			dirX /= weightTotal;
			dirY /= weightTotal;
			dirZ /= weightTotal;
//			spot++;
        }
		weightTotal = Math.min(Math.pow(weightTotal, 1.0 / this.trueVol) * this.trueVol, this.trueVol);
		this.volume = (float) (this.trueVol * weightTotal);
//		System.out.println(weightTotal + "\tweight");
//		System.out.println("\n" + dirX + "\tdirX\n" + dirY + "\n" + dirZ);
		this.x = dirX;
		this.y = dirY;
		this.z = dirZ;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    protected final void setDone() {
        this.done = true;
        this.repeat = false;
    }

    @Override
    public void tick() {
        updatePosition();
    }
}
