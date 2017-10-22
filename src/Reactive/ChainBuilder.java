package Reactive;

import java.util.Map;
import java.util.function.BiFunction;

public class ChainBuilder {
    private ChainBuilder(){}
    /**
     * Receives a string representing a Polish Notation chain.
     * Do not expect this to work if you pass shit as parameters.
     * The numbers in the string are not numbers, are indices.
     * Every index should have a non-null associated value, otherwise
     * you'll get a beautiful NullPointerException.
     *
     * @param chain
     * @return the chain
     */
    public static Chain<Integer> build(Map<Integer, Integer> map, String... chain){
        Chain<Integer> ans = new Chain<Integer>(null, -1, null, map);
        ans.setId(Integer.valueOf(chain[0]));
        for(int i=1; i<chain.length; i+=2){
            ans = new Chain<Integer>(ans,Integer.valueOf(chain[i]),getOperand(chain[i+1]),map);
        }
        return ans;
    }
    private static BiFunction<Integer, Integer, Integer> getOperand(String s){

        switch (s){
            case "+":
                return Operations.SUM;
            case "-":
                return Operations.SUBTRACTION;
            case "*":
                return Operations.PRODUCT;
            case "/":
                return Operations.DIVISION;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}
