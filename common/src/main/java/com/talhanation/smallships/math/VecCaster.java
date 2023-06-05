package com.talhanation.smallships.math;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

public class VecCaster {
    public static Vec3i Vec3toVec3i(Vec3 v){
        return new Vec3i((int)v.x, (int)v.y, (int)v.z);
    }
}
