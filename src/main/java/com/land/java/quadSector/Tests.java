package com.land.java.quadSector;

import com.land.java.quadSector.interfaces.Mass;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tests {

    public static void main(String ... args){
        /**
         * The objective is that avg. occupancy of buckets is around
         * 50% of bucket capacity
         *
         * The loadFactor could be reduced with each subdivision of
         * a plain sector.
         */
        //test1();
        //testApproximatingIterator(500000,50, new Vector(25465,31457), 15000, 100000,2000);
        //testApproximatingIterator(1000000,40, new Vector(50000,50000), 5000, 100000,1000);
        //testInsertionTimes(1000000,50, new Vector(50000,50000), 10000, 100000,2000);
        testApproximatingIteratorTime(1000000,40, new Vector(50000,50000), 25000, 50000,750);
    }

    private static void test1(){
        List<Mass> l = Arrays.asList(new MassImpl(1,1,100), new MassImpl(1,5,600), new MassImpl(3,7,800));
        MassContainer container = new MassContainer(2);
        l.forEach(x->container.add(x));
        container.approximatingIterator(new Vector(100,500),10,0).forEachRemaining(x-> System.out.println(x));
        container.delete(new Vector(3,7));
    }

    private static void testInsertionTimes(int N, int loadFactor, Vector p, double distance, double range, double pr){

        List<Mass> l = IntStream.range(0, N).mapToObj(x->
            new MassImpl(Math.random()*range, Math.random()*range, Math.random()*range*2)
        ).collect(Collectors.toList());

        long t0 = System.currentTimeMillis();
        Set<Mass> s = new HashSet<>();
        l.forEach(x->s.add(x));
        System.out.println(System.currentTimeMillis()-t0);
        System.out.println(ObjectSizeCalculator.getObjectSize(s));

        t0 = System.currentTimeMillis();
        MassContainer container = new MassContainer(loadFactor);
        l.forEach(x->container.add(x));
        System.out.println(System.currentTimeMillis()-t0);
        System.out.println(ObjectSizeCalculator.getObjectSize(container));
    }

    private static void testApproximatingIterator(int N, int loadFactor, Vector p, double distance, double range, double pr){
        MassContainer container = new MassContainer(loadFactor);
        IntStream.range(0, N).forEach(x->{
            container.add(new MassImpl(Math.random()*range, Math.random()*range, Math.random()*range*2));
        });

        Iterator<Mass> iterator = container.iterator();
        double mass = 0;
        int count = 0;
        while (iterator.hasNext()){
            mass += iterator.next().getMass();
            count++;
        }
        System.out.println(count);
        System.out.println(mass);
        iterator = container.approximatingIterator(p, distance, pr);
        mass = 0;
        count = 0;
        while (iterator.hasNext()) {
            mass += iterator.next().getMass();
            count++;
        }
        System.out.println(count);
        System.out.println(mass);
    }

    private static void testApproximatingIteratorTime(int N, int loadFactor, Vector p, double distance, double range, double pr){
        MassContainer container = new MassContainer(loadFactor);
        IntStream.range(0, N).forEach(x->{
            container.add(new MassImpl(Math.random()*range, Math.random()*range, Math.random()*range*2));
        });

        Iterator<Mass> iterator = null ;
        int count = 0;
        long t0 = 0;
        for(int i=0; i<10; i++) {
            iterator = container.iterator();
            count = 0;
            t0 = System.currentTimeMillis();
            while (iterator.hasNext()) {
                iterator.next();
                count++;
            }

            System.out.printf("Time 0: %d, Count: %d\n", System.currentTimeMillis() - t0, count);
        }
        for(int i=0; i<10; i++) {
            iterator = container.approximatingIterator(p, distance, pr);
            count = 0;
            t0 = System.currentTimeMillis();
            while (iterator.hasNext()) {
                iterator.next();
                count++;
            }
            System.out.printf("Time 1: %d, Count: %d\n", System.currentTimeMillis() - t0, count);
        }
    }
}
