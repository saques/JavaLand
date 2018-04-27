package com.land.java.quadSector;

public class Vector {

    private double x,y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector(Vector o){
        this.x = o.x;
        this.y = o.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distance(Vector p){
        return Math.sqrt(Math.pow(x-p.x, 2) + Math.pow(y-p.y, 2));
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (Double.compare(vector.x, x) != 0) return false;
        return Double.compare(vector.y, y) == 0;
    }

    public Vector dot(double d){
        x*=d;
        y*=d;
        return this;
    }

    public Vector dot(Vector d){
        x*=d.x;
        y*=d.y;
        return this;
    }

    public Vector sum(Vector d){
        x+=d.x;
        y+=d.y;
        return this;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}
