package com.land.java.quadSector;

import com.land.java.quadSector.interfaces.Mass;

import java.util.Collections;
import java.util.Iterator;

public class MassIteratorContainer {

    private Mass representativeMass;

    private Iterator<Mass> iterator;

    private boolean empty;

    /**
     * Identity constructor
     */
    public MassIteratorContainer(){
        this.representativeMass = new MassImpl();
        this.iterator = Collections.emptyIterator();
        this.empty = true;
    }



    public boolean isEmpty() {
        return empty;
    }

    public void setNotEmpty(){
        empty = false;
    }

    public MassIteratorContainer(Mass r){
        this.representativeMass = r;
        this.iterator = Collections.emptyIterator();
        this.empty = true;
    }

    public Mass getMass() {
        return representativeMass;
    }

    public void setRepresentativeMass(Mass representativeMass) {
        this.representativeMass = representativeMass;
    }

    public Iterator<Mass> getIterator() {
        return iterator;
    }

    public void setIterator(Iterator<Mass> iterator) {
        this.iterator = iterator;
    }

    public MassIteratorContainer setIteratorAndReturn(Iterator<Mass> iterator){
        this.iterator = iterator;
        return this;
    }
}
