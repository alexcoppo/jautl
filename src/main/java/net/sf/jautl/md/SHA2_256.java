/*
    Copyright (c) 2000-2013 Alessandro Coppo
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.
    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.jautl.md;

import java.util.Arrays;

/**
 * The implementation of the Secure Hash Algorithm SHA2-256.
 */
public final class SHA2_256 extends DigestEngine {
    private InputBuffer buffer = new InputBuffer(64);
    private int[] state = new int[8];
    private int[] S = new int[8];
    private int[] W = new int[64];
    private int[] K = {
        0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
        0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
        0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
        0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
        0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
        0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
        0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
        0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
        0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
        0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
        0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
        0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
        0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
        0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
        0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
        0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    /**
     * The constructor of the SHA2/256 class.
     */
    public SHA2_256() {
        super(32);
    }

    public void initiate() {
        state[0] = 0x6A09E667;
        state[1] = 0xBB67AE85;
        state[2] = 0x3C6EF372;
        state[3] = 0xA54FF53A;
        state[4] = 0x510E527F;
        state[5] = 0x9B05688C;
        state[6] = 0x1F83D9AB;
        state[7] = 0x5BE0CD19;

        buffer.clear();
    }

    public void add(byte b) {
        buffer.add(b);
        if (!buffer.isFull()) return;
        update();
        buffer.reset();
    }

    public void terminate() {
        long messageBitCount = buffer.getLoBitsCount();

        buffer.add((byte)0x80);
        if ((buffer.getLoBytesCount() & 63) >= 56) {
            while (!buffer.isFull())
                buffer.add((byte)0);
            update();
            buffer.reset();
        }

        /* pad upto 56 bytes of zeroes */
        while ((buffer.getLoBytesCount() & 63) != 56)
            buffer.add((byte)0);

        /* append length */
        buffer.add((byte)0);
        buffer.add((byte)0);
        buffer.add((byte)0);
        buffer.add((byte)0);
        buffer.add((byte)((messageBitCount >> 24) & 0xFF));
        buffer.add((byte)((messageBitCount >> 16) & 0xFF));
        buffer.add((byte)((messageBitCount >>  8) & 0xFF));
        buffer.add((byte)( messageBitCount        & 0xFF));
        update();
        buffer.reset();

        for (int i = 0; i < 32; i++)
            digest[i] = (byte)((state[i >> 2] >> (((3 - i) & 3) << 3)) & 255);

        //Remove security sensitive information
        Arrays.fill(S, 0);
        Arrays.fill(W, 0);
        buffer.clear();
    }

    private int Ch(int x, int y, int z) {
        return (x & y) ^ (~x & z);
    }

    private int Maj(int x, int y, int z) {
        return (x & y) ^ (x & z) ^ (y & z);
    }

    private int S(int x, int n) {
        return (x >>> n) | (x << (32 - n));
    }

    private int R(int x, int n) {
        return x >>> n;
    }

    private int Sigma0(int x) {
        return S(x, 2) ^ S(x, 13) ^ S(x, 22);
    }

    private int Sigma1(int x) {
        return S(x, 6) ^ S(x, 11) ^ S(x, 25);
    }

    private int Gamma0(int x) {
        return S(x, 7) ^ S(x, 18) ^ R(x, 3);
    }

    private int Gamma1(int x) {
        return S(x, 17) ^ S(x, 19) ^ R(x, 10);
    }

    private void update() {
        int i;

        for (i = 0; i < 8; i++)
            S[i] = state[i];

        for (i = 0; i < 16; i++)
            W[i] = buffer.getInt32BigEndianAt(i);

        for (i = 16; i < 64; i++)
            W[i] = Gamma1(W[i - 2]) + W[i - 7] + Gamma0(W[i - 15]) + W[i - 16];
        
        int t0;
        for (i = 0; i < 64; i++) {
            t0 = S[7] + Sigma1(S[4]) + Ch(S[4], S[5], S[6]) + K[i] + W[i];
            S[7] = S[6];
            S[6] = S[5];
            S[5] = S[4];
            S[4] = S[3] + t0;
            S[3] = S[2];
            S[2] = S[1];
            S[1] = S[0];
            S[0] = t0 + Sigma0(S[1]) + Maj(S[1], S[2], S[3]);
        }

        for (i = 0; i < 8; i++)
            state[i] += S[i];

        t0 = 0;
    }
}