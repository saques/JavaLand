package com.land.java.quadSector;

import com.google.common.collect.Iterators;
import com.land.java.quadSector.interfaces.Mass;
import com.land.java.quadSector.interfaces.Sector;

import java.util.*;

class PlainSector extends GenericSector implements Sector {

    private Map<Vector, Mass> tdMap;

    @Override
    public Optional<Mass> get(Vector p) {
        return Optional.ofNullable(tdMap.get(p));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<Mass> iterator() {
        return tdMap.values().iterator();
    }

    @Override
    public Optional<MassIteratorContainer> approximatingIterator(Vector from, double distance, double permissible) {
        if(tdMap.isEmpty())
            return Optional.empty();
        MassIteratorContainer ans = new MassIteratorContainer(m);
        return m.getPosition().distance(from) > distance ?
                Optional.of(ans.setIteratorAndReturn(Iterators.singletonIterator(m))) :
                Optional.of(ans.setIteratorAndReturn(iterator()));
    }

    @Override
    public boolean contains(Vector p) {
        return tdMap.containsKey(p);
    }

    @Override
    public int size() {
        return tdMap.size();
    }

    @Override
    public int buckets() {
        return 1;
    }

    public PlainSector(int loadFactor){
        this.loadFactor = loadFactor;
        this.tdMap = new HashMap<>();
    }

    @Override
    public Optional<Mass> delete(Vector p) {
        Optional<Mass> ans = Optional.ofNullable(tdMap.remove(p));
        ans.ifPresent(this::reduceMass);
        return ans;
    }

    @Override
    public Sector add(Mass t) {
        Vector p = t.getPosition();
        Mass m = tdMap.get(p);
        if(m == null) {
            tdMap.put(p, t);
        } else {
            m.add(t, true);
        }
        addMass(t);
        if(tdMap.size() >= loadFactor){
            QuadSector  ans = new QuadSector(this.m.getPosition().getX(), this.m.getPosition().getY(),
                                            (int)Math.floor(loadFactor));
            ans.bulkAdd(tdMap.values(), this.m);
            return ans;
        }
        return this;
    }


}
