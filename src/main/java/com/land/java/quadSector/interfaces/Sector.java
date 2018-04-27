package com.land.java.quadSector.interfaces;
import com.land.java.quadSector.MassIteratorContainer;
import com.land.java.quadSector.Vector;

import java.util.Optional;

public interface Sector extends Iterable<Mass> {

    Sector add(Mass m);

    int size();

    int buckets();

    Optional<Mass> delete(Vector p);

    Optional<Mass> get(Vector p);

    Optional<MassIteratorContainer> approximatingIterator(Vector from, double distance, double permissible);

    boolean contains(Vector p);

}
