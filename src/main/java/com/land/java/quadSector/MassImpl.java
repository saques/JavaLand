package com.land.java.quadSector;

import com.land.java.quadSector.interfaces.Mass;

public class MassImpl implements Mass {

    private double mass;
    private Vector position;
    private Vector velocity;

    public MassImpl(double x, double y, double mass){
        this.position = new Vector(x, y);
        this.velocity = new Vector(0,0);
        this.mass = mass;
    }

    public MassImpl(){
        mass = 0;
        position = new Vector(0,0);
        velocity = new Vector(0,0);
    }

    public MassImpl(Vector position, Vector velocity, double mass){
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

    @Override
    public Mass add(Mass m, boolean varyVelocity) {
        vary(m,1, varyVelocity);
        return this;
    }

    @Override
    public Mass reduce(Mass m, boolean varyVelocity) {
        vary(m,-1, varyVelocity);
        return this;
    }

    private void vary(Mass m, int flogisto, boolean varyVelocity){
        varyCenterOfMass(m, flogisto);
        if(varyVelocity)
            varyVelocity(m, flogisto);
        mass += flogisto*m.getMass();
    }

    private void varyCenterOfMass(Mass m, int flogisto){
        position.dot(mass)
                .sum(new Vector(m.getPosition()).dot(flogisto*m.getMass()))
                .dot(1/(mass+flogisto*m.getMass()));
    }

    private void varyVelocity(Mass m, int flogisto){
        velocity.dot(mass)
                .sum(new Vector(m.getVelocity()).dot(flogisto*m.getMass()))
                .dot(1/(mass+flogisto*m.getMass()));
    }


    public MassImpl(Vector position){
        this.position = position;
        this.velocity = new Vector(0,0);
        this.mass = 0;
    }

    public Vector getPosition() {
        return position;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    public double getMass() {
        return mass;
    }

    @Override
    public String toString() {
        return position.toString() + " Mass: " + mass;
    }
}
