package com.land.java.Cryptography;

import java.util.Random;
import java.util.stream.LongStream;

public final class NumAlg {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private NumAlg(){}

    public static long expstd(long n, long p, long m){
        if (m == 1) return 0;
        long ans = 1;
        for( ; p>0 ; p--){
            ans = (ans*n) % m;
        }
        return ans;
    }

    public static long expRTL(long n, long p, long m) {
        if (m == 1) return 0;
        long ans = 1;
        n %= m;
        while (p > 0) {
            if (p % 2 == 1)
                ans = (ans * n) % m;
            p >>= 1;
            n = (n * n) % m;
        }
        return ans;
    }

    public static long randPrime(long max){
        return randStream(max)
                .filter(NumAlg::prime)
                .findFirst()
                .orElse(1);
    }

    public static void shuffle(long[] a){
        int n = a.length;
        for(int i = 0 ; i < n; i++){
            long tmp = a[i];
            int rnd = RANDOM.nextInt(n);
            a[i] = a[rnd];
            a[rnd] = tmp;
        }
    }


    public static LongStream randStream(long max){
        return LongStream.generate(RANDOM::nextLong).filter(x-> x < max && x > 1 && x % 2 == 1);
    }

    public static long randPrime(){
        return randPrime(Long.MAX_VALUE);
    }

    public static long randRelPrimeTo(long max){
        return randStream(max)
                .filter(x-> gcd(x,max) == 1)
                .findFirst()
                .orElse(1);

    }

    public static boolean prime(long n){
        if (n <= 1)
            return false;
        else if (n <= 3)
            return true;
        else if (n % 2 == 0 || n % 3 == 0)
            return false;
        long i = 5;
        while(i*i <= n){
            if(n % i == 0 || n % (i+2) == 0)
                return false;
            i += 6;
        }
        return true;
    }

    public static long gcd(long a, long b){
        if(a == b)
            return a;
        if(a > b)
            return gcd(a - b, b);
        else
            return gcd(a, b - a);
    }

    /**
     *
     * @param a
     * @param b
     * @return ans[0] = gcd, ans[1] = x, ans[2] = y
     */
    public static long[] bezoutGcd(long a, long b){
        long s = 0, oldS = 1, t = 1, oldT = 0, r = b, oldR = a;

        while (r != 0){
            long quotient = oldR / r;

            long tmp = r;
            r = oldR - quotient*r;
            oldR = tmp;

            tmp = s;
            s = oldS - quotient*s;
            oldS = tmp;

            tmp = t;
            t = oldT - quotient*t;
            oldT = tmp;
        }

        long ans[] = new long[3];
        ans[0] = oldR; ans[1] = oldS; ans[2] = oldT;
        return ans;

    }

    public static long lcm(long a, long b){
        return (a * b)/gcd(a, b);
    }

}
