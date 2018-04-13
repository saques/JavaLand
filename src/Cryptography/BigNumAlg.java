package Cryptography;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.stream.Stream;

public final class BigNumAlg {

    private BigNumAlg() {}

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int FERMAT_TIMES = 3;
    public static final BigInteger TWO = new BigInteger("2");
    public static final BigInteger THREE = new BigInteger("3");
    public static final BigInteger FIVE = new BigInteger("5");
    public static final BigInteger SIX = new BigInteger("6");

    public static BigInteger modPow(BigInteger n, BigInteger p, BigInteger m){
        if(m.equals(BigInteger.ONE)) return BigInteger.ZERO;
        BigInteger ans = BigInteger.ONE;
        n = n.mod(m);
        while(p.compareTo(BigInteger.ZERO) > 0){
            if(p.mod(TWO).equals(BigInteger.ONE))
                ans = ans.multiply(n).mod(m);
            p = p.shiftRight(1);
            n = n.pow(2).mod(m);
        }
        return ans;
    }

    public static boolean prime(BigInteger n){
        if(n.compareTo(BigInteger.ONE) <= 0)
            return false;
        else if (n.compareTo(THREE) <= 0)
            return true;
        else if (n.mod(TWO).equals(BigInteger.ZERO) || n.mod(THREE).equals(BigInteger.ZERO))
            return false;
        BigInteger i = FIVE;
        while(i.pow(2).compareTo(n) <= 0){
            if(n.mod(i).equals(BigInteger.ZERO) || n.mod(i.add(TWO)).equals(0))
                return false;
            i = i.add(SIX);
        }
        return true;
    }

    /**
     * Using Fermat's little theorem to determine if a number is prime.
     * It may happen that at every k, a pseudoprime is generated and tested
     * against the theorem, though the probability is negligible.
     * @param n
     * @param k
     * @return
     */
    public static boolean primeFermat(BigInteger n, int k){
        for(; k>0 ; k--){
            BigInteger a = rand(n.subtract(TWO));
            if(!modPow(a,n.subtract(BigInteger.ONE),n).equals(BigInteger.ONE))
                return false;
        }
        return true;
    }

    public static BigInteger randPrime(int bits){
        BigInteger ans = new BigInteger(bits, RANDOM);
        while (!primeFermat(ans,FERMAT_TIMES))
            ans = new BigInteger(bits,RANDOM);
        return ans;
    }

    public static BigInteger rand(BigInteger n){
        int length = n.bitLength();
        BigInteger ans, s, nm1 = n.subtract(BigInteger.ONE);
        do {
            s = new BigInteger(length + 100, RANDOM);
            ans = s.mod(n);
        } while (s.subtract(ans).add(nm1).bitLength() >= length + 100 || ans.compareTo(TWO) <= 0);
        return ans;
    }

    public static BigInteger randRelPrimeTo(BigInteger n){
        return Stream.generate(()->rand(n.subtract(BigInteger.ONE)))
                     .filter(x->primeFermat(x,FERMAT_TIMES))
                     .findFirst().orElse(TWO);
    }

    public static BigInteger gcd(BigInteger a, BigInteger b){
        if(a.equals(b))
            return a;
        if(a.compareTo(b) > 0)
            return gcd(a.subtract(b), b);
        else
            return gcd(a, b.subtract(a));
    }

    public static BigInteger fastGcd(BigInteger a, BigInteger b){
        int d = 0;
        while(a.mod(TWO).equals(BigInteger.ZERO) && b.mod(TWO).equals(BigInteger.ZERO)){
            a = a.divide(TWO);
            b = b.divide(TWO);
            d ++;
        }
        while(!a.equals(b)){
            if (a.mod(TWO).equals(BigInteger.ZERO))
                a = a.divide(TWO);
            else if (b.mod(TWO).equals(BigInteger.ZERO))
                b = b.divide(TWO);
            else if (a.compareTo(b) > 0)
                a = (a.subtract(b)).divide(TWO);
            else
                b = b.subtract(a).divide(TWO);
        }
        return a.multiply(TWO.pow(d));
    }

    public static BigInteger lcm(BigInteger a, BigInteger b){
        return (a.multiply(b)).divide(fastGcd(a,b));
    }

    public static BigInteger modInverse(BigInteger a, BigInteger m){
        BigInteger t = BigInteger.ZERO, newT = BigInteger.ONE;
        BigInteger r = m, newR = a;
        while (!newR.equals(BigInteger.ZERO)){
            BigInteger quotient = r.divide(newR);

            BigInteger tmp = t.subtract(quotient.multiply(newT));
            t = newT;
            newT = tmp;

            tmp = r.subtract(quotient.multiply(newR));
            r = newR;
            newR = tmp;
        }
        if(r.compareTo(BigInteger.ONE) > 0) return null;
        if(t.compareTo(BigInteger.ZERO) < 0) t = t.add(m);
        return t;
    }

}
