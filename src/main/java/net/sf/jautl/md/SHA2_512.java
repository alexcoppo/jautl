/*
    Copyright (c) 2000-2012 Alessandro Coppo
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

import net.sf.jautl.utility.BitRotations;

/**
 * The implementation of the Secure Hash Algorithm SHA2-512.
 */
public final class SHA2_512 extends DigestEngine {
    private InputBuffer buffer = new InputBuffer(128);
    private long[] workArea = new long[8];
    private long[] W = new long[80];
    private long[] S = new long[8];
    private static long[] K = new long[] {
        0x428a2f98d728ae22L, 0x7137449123ef65cdL,
        0xb5c0fbcfec4d3b2fL, 0xe9b5dba58189dbbcL,
        0x3956c25bf348b538L, 0x59f111f1b605d019L,
        0x923f82a4af194f9bL, 0xab1c5ed5da6d8118L,
        0xd807aa98a3030242L, 0x12835b0145706fbeL,
        0x243185be4ee4b28cL, 0x550c7dc3d5ffb4e2L,
        0x72be5d74f27b896fL, 0x80deb1fe3b1696b1L,
        0x9bdc06a725c71235L, 0xc19bf174cf692694L,
        0xe49b69c19ef14ad2L, 0xefbe4786384f25e3L,
        0x0fc19dc68b8cd5b5L, 0x240ca1cc77ac9c65L,
        0x2de92c6f592b0275L, 0x4a7484aa6ea6e483L,
        0x5cb0a9dcbd41fbd4L, 0x76f988da831153b5L,
        0x983e5152ee66dfabL, 0xa831c66d2db43210L,
        0xb00327c898fb213fL, 0xbf597fc7beef0ee4L,
        0xc6e00bf33da88fc2L, 0xd5a79147930aa725L,
        0x06ca6351e003826fL, 0x142929670a0e6e70L,
        0x27b70a8546d22ffcL, 0x2e1b21385c26c926L,
        0x4d2c6dfc5ac42aedL, 0x53380d139d95b3dfL,
        0x650a73548baf63deL, 0x766a0abb3c77b2a8L,
        0x81c2c92e47edaee6L, 0x92722c851482353bL,
        0xa2bfe8a14cf10364L, 0xa81a664bbc423001L,
        0xc24b8b70d0f89791L, 0xc76c51a30654be30L,
        0xd192e819d6ef5218L, 0xd69906245565a910L,
        0xf40e35855771202aL, 0x106aa07032bbd1b8L,
        0x19a4c116b8d2d0c8L, 0x1e376c085141ab53L,
        0x2748774cdf8eeb99L, 0x34b0bcb5e19b48a8L,
        0x391c0cb3c5c95a63L, 0x4ed8aa4ae3418acbL,
        0x5b9cca4f7763e373L, 0x682e6ff3d6b2b8a3L,
        0x748f82ee5defb2fcL, 0x78a5636f43172f60L,
        0x84c87814a1f0ab72L, 0x8cc702081a6439ecL,
        0x90befffa23631e28L, 0xa4506cebde82bde9L,
        0xbef9a3f7b2c67915L, 0xc67178f2e372532bL,
        0xca273eceea26619cL, 0xd186b8c721c0c207L,
        0xeada7dd6cde0eb1eL, 0xf57d4f7fee6ed178L,
        0x06f067aa72176fbaL, 0x0a637dc5a2c898a6L,
        0x113f9804bef90daeL, 0x1b710b35131c471bL,
        0x28db77f523047d84L, 0x32caab7b40c72493L,
        0x3c9ebe0a15c9bebcL, 0x431d67c49c100d4cL,
        0x4cc5d4becb3e42b6L, 0x597f299cfc657e2aL,
        0x5fcb6fab3ad6faecL, 0x6c44198c4a475817L
    };

    /**
     * The constructor of the SHA2/512 class.
     */
    public SHA2_512() {
		super(64);
	}

	public void initiate() {
        workArea[0] = 0x6a09e667f3bcc908L;
        workArea[1] = 0xbb67ae8584caa73bL;
        workArea[2] = 0x3c6ef372fe94f82bL;
        workArea[3] = 0xa54ff53a5f1d36f1L;
        workArea[4] = 0x510e527fade682d1L;
        workArea[5] = 0x9b05688c2b3e6c1fL;
        workArea[6] = 0x1f83d9abfb41bd6bL;
        workArea[7] = 0x5be0cd19137e2179L;

        buffer.clear();
	}

	public void add(byte b) {
        buffer.add(b);
        if (!buffer.isFull()) return;
        compress();
        buffer.reset();
	}

	public void terminate() {
        int saveLoCount = (int)buffer.getLoBitsCount();
        int saveHiCount = (int)buffer.getHiBitsCount();

        add((byte)0x80);
        int currBlockPos = buffer.getLoBytesCount() & 127;
        if (currBlockPos >= 112) {
            while (currBlockPos++ < 128)
                add((byte)0x00);
            currBlockPos = 0;
        }

        while (currBlockPos++ < 120)
            add((byte)0x00);

        add((byte)((saveHiCount >>> 24) & 0xFF));
        add((byte)((saveHiCount >>> 16) & 0xFF));
        add((byte)((saveHiCount >>> 8) & 0xFF));
        add((byte)((saveHiCount) & 0xFF));
        add((byte)((saveLoCount >>> 24) & 0xFF));
        add((byte)((saveLoCount >>> 16) & 0xFF));
        add((byte)((saveLoCount >>> 8) & 0xFF));
        add((byte)((saveLoCount) & 0xFF));

        for (int i = 0; i < 8; i++) {
            digest[8 * i + 0] = (byte)(workArea[i] >>> 56);
            digest[8 * i + 1] = (byte)(workArea[i] >>> 48);
            digest[8 * i + 2] = (byte)(workArea[i] >>> 40);
            digest[8 * i + 3] = (byte)(workArea[i] >>> 32);
            digest[8 * i + 4] = (byte)(workArea[i] >>> 24);
            digest[8 * i + 5] = (byte)(workArea[i] >>> 16);
            digest[8 * i + 6] = (byte)(workArea[i] >> 8);
            digest[8 * i + 7] = (byte)(workArea[i]);
        }

        buffer.clear();
        for (int i = 0; i < 80; i++)
            W[i] = 0;
        for (int i = 0; i < 8; i++)
            S[i] = 0;
	}

    private void compress() {
        for (int i = 0; i < 8; i++)
            S[i] = workArea[i];

        for (int i = 0; i < 16; i++)
            W[i] = buffer.getInt64BigEndianAt(i);

        for (int i = 16; i < 80; i++) {
            W[i] = Gamma1(W[i - 2]) + W[i - 7];
            W[i] += Gamma0(W[i - 15]) + W[i - 16];
        }

        for (int i = 0; i < 80; i++) {
            long t0 = S[7] + Sigma1(S[4]) + Ch(S[4], S[5], S[6]) + K[i] + W[i];
            long t1 = Sigma0(S[0]) + Maj(S[0], S[1], S[2]);
            S[7] = S[6];
            S[6] = S[5];
            S[5] = S[4];
            S[4] = S[3] + t0;
            S[3] = S[2];
            S[2] = S[1];
            S[1] = S[0];
            S[0] = t0 + t1;
        }

        for (int i = 0; i < 8; i++)
            workArea[i] += S[i];
    }

    private long Ch(long x, long y, long z) {
        return (x & y) ^ (~x & z);
    }

    private long Maj(long x, long y, long z) {
        return (x & y) ^ (x & z) ^ (y & z);
    }

    private long Sigma0(long x) {
        return BitRotations.right(x, 28) ^ BitRotations.right(x, 34) ^ BitRotations.right(x, 39);
    }

    private long Sigma1(long x) {
        return BitRotations.right(x, 14) ^ BitRotations.right(x, 18) ^ BitRotations.right(x, 41);
    }

    private long Gamma0(long x) {
        return BitRotations.right(x, 1) ^ BitRotations.right(x, 8) ^ (x >>> 7);
    }

    private long Gamma1(long x) {
        return BitRotations.right(x, 19) ^ BitRotations.right(x, 61) ^ (x >>> 6);
    }
}
