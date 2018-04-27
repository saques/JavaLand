package com.land.java.quadSector.interfaces;

import com.land.java.quadSector.Vector;

import java.util.Iterator;
import java.util.Optional;

public interface OuterSector extends Iterable<Mass> {

    OuterSector add(Mass m);

    int size();

    Optional<Mass> delete(Vector p);

    Optional<Mass> get(Vector p);

    /**
     * Distance indicates the distance from the passed Vector from which close particles will be tried to be treated as one.
     *  Increase distance to increase precision.
     * The permissible distance is the distance between clusters of far away particles from which those clusters should be treated as
     * one.
     *  Decrease the permissible distance to increase precision.
     * @param from
     * @param distance
     * @param permissible
     * @return
     */
    Iterator<Mass> approximatingIterator(Vector from, double distance, double permissible);

    boolean contains(Vector p);

}
