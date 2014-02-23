/*
    Copyright (c) 2000-2014 Alessandro Coppo
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
 * The implementation of the RIPEMD128 hashing algorithm. This algorithm was 
 * developed in the framework of the EU project RIPE (RACE Integrity Primitives
 * Evaluation, 1988-1992) and it the work of <A href="mailto:dobbertin@skom.rhein.de">Hans
 * Dobbertin</A>, <A href="http://www.esat.kuleuven.ac.be/~bosselae">Antoon Bosselaers</A>,
 * and <A href="http://www.esat.kuleuven.ac.be/~preneel">Bart Preneel</A>.
 * <p>For the documentation, reference implementation and other information,
 * see the <a href="http://www.esat.kuleuven.ac.be/~bosselae/ripemd160.html">RIPEMD page</a>.
 */
public final class RIPEMD128 extends DigestEngine {
    private InputBuffer buffer = new InputBuffer(64);
    private int[] workArea = new int[4];
    private int[] addDataTmp = new int[16];
    private int[] terminateTmp = new int[16];

    /**
     * The constructor of the RIPEMD128 class.
     */
    public RIPEMD128() {
        super(16);
    }

    public void initiate() {
        workArea[0] = 0x67452301;
        workArea[1] = 0xEFCDAB89;
        workArea[2] = 0x98BADCFE;
        workArea[3] = 0x10325476;
        buffer.clear();
    }

    public void add(byte b) {
        buffer.add(b);
        if (!buffer.isFull()) return;
        for (int i = 0; i <= 15; i++)
            addDataTmp[i] = (int)buffer.getInt32SmallEndianAt(i);
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
            terminateTmp[i] = (int)buffer.getInt32SmallEndianAt(i);
        terminateTmp[14] = saveLoCount;
        terminateTmp[15] = saveHiCount;

        transform(terminateTmp);

        int ii = 0;

        for (i = 0; i < 4; i++) {
            digest[ii++] = (byte)( workArea[i]        & 0xFF);
            digest[ii++] = (byte)((workArea[i] >>  8) & 0xFF);
            digest[ii++] = (byte)((workArea[i] >> 16) & 0xFF);
            digest[ii++] = (byte)((workArea[i] >> 24) & 0xFF);
        }

        //Remove security sensitive information
        Arrays.fill(addDataTmp, 0);
        Arrays.fill(terminateTmp, 0);
        Arrays.fill(workArea, 0);
        buffer.clear();
    }

    private int F(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private int G(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private int H(int x, int y, int z) {
        return (x | ~y) ^ z;
    }

    private int I(int x, int y, int z) {
        return (x & z) | (y & ~z);
    }

    private int FF(int a, int b, int c, int d, int x, int s) {
        return BitRotations.left(a + F(b, c, d) + x, s);
    }

    private int GG(int a, int b, int c, int d, int x, int s) {
        return BitRotations.left(a + G(b, c, d) + x + 0x5A827999, s);
    }

    private int HH(int a, int b, int c, int d, int x, int s) {
        return BitRotations.left(a + H(b, c, d) + x + 0x6ED9EBA1, s);
    }

    private int II(int a, int b, int c, int d, int x, int s) {
        return BitRotations.left(a + I(b, c, d) + x + 0x8F1BBCDC, s);
    }

    private int FFF(int a, int b, int c, int d, int x, int s) {
        return BitRotations.left(a + F(b, c, d) + x, s);
    }

    private int GGG(int a, int b, int c, int d, int x, int s) {
        return BitRotations.left(a + G(b, c, d) + x + 0x6D703EF3, s);
    }

    private int HHH(int a, int b, int c, int d, int x, int s) {
        return BitRotations.left(a + H(b, c, d) + x + 0x5C4DD124, s);
    }

    private int III(int a, int b, int c, int d, int x, int s) {
        return BitRotations.left(a + I(b, c, d) + x + 0x50A28BE6, s);
    }

    private void transform(int[] x) {
        int aa  = workArea[0];
        int bb  = workArea[1];
        int cc  = workArea[2];
        int dd  = workArea[3];
        int aaa = workArea[0];
        int bbb = workArea[1];
        int ccc = workArea[2];
        int ddd = workArea[3];

        aa = FF(aa, bb, cc, dd, x[ 0], 11);
        dd = FF(dd, aa, bb, cc, x[ 1], 14);
        cc = FF(cc, dd, aa, bb, x[ 2], 15);
        bb = FF(bb, cc, dd, aa, x[ 3], 12);
        aa = FF(aa, bb, cc, dd, x[ 4],  5);
        dd = FF(dd, aa, bb, cc, x[ 5],  8);
        cc = FF(cc, dd, aa, bb, x[ 6],  7);
        bb = FF(bb, cc, dd, aa, x[ 7],  9);
        aa = FF(aa, bb, cc, dd, x[ 8], 11);
        dd = FF(dd, aa, bb, cc, x[ 9], 13);
        cc = FF(cc, dd, aa, bb, x[10], 14);
        bb = FF(bb, cc, dd, aa, x[11], 15);
        aa = FF(aa, bb, cc, dd, x[12],  6);
        dd = FF(dd, aa, bb, cc, x[13],  7);
        cc = FF(cc, dd, aa, bb, x[14],  9);
        bb = FF(bb, cc, dd, aa, x[15],  8);

        aa = GG(aa, bb, cc, dd, x[ 7],  7);
        dd = GG(dd, aa, bb, cc, x[ 4],  6);
        cc = GG(cc, dd, aa, bb, x[13],  8);
        bb = GG(bb, cc, dd, aa, x[ 1], 13);
        aa = GG(aa, bb, cc, dd, x[10], 11);
        dd = GG(dd, aa, bb, cc, x[ 6],  9);
        cc = GG(cc, dd, aa, bb, x[15],  7);
        bb = GG(bb, cc, dd, aa, x[ 3], 15);
        aa = GG(aa, bb, cc, dd, x[12],  7);
        dd = GG(dd, aa, bb, cc, x[ 0], 12);
        cc = GG(cc, dd, aa, bb, x[ 9], 15);
        bb = GG(bb, cc, dd, aa, x[ 5],  9);
        aa = GG(aa, bb, cc, dd, x[ 2], 11);
        dd = GG(dd, aa, bb, cc, x[14],  7);
        cc = GG(cc, dd, aa, bb, x[11], 13);
        bb = GG(bb, cc, dd, aa, x[ 8], 12);

        aa = HH(aa, bb, cc, dd, x[ 3], 11);
        dd = HH(dd, aa, bb, cc, x[10], 13);
        cc = HH(cc, dd, aa, bb, x[14],  6);
        bb = HH(bb, cc, dd, aa, x[ 4],  7);
        aa = HH(aa, bb, cc, dd, x[ 9], 14);
        dd = HH(dd, aa, bb, cc, x[15],  9);
        cc = HH(cc, dd, aa, bb, x[ 8], 13);
        bb = HH(bb, cc, dd, aa, x[ 1], 15);
        aa = HH(aa, bb, cc, dd, x[ 2], 14);
        dd = HH(dd, aa, bb, cc, x[ 7],  8);
        cc = HH(cc, dd, aa, bb, x[ 0], 13);
        bb = HH(bb, cc, dd, aa, x[ 6],  6);
        aa = HH(aa, bb, cc, dd, x[13],  5);
        dd = HH(dd, aa, bb, cc, x[11], 12);
        cc = HH(cc, dd, aa, bb, x[ 5],  7);
        bb = HH(bb, cc, dd, aa, x[12],  5);

        aa = II(aa, bb, cc, dd, x[ 1], 11);
        dd = II(dd, aa, bb, cc, x[ 9], 12);
        cc = II(cc, dd, aa, bb, x[11], 14);
        bb = II(bb, cc, dd, aa, x[10], 15);
        aa = II(aa, bb, cc, dd, x[ 0], 14);
        dd = II(dd, aa, bb, cc, x[ 8], 15);
        cc = II(cc, dd, aa, bb, x[12],  9);
        bb = II(bb, cc, dd, aa, x[ 4],  8);
        aa = II(aa, bb, cc, dd, x[13],  9);
        dd = II(dd, aa, bb, cc, x[ 3], 14);
        cc = II(cc, dd, aa, bb, x[ 7],  5);
        bb = II(bb, cc, dd, aa, x[15],  6);
        aa = II(aa, bb, cc, dd, x[14],  8);
        dd = II(dd, aa, bb, cc, x[ 5],  6);
        cc = II(cc, dd, aa, bb, x[ 6],  5);
        bb = II(bb, cc, dd, aa, x[ 2], 12);

        aaa = III(aaa, bbb, ccc, ddd, x[ 5],  8);
        ddd = III(ddd, aaa, bbb, ccc, x[14],  9);
        ccc = III(ccc, ddd, aaa, bbb, x[ 7],  9);
        bbb = III(bbb, ccc, ddd, aaa, x[ 0], 11);
        aaa = III(aaa, bbb, ccc, ddd, x[ 9], 13);
        ddd = III(ddd, aaa, bbb, ccc, x[ 2], 15);
        ccc = III(ccc, ddd, aaa, bbb, x[11], 15);
        bbb = III(bbb, ccc, ddd, aaa, x[ 4],  5);
        aaa = III(aaa, bbb, ccc, ddd, x[13],  7);
        ddd = III(ddd, aaa, bbb, ccc, x[ 6],  7);
        ccc = III(ccc, ddd, aaa, bbb, x[15],  8);
        bbb = III(bbb, ccc, ddd, aaa, x[ 8], 11);
        aaa = III(aaa, bbb, ccc, ddd, x[ 1], 14);
        ddd = III(ddd, aaa, bbb, ccc, x[10], 14);
        ccc = III(ccc, ddd, aaa, bbb, x[ 3], 12);
        bbb = III(bbb, ccc, ddd, aaa, x[12],  6);

        aaa = HHH(aaa, bbb, ccc, ddd, x[ 6],  9);
        ddd = HHH(ddd, aaa, bbb, ccc, x[11], 13);
        ccc = HHH(ccc, ddd, aaa, bbb, x[ 3], 15);
        bbb = HHH(bbb, ccc, ddd, aaa, x[ 7],  7);
        aaa = HHH(aaa, bbb, ccc, ddd, x[ 0], 12);
        ddd = HHH(ddd, aaa, bbb, ccc, x[13],  8);
        ccc = HHH(ccc, ddd, aaa, bbb, x[ 5],  9);
        bbb = HHH(bbb, ccc, ddd, aaa, x[10], 11);
        aaa = HHH(aaa, bbb, ccc, ddd, x[14],  7);
        ddd = HHH(ddd, aaa, bbb, ccc, x[15],  7);
        ccc = HHH(ccc, ddd, aaa, bbb, x[ 8], 12);
        bbb = HHH(bbb, ccc, ddd, aaa, x[12],  7);
        aaa = HHH(aaa, bbb, ccc, ddd, x[ 4],  6);
        ddd = HHH(ddd, aaa, bbb, ccc, x[ 9], 15);
        ccc = HHH(ccc, ddd, aaa, bbb, x[ 1], 13);
        bbb = HHH(bbb, ccc, ddd, aaa, x[ 2], 11);

        aaa = GGG(aaa, bbb, ccc, ddd, x[15],  9);
        ddd = GGG(ddd, aaa, bbb, ccc, x[ 5],  7);
        ccc = GGG(ccc, ddd, aaa, bbb, x[ 1], 15);
        bbb = GGG(bbb, ccc, ddd, aaa, x[ 3], 11);
        aaa = GGG(aaa, bbb, ccc, ddd, x[ 7],  8);
        ddd = GGG(ddd, aaa, bbb, ccc, x[14],  6);
        ccc = GGG(ccc, ddd, aaa, bbb, x[ 6],  6);
        bbb = GGG(bbb, ccc, ddd, aaa, x[ 9], 14);
        aaa = GGG(aaa, bbb, ccc, ddd, x[11], 12);
        ddd = GGG(ddd, aaa, bbb, ccc, x[ 8], 13);
        ccc = GGG(ccc, ddd, aaa, bbb, x[12],  5);
        bbb = GGG(bbb, ccc, ddd, aaa, x[ 2], 14);
        aaa = GGG(aaa, bbb, ccc, ddd, x[10], 13);
        ddd = GGG(ddd, aaa, bbb, ccc, x[ 0], 13);
        ccc = GGG(ccc, ddd, aaa, bbb, x[ 4],  7);
        bbb = GGG(bbb, ccc, ddd, aaa, x[13],  5);

        aaa = FFF(aaa, bbb, ccc, ddd, x[ 8], 15);
        ddd = FFF(ddd, aaa, bbb, ccc, x[ 6],  5);
        ccc = FFF(ccc, ddd, aaa, bbb, x[ 4],  8);
        bbb = FFF(bbb, ccc, ddd, aaa, x[ 1], 11);
        aaa = FFF(aaa, bbb, ccc, ddd, x[ 3], 14);
        ddd = FFF(ddd, aaa, bbb, ccc, x[11], 14);
        ccc = FFF(ccc, ddd, aaa, bbb, x[15],  6);
        bbb = FFF(bbb, ccc, ddd, aaa, x[ 0], 14);
        aaa = FFF(aaa, bbb, ccc, ddd, x[ 5],  6);
        ddd = FFF(ddd, aaa, bbb, ccc, x[12],  9);
        ccc = FFF(ccc, ddd, aaa, bbb, x[ 2], 12);
        bbb = FFF(bbb, ccc, ddd, aaa, x[13],  9);
        aaa = FFF(aaa, bbb, ccc, ddd, x[ 9], 12);
        ddd = FFF(ddd, aaa, bbb, ccc, x[ 7],  5);
        ccc = FFF(ccc, ddd, aaa, bbb, x[10], 15);
        bbb = FFF(bbb, ccc, ddd, aaa, x[14],  8);

        ddd = ddd + cc + workArea[1];
        workArea[1] = workArea[2] + dd + aaa;
        workArea[2] = workArea[3] + aa + bbb;
        workArea[3] = workArea[0] + bb + ccc;
        workArea[0] = ddd;

        //Remove security sensitive information
        aa = 0;
        bb = 0;
        cc = 0;
        dd = 0;
        aaa = 0;
        bbb = 0;
        ccc = 0;
        ddd = 0;
    }
}