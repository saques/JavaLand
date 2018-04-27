package com.land.java.quadSector;

import com.land.java.quadSector.interfaces.Mass;

public abstract class GenericSector{

    protected static final float REDUCTION_THRESHOLD = .5f;

    protected Mass m = new MassImpl();
    protected int loadFactor;

    protected void addMass(Mass m){
        this.m.add(m, false);
    }

    protected void reduceMass(Mass m){
        this.m.reduce(m, false);
    }

}
