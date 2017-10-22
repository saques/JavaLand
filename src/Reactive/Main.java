package Reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String ... args){

        Map<Integer, Integer> map = new HashMap<>();

        Chain<Integer> chain = ChainBuilder.build(map, "0", "1", "+", "2", "*");

        map.put(0,1);
        map.put(1,2);
        map.put(2,2);


        Scanner s = new Scanner(System.in);

        while (true){
            int id, val;
            id = s.nextInt();
            val = s.nextInt();
            map.put(id, val);
            System.out.println("Evaluation: " + chain.evaluate());
        }

    }

}
