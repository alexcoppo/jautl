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

import java.util.Arrays;

import net.sf.jautl.utility.BitRotations;

/**
 * The implementation of the Secure Hash Algorithm as described in FIPS 
 * publication 180-1. For further information, see that publication or any 
 * book about cryptography.
 * <p>The code has been checked trying it against all the test data provided
 * in FIPS 180-1.
 */
public final class SHA1 extends DigestEngine {
    private InputBuffer buffer = new InputBuffer(64);
    private int[] w = new int[80];
    private int[] addDataTmp = new int[16];
    private int[] terminateTmp = new int[16];
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int[] h = new int[5];

    /**
     * Construct an SHA1 engine instance.
     */
    public SHA1() {
        super(20);
    }

    public void initiate() {
        h[0] = 0x67452301;
        h[1] = 0xEFCDAB89;
        h[2] = 0x98BADCFE;
        h[3] = 0x10325476;
        h[4] = 0xC3D2E1F0;
        buffer.clear();
    }

    public void add(byte b) {
        buffer.add(b);
        if (!buffer.isFull()) return;
        for (int i = 0; i <= 15; i++)
            addDataTmp[i] = (int)buffer.getInt32BigEndianAt(i);
        transform(addDataTmp);
        buffer.reset();
    }	

    public void terminate() {
        int saveLoCount = (int)buffer.getLoBitsCount();
        int saveHiCount = (int)buffer.getHiBitsCount();

        byte[] padding = new byte[1];
        padding[0] = (byte)0x80;
        add(padding, 0, 1);

        padding[0] = (byte)0;

        int currentSize = (int)(buffer.getLoBytesCount() & 63);
        int bytesToAdd = (currentSize < 56) ? 56 - currentSize : 120 - currentSize;

        while (bytesToAdd > 0) {
            add(padding, 0, 1);
            bytesToAdd--;
        }

        int i;

        for (i = 0; i < 14; i++)
            terminateTmp[i] = (int)buffer.getInt32BigEndianAt(i);
        terminateTmp[14] = saveHiCount;
        terminateTmp[15] = saveLoCount;

        transform(terminateTmp);

        int ii = 0;

        for (i = 0; i <= 4; i++) {
            digest[ii++] = (byte)((h[i] >> 24) & 0xFF);
            digest[ii++] = (byte)((h[i] >> 16) & 0xFF);
            digest[ii++] = (byte)((h[i] >>  8) & 0xFF);
            digest[ii++] = (byte)( h[i]        & 0xFF);
        }

        //Remove security sensitive information
        Arrays.fill(addDataTmp, 0);
        Arrays.fill(terminateTmp, 0);
        Arrays.fill(w, 0);
        a = 0;
        b = 0;
        c = 0;
        d = 0;
        e = 0;
        buffer.clear();
    }

    private void transform(int[] data) {
        int i;
        for (i = 0; i < 16; i++)
            w[i] = data[i];

        for (i = 16; i < 80; i++)
            w[i] = BitRotations.left(w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16], 1);

        a = h[0];
        b = h[1];
        c = h[2];
        d = h[3];
        e = h[4];

        pass0();
        pass1();
        pass2();
        pass3();

        h[0] += a;
        h[1] += b;
        h[2] += c;
        h[3] += d;
        h[4] += e;
    }

    private int F1(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private int F2(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private int F3(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    private int F4(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private void pass0() {
        for (int i = 0; i < 20; i++) {
            int tmp = BitRotations.left(a, 5) + F1(b, c, d) + e + w[i] + 0x5A827999;
            e = d;
            d = c;
            c = BitRotations.left(b, 30);
            b = a;
            a = tmp;
        }
    }

    private void pass1() {
        for (int i = 20; i < 40; i++) {
            int tmp = BitRotations.left(a, 5) + F2(b, c, d) + e + w[i] + 0x6ED9EBA1;
            e = d;
            d = c;
            c = BitRotations.left(b, 30);
            b = a;
            a = tmp;
        }
    }

    private void pass2() {
        for (int i = 40; i < 60; i++) {
            int tmp = BitRotations.left(a, 5) + F3(b, c, d) + e + w[i] + 0x8F1BBCDC;
            e = d;
            d = c;
            c = BitRotations.left(b, 30);
            b = a;
            a = tmp;
        }
    }

    private void pass3() {
        for (int i = 60; i < 80; i++) {
            int tmp = BitRotations.left(a, 5) + F4(b, c, d) + e + w[i] + 0xCA62C1D6;
            e = d;
            d = c;
            c = BitRotations.left(b, 30);
            b = a;
            a = tmp;
        }
    }
}