package com.land.java.Reactive;

import java.util.Map;
import java.util.function.BiFunction;

public class Chain<T> {

    private String opString;
    private Chain<T> chain;
    private String id;
    private BiFunction<T, T, T> f;
    private Map<String, T> m;

    public Chain(Chain o, String id, BiFunction<T, T, T> f, Map<String, T> m, String opString){
        this.chain = o;
        this.id= id;
        this.f = f;
        this.m = m;
        this.opString = opString;
    }

    public T evaluate(){
        if(chain == null){
            System.out.print(id+",");
            return m.get(id);
        }
        T o = chain.evaluate();
        System.out.print(id+","+opString);
        return f.apply(m.get(id),o);
    }

    public void setId(String id){
        this.id = id;
    }

}
