package com.land.java.quadSector;

import com.google.common.collect.Iterators;
import com.land.java.quadSector.interfaces.Mass;
import com.land.java.quadSector.interfaces.Sector;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

class QuadSector extends GenericSector implements Sector {

    private Sector nw, ne, sw, se;
    private double x, y;

    @Override
    public Iterator<Mass> iterator() {
        return Iterators.concat(nw.iterator(), ne.iterator(), sw.iterator(), se.iterator());
    }

    @Override
    public Optional<MassIteratorContainer> approximatingIterator(Vector f, double d, double p) {

        Optional<MassIteratorContainer> ans = Optional.of(Stream.of(nw.approximatingIterator(f, d, p), ne.approximatingIterator(f, d, p),
                                                                    sw.approximatingIterator(f, d, p), se.approximatingIterator(f, d, p))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(new MassIteratorContainer(this.m), (x,y)->{
            if(x.isEmpty() || x.getMass().getPosition().distance(y.getMass().getPosition()) >= p){
                x.setIterator(Iterators.concat(x.getIterator(), y.getIterator()));
                x.setNotEmpty();
            } else {
                x.setIterator(Iterators.concat(x.getIterator(), Iterators.singletonIterator(y.getMass())));
            }
            return x;
        }));

        return ans;

    }

    public QuadSector(double x, double y, int loadFactor){
        this.x = x;
        this.y = y;
        this.loadFactor = loadFactor;
        nw = new PlainSector(loadFactor);
        ne = new PlainSector(loadFactor);
        sw = new PlainSector(loadFactor);
        se = new PlainSector(loadFactor);
    }

    @Override
    public int size() {
        return nw.size() + ne.size() + sw.size() + se.size();
    }

    @Override
    public int buckets() {
        return nw.buckets() + ne.buckets() + sw.buckets() + se.buckets();
    }

    @Override
    public Optional<Mass> delete(Vector p) {
        Optional<Mass> ans;
        if(p.getX() < x && p.getY() < y){
            ans = sw.delete(p);
            sw = reduce(sw);
        } else if (p.getX() < x && p.getY() >= y) {
            ans = nw.delete(p);
            nw = reduce(nw);
        } else if (p.getX() >= x && p.getY() < y) {
            ans = se.delete(p);
            se = reduce(se);
        } else {
            ans = ne.delete(p);
            ne = reduce(ne);
        }
        ans.ifPresent(y-> reduceMass(y));
        return ans;
    }

    private Sector reduce(Sector s){
        if(s.size() < s.buckets()*loadFactor*REDUCTION_THRESHOLD){
            double d = Math.ceil(loadFactor);
            int newLoadFactor = d >= 2 ? (int)d: 2;
            PlainSector ans = new PlainSector(newLoadFactor);
            s.forEach(x->ans.add(x));
            return ans;
        }
        return s;
    }

    @Override
    public Optional<Mass> get(Vector p) {
        return searchAndApply(new MassImpl(p), ((sector, mass1) -> sector.get(mass1.getPosition())));
    }

    @Override
    public boolean contains(Vector p) {
        return searchAndApply(new MassImpl(p), (sector, mass1) -> sector.contains(mass1.getPosition()));
    }

    private <R> R searchAndApply(Mass m, BiFunction<Sector, Mass, R> biFunction){
        Vector p = m.getPosition();
        R ans;
        if(p.getX() < x && p.getY() < y){
            ans = biFunction.apply(sw, m);
        } else if (p.getX() < x && p.getY() >= y) {
            ans = biFunction.apply(nw, m);
        } else if (p.getX() >= x && p.getY() < y) {
            ans = biFunction.apply(se, m);
        } else {
            ans = biFunction.apply(ne, m);
        }
        return ans;
    }

    @Override
    public Sector add(Mass t) {
        addWithoutAddingMass(t);
        addMass(t);
        return this;
    }

    private void addWithoutAddingMass(Mass t){
        Vector p = t.getPosition();
        if(p.getX() < x && p.getY() < y){
            sw = sw.add(t);
        } else if (p.getX() < x && p.getY() >= y) {
            nw = nw.add(t);
        } else if (p.getX() >= x && p.getY() < y) {
            se = se.add(t);
        } else {
            ne = ne.add(t);
        }
    }

    protected void bulkAdd(Collection<Mass> c, Mass representative){
        this.m = representative;
        c.forEach(x-> addWithoutAddingMass(x) );
    }
}
