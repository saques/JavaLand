package com.land.java.Cryptography;

import java.math.BigInteger;

public class RSA {

    public static void main(String[] args) {
        KeySet s = RSA_keys(1024);

        System.out.println("N: " + s.n);
        System.out.println("PK: " +s.pk);


        byte[] text = ("The attempt on my life has left me scarred and deformed. " +
                       "But I assure you, my resolve has never been stronger!").getBytes();

        BigInteger m = new BigInteger(text);
        System.out.println("Plaintext: " + m);
        BigInteger c = encrypt(m,s.pk, s.n);
        System.out.println("Ciphertext: " + c);
        BigInteger d = decrypt(c,s.sk, s.n);
        System.out.println("Check: " + d.equals(m));
        String t = new String(d.toByteArray());
        System.out.println(t);

    }


    public static final class KeySet {
        BigInteger pk, sk, n;
        public KeySet(BigInteger pk, BigInteger sk, BigInteger n){
            this.pk = pk;
            this.sk = sk;
            this.n = n;
        }
    }

    public static KeySet RSA_keys(int bits){
        BigInteger p, q, n, t, e, d;

        p = BigNumAlg.randPrime(bits);
        q = BigNumAlg.randPrime(bits);
        n = p.multiply(q);
        t = BigNumAlg.lcm(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
        e = BigNumAlg.randRelPrimeTo(t);
        d = BigNumAlg.modInverse(e,t);

        return new KeySet(e, d, n);
    }

    public static BigInteger encrypt(BigInteger m, BigInteger pk, BigInteger n){
        return BigNumAlg.modPow(m, pk, n);
    }

    public static BigInteger decrypt(BigInteger c, BigInteger sk, BigInteger n){
        return BigNumAlg.modPow(c, sk, n);
    }




}
