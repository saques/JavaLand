package com.land.java.quadSector;

import com.land.java.quadSector.interfaces.Mass;
import com.land.java.quadSector.interfaces.OuterSector;
import com.land.java.quadSector.interfaces.Sector;

import java.util.*;

public final class MassContainer extends GenericSector implements OuterSector{

    private Sector sector;

    @Override
    public Iterator<Mass> iterator() {
        return sector.iterator();
    }

    @Override
    public Iterator<Mass> approximatingIterator(Vector from, double distance, double permissible) {
        return sector.approximatingIterator(from, distance, permissible).orElseThrow(NoSuchElementException::new).getIterator();
    }

    @Override
    public boolean contains(Vector p) {
        return sector.contains(p);
    }

    public MassContainer(int loadFactor){
        if(loadFactor<2)
            throw new IllegalArgumentException();
        this.loadFactor = loadFactor;
        sector = new PlainSector(loadFactor);
    }

    @Override
    public OuterSector add(Mass t) {
        this.sector = sector.add(t);
        addMass(t);
        return this;
    }

    @Override
    public Optional<Mass> get(Vector p) {
        return sector.get(p);
    }

    @Override
    public int size() {
        return sector.size();
    }

    @Override
    public Optional<Mass> delete(Vector t) {
        Optional<Mass> ans = sector.delete(t);
        ans.ifPresent(y->reduceMass(y));
        if(size() == 0)
            sector = new PlainSector(loadFactor);
        return ans;
    }

}

