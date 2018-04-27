package com.land.java.quadSector.interfaces;

import com.land.java.quadSector.Vector;

public interface Mass {

    Vector getPosition();

    Vector getVelocity();

    Mass add(Mass m, boolean varyVelocity);

    Mass reduce(Mass m, boolean varyVelocity);

    double getMass();
}
