package com.talhanation.smallships.world.entity.projectile;

import com.mojang.datafixers.util.Pair;
import com.talhanation.smallships.world.entity.ship.Ship;
import com.talhanation.smallships.world.entity.ship.abilities.Cannonable;
import com.talhanation.smallships.world.sound.ModSoundTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.function.BiConsumer;

public class Cannon extends Entity {
    private final RandomSource random;
    private final double offsetX;
    private final double offsetY;
    private final double offsetZ;
    private int time;
    private int coolDown;
    private final Ship ship;
    private final Level level;
    private double angle;
    private boolean isRightSided;
    private boolean isLeftSided;

    public Cannon(Ship ship, Cannonable.CannonPosition cannonPosition) {
        this(ship, cannonPosition.x, cannonPosition.y, cannonPosition.z, cannonPosition.isRightSided, !cannonPosition.isRightSided);
    }

    public Cannon(Ship ship, double offsetX, double offsetY, double offsetZ, boolean isRightSided, boolean isLeftSided) {
        super(EntityType.ARMOR_STAND, ship.getLevel());
        this.ship = ship;
        this.level = ship.getLevel();
        this.random = level.getRandom();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.resetTimer();
        this.coolDown = 0;

        if (isRightSided) this.setRightSided();
        if (isLeftSided) this.setLeftSided();
    }


    public void tick(){
        if (coolDown > 0) coolDown--;
        this.updatePosition();
    }

    @Override
    protected void defineSynchedData() {
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }

    @SuppressWarnings("NullableProblems")
    //@Override
    /*public Packet<?> getAddEntityPacket() {
        return null;
    }*/
    //Method no longer abstract, defaults to ClientboundAddEntityPacket

    public void trigger() {
        if (coolDown == 0) {
            if (time > 0) time--;

            if (time == 0) {
                this.shoot();
                this.resetTimer();
                this.setCoolDown();
            }
        }
    }

    public void updatePosition(){
        Vec3 forward = this.ship.getForward();
        float x0 = 0; // /-/rechst /+/links //no need

        double f0 = (Math.cos(this.ship.getYRot() * ((float)Math.PI / 180F)) * x0);
        double f1 = (Math.sin(this.ship.getYRot() * ((float)Math.PI / 180F)) * x0);
        double f2 = this.getOffsetX(); // /-/vorne /+/zurück
        double d1 = this.ship.getX() - forward.x * f2 + f0;
        double d2 = this.ship.getY() - forward.y + this.getOffsetY();//hoch
        double d3 = this.ship.getZ() - forward.z * f2 + f1;

        this.moveTo(d1, d2, d3);
    }

    private void resetTimer() {
        this.time = 10 + random.nextInt(10);
    }

    private void setCoolDown() {
        this.coolDown = 50;
    }

    private void shoot() {
        LivingEntity driverEntity = (LivingEntity) ship.getControllingPassenger();
        if (driverEntity == null) return;

        Vec3 forward = ship.getForward().normalize();
        Vec3 shootVec = getShootVector(forward, driverEntity);

        double speed = 2.2F;
        double k = 3F;

        if (shootVec != null) {
            boolean playerView = driverEntity.getLookAngle().y >= 0;
            double yShootVec = playerView ? shootVec.y() + driverEntity.getLookAngle().y * 0.95F : shootVec.y() + 0.15F;

            CannonBallEntity cannonBallEntity = new CannonBallEntity(this.level, (LivingEntity) ship.getControllingPassenger(), this.getX(), this.getY() + 1, this.getZ());
            cannonBallEntity.shoot(shootVec.x(), yShootVec, shootVec.z(), (float) speed, (float) k);
            this.level.addFreshEntity(cannonBallEntity);
            ship.playSound(SoundEvents.TNT_PRIMED, 1.0F, 1.0F / (0.4F + 1.2F) + 0.5F);

            this.playCannonShotSound();

            if (ship instanceof Cannonable cannonable) cannonable.consumeCannonBall();
        }
    }

    private Vec3 getShootVector(Vec3 forward, LivingEntity driver) {
        Vec3 VecRight = forward.yRot(-3.14F / 2).normalize();
        Vec3 VecLeft = forward.yRot(3.14F / 2).normalize();

        Vec3 playerVec = driver.getLookAngle().normalize();

        if (playerVec.distanceTo(VecLeft) > playerVec.distanceTo(VecRight)) {
            return VecRight;
        }

        if (playerVec.distanceTo(VecLeft) < playerVec.distanceTo(VecRight)) {
            return VecLeft;
        }
        return null;
    }


    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public float getAngle() {
        return (float) this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setLeftSided() {
        this.isLeftSided = true;
        this.setAngle(0F);
    }

    public void setRightSided() {
        this.isRightSided = true;
        this.setAngle(180F);
    }
    public boolean isRightSided() {
        return isRightSided;
    }

    public boolean canShootDirection() {
        LivingEntity driver = (LivingEntity) ship.getControllingPassenger();
        if (driver == null) return false;

        Vec3 forward = ship.getForward().normalize();
        Vec3 shootVec = getShootVector(forward, driver);
        Vec3 VecRight = forward.yRot(-3.14F / 2).normalize();
        Vec3 VecLeft = forward.yRot(3.14F / 2).normalize();

        if (isRightSided && Objects.equals(shootVec, VecRight)) {
            return true;
        } else {
            return isLeftSided && Objects.equals(shootVec, VecLeft);
        }
    }

    public CompoundTag getData(){
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.putDouble("x", this.getOffsetX());
        compoundtag.putDouble("y", this.getOffsetY());
        compoundtag.putDouble("z", this.getOffsetZ());
        compoundtag.putBoolean("isRightSided", this.isRightSided());
        return compoundtag;
    }

    private void playCannonShotSound() {
        BiConsumer<SoundEvent, Pair<Float, Float>> play = (sound, modifier) -> {
            if (!ship.getLevel().isClientSide()) ship.playSound(sound, modifier.getFirst(), modifier.getSecond());
            else ship.getLevel().playLocalSound(ship.getX(), ship.getY() + 4, ship.getZ(), sound, ship.getSoundSource(), modifier.getFirst(), modifier.getSecond(), false);
        };
        play.accept(ModSoundTypes.CANNON_SHOT, Pair.of(10.0F, 1.0F));
    }

}
