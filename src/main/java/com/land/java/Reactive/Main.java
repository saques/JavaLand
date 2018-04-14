package com.land.java.Reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String ... args){

        Scanner s = new Scanner(System.in);
        Map<String, Integer> map = new HashMap<>();

        System.out.println("Enter a comma separated RPN operation");

        Chain<Integer> chain = ChainBuilder.build(map, s.nextLine().split(","));


        while (true) {
            String id;
            int val;
            id = s.next();
            val = s.nextInt();
            map.put(id, val);
            System.out.println(" = " + chain.evaluate());
        }

    }

}
