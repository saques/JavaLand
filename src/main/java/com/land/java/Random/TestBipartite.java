package com.land.java.Random;// Don't place your source in a package
import java.util.*;
import java.lang.*;

// Please name your class Random.TestBipartite
public class TestBipartite {

    private static class Node {
        int id;
        List<Node> adj;
        boolean visited;
        int colour = -2;

        public Node(int id) {
            this.id = id;
            this.adj = new ArrayList<>();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (!(o instanceof Node)) {
                return false;
            }
            if (((Node) o).id == this.id)
                return true;
            return false;
        }

        public int hashCode() {
            return Integer.valueOf(id).hashCode();
        }

    }

    public static void main(String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        Map<Integer, Node> s = new HashMap<>();
        int n;
        n = in.nextInt();

        while (true) {
            int a = in.nextInt();
            int b = in.nextInt();
            Node n_a = s.get(a);

            if (n_a == null)
                s.put(a, n_a = new Node(a));

            Node n_b = s.get(b);

            if (n_b == null)
                s.put(b, n_b = new Node(b));

            n_a.adj.add(n_b);
            n_b.adj.add(n_a);

            for(Node p: s.values()){
                if(p.colour == -2 && !bipartite(p,1,null)){
                    System.out.println(0);
                    return;
                }
            }
            s.values().stream().forEach(x->x.colour=-2);
            System.out.println(1);

        }
    }
    public static boolean bipartite(Node n, int colour, Node from){

        Queue<Node> q = new LinkedList<>();
        n.colour = colour;
        n.visited = true;

        for(Node o: n.adj){
            q.add(o);
            o.colour = n.colour*-1;
            o.visited = true;
        }

        while (!q.isEmpty()) {
            Node curr = q.poll();
            for (Node o : curr.adj) {
                if (!o.visited) {
                    o.colour = curr.colour * -1;
                    o.visited = true;
                    q.add(o);
                } else if (o.visited && o.colour == curr.colour) {
                    return false;
                }
            }
        }
        return true;
    }
}
