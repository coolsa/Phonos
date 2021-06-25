package io.github.foundationgames.phonos.sound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.AbstractSoundInstance;
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
	double minDist = 64;
	int total = 0;
	for (long l : positions) {
	    mpos.set(l);
	    // start by getting the squared distance between the player and the block.
	    double squareDistance = mpos.getSquaredDistance(pos.x, pos.y, pos.z, false);
	    if (squareDistance > 64 * 64)
		continue;
	    squareDistance = Math.sqrt(squareDistance);
	    if (squareDistance < minDist)
		minDist = squareDistance;
	    double weight = 1.0 / (Math.pow(squareDistance / (Math.pow(squareDistance, (16.0 / squareDistance))),
		    (16.0 / squareDistance)));
	    if (Double.isInfinite(weight)) {
		dirX = mpos.getX();
		dirY = mpos.getY();
		dirZ = mpos.getZ();
		this.volume = this.trueVol;
		return;
	    }
	    dirX = dirX * weightTotal + mpos.getX() * weight;
	    dirY = dirY * weightTotal + mpos.getY() * weight;
	    dirZ = dirZ * weightTotal + mpos.getZ() * weight;
	    weightTotal += weight;
	    dirX /= weightTotal;
	    dirY /= weightTotal;
	    dirZ /= weightTotal;
	    total++;
	}
	if (Double.isInfinite(weightTotal)) {
	    weightTotal = this.trueVol / minDist;
	} else {
	    weightTotal /= total;
	    weightTotal = Math.pow(weightTotal, 1 / 16.0) * 16.0 / (minDist);
	    weightTotal = Math.min(weightTotal, this.trueVol);
	    weightTotal = Math.max(weightTotal, 0);
	}
	this.volume = (float) (this.trueVol * weightTotal);
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
