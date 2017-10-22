package Reactive;

import java.util.Map;
import java.util.function.BiFunction;

public class Chain<T> {

    private Chain<T> chain;
    private int id;
    private BiFunction<T, T, T> f;
    private Map<Integer, T> m;

    public Chain(Chain o, int id, BiFunction<T, T, T> f, Map<Integer, T> m){
        this.chain = o;
        this.id= id;
        this.f = f;
        this.m = m;
    }

    public T evaluate(){
        if(chain == null){
            return m.get(id);
        }
        return f.apply(m.get(id),chain.evaluate());
    }

    public void setChain(Chain<T> chain){
        this.chain = chain;
    }

    public void setId(int id){
        this.id = id;
    }

}
