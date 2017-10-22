package Reactive;

import java.util.function.BiFunction;

public final class Operations {

    public static final BiFunction<Integer, Integer, Integer> SUM = (x, y) -> x + y;

    public static final BiFunction<Integer, Integer, Integer> SUBTRACTION = (x, y) -> x - y;

    public static final BiFunction<Integer, Integer, Integer> PRODUCT = (x, y) -> x*y;

    public static final BiFunction<Integer, Integer, Integer> DIVISION = (x, y) -> x/y;

    private Operations(){}

}
