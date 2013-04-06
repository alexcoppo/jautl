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

import net.sf.jautl.utility.BitRotations;

/**
 * The implementation of the RIPEMD160 hashing algorithm. This algorithm was 
 * developed in the framework of the EU project RIPE (RACE Integrity Primitives
 * Evaluation, 1988-1992) and it the work of <A href="mailto:dobbertin@skom.rhein.de">Hans
 * Dobbertin</A>, <A href="http://www.esat.kuleuven.ac.be/~bosselae">Antoon Bosselaers</A>,
 * and <A href="http://www.esat.kuleuven.ac.be/~preneel">Bart Preneel</A>.
 * <p>For the documentation, reference implementation and other information,
 * see the <a href="http://www.esat.kuleuven.ac.be/~bosselae/ripemd160.html">RIPEMD page</a>.
 */
public final class RIPEMD160 extends DigestEngine {
    private InputBuffer buffer = new InputBuffer(64);
    private int[] workArea = new int[5];
    private int[] addDataTmp = new int[16];
    private int[] terminateTmp = new int[16];

    /**
     * The constructor of the RIPEMD160 class.
     */
    public RIPEMD160() {
        super(20);
    }

    public void initiate() {
        workArea[0] = 0x67452301;
        workArea[1] = 0xEFCDAB89;
        workArea[2] = 0x98BADCFE;
        workArea[3] = 0x10325476;    
        workArea[4] = 0xC3D2E1F0;
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

        for (i = 0; i < 5; i++) {
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

    private int J(int x, int y, int z) {
        return x ^ (y | ~z);
    }

    private int FF(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + F(b, c, d) + x, s) + e;
    }

    private int GG(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + G(b, c, d) + x + 0x5A827999, s) + e;
    }

    private int HH(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + H(b, c, d) + x + 0x6ED9EBA1, s) + e;
    }

    private int II(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + I(b, c, d) + x + 0x8F1BBCDC, s) + e;
    }

    private int JJ(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + J(b, c, d) + x + 0xA953FD4E, s) + e;
    }

    private int FFF(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + F(b, c, d) + x, s) + e;
    }

    private int GGG(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + G(b, c, d) + x + 0x7A6D76E9, s) + e;
    }

    private int HHH(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + H(b, c, d) + x + 0x6D703EF3, s) + e;
    }

    private int III(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + I(b, c, d) + x + 0x5C4DD124, s) + e;
    }

    private int JJJ(int a, int b, int c, int d, int e, int x, int s) {
        return BitRotations.left(a + J(b, c, d) + x + 0x50A28BE6, s) + e;
    }

    private void transform(int[] x) {
        int aa = workArea[0];
        int bb = workArea[1];
        int cc = workArea[2];
        int dd = workArea[3];
        int ee = workArea[4];
        int aaa = workArea[0];
        int bbb = workArea[1];
        int ccc = workArea[2];
        int ddd = workArea[3];
        int eee = workArea[4];

        aa = FF(aa, bb, cc, dd, ee, x[ 0], 11); cc = BitRotations.left(cc, 10);
        ee = FF(ee, aa, bb, cc, dd, x[ 1], 14); bb = BitRotations.left(bb, 10);
        dd = FF(dd, ee, aa, bb, cc, x[ 2], 15); aa = BitRotations.left(aa, 10);
        cc = FF(cc, dd, ee, aa, bb, x[ 3], 12); ee = BitRotations.left(ee, 10);
        bb = FF(bb, cc, dd, ee, aa, x[ 4],  5); dd = BitRotations.left(dd, 10);
        aa = FF(aa, bb, cc, dd, ee, x[ 5],  8); cc = BitRotations.left(cc, 10);
        ee = FF(ee, aa, bb, cc, dd, x[ 6],  7); bb = BitRotations.left(bb, 10);
        dd = FF(dd, ee, aa, bb, cc, x[ 7],  9); aa = BitRotations.left(aa, 10);
        cc = FF(cc, dd, ee, aa, bb, x[ 8], 11); ee = BitRotations.left(ee, 10);
        bb = FF(bb, cc, dd, ee, aa, x[ 9], 13); dd = BitRotations.left(dd, 10);
        aa = FF(aa, bb, cc, dd, ee, x[10], 14); cc = BitRotations.left(cc, 10);
        ee = FF(ee, aa, bb, cc, dd, x[11], 15); bb = BitRotations.left(bb, 10);
        dd = FF(dd, ee, aa, bb, cc, x[12],  6); aa = BitRotations.left(aa, 10);
        cc = FF(cc, dd, ee, aa, bb, x[13],  7); ee = BitRotations.left(ee, 10);
        bb = FF(bb, cc, dd, ee, aa, x[14],  9); dd = BitRotations.left(dd, 10);
        aa = FF(aa, bb, cc, dd, ee, x[15],  8); cc = BitRotations.left(cc, 10);

        ee = GG(ee, aa, bb, cc, dd, x[ 7],  7); bb = BitRotations.left(bb, 10);
        dd = GG(dd, ee, aa, bb, cc, x[ 4],  6); aa = BitRotations.left(aa, 10);
        cc = GG(cc, dd, ee, aa, bb, x[13],  8); ee = BitRotations.left(ee, 10);
        bb = GG(bb, cc, dd, ee, aa, x[ 1], 13); dd = BitRotations.left(dd, 10);
        aa = GG(aa, bb, cc, dd, ee, x[10], 11); cc = BitRotations.left(cc, 10);
        ee = GG(ee, aa, bb, cc, dd, x[ 6],  9); bb = BitRotations.left(bb, 10);
        dd = GG(dd, ee, aa, bb, cc, x[15],  7); aa = BitRotations.left(aa, 10);
        cc = GG(cc, dd, ee, aa, bb, x[ 3], 15); ee = BitRotations.left(ee, 10);
        bb = GG(bb, cc, dd, ee, aa, x[12],  7); dd = BitRotations.left(dd, 10);
        aa = GG(aa, bb, cc, dd, ee, x[ 0], 12); cc = BitRotations.left(cc, 10);
        ee = GG(ee, aa, bb, cc, dd, x[ 9], 15); bb = BitRotations.left(bb, 10);
        dd = GG(dd, ee, aa, bb, cc, x[ 5],  9); aa = BitRotations.left(aa, 10);
        cc = GG(cc, dd, ee, aa, bb, x[ 2], 11); ee = BitRotations.left(ee, 10);
        bb = GG(bb, cc, dd, ee, aa, x[14],  7); dd = BitRotations.left(dd, 10);
        aa = GG(aa, bb, cc, dd, ee, x[11], 13); cc = BitRotations.left(cc, 10);
        ee = GG(ee, aa, bb, cc, dd, x[ 8], 12); bb = BitRotations.left(bb, 10);

        dd = HH(dd, ee, aa, bb, cc, x[ 3], 11); aa = BitRotations.left(aa, 10);
        cc = HH(cc, dd, ee, aa, bb, x[10], 13); ee = BitRotations.left(ee, 10);
        bb = HH(bb, cc, dd, ee, aa, x[14],  6); dd = BitRotations.left(dd, 10);
        aa = HH(aa, bb, cc, dd, ee, x[ 4],  7); cc = BitRotations.left(cc, 10);
        ee = HH(ee, aa, bb, cc, dd, x[ 9], 14); bb = BitRotations.left(bb, 10);
        dd = HH(dd, ee, aa, bb, cc, x[15],  9); aa = BitRotations.left(aa, 10);
        cc = HH(cc, dd, ee, aa, bb, x[ 8], 13); ee = BitRotations.left(ee, 10);
        bb = HH(bb, cc, dd, ee, aa, x[ 1], 15); dd = BitRotations.left(dd, 10);
        aa = HH(aa, bb, cc, dd, ee, x[ 2], 14); cc = BitRotations.left(cc, 10);
        ee = HH(ee, aa, bb, cc, dd, x[ 7],  8); bb = BitRotations.left(bb, 10);
        dd = HH(dd, ee, aa, bb, cc, x[ 0], 13); aa = BitRotations.left(aa, 10);
        cc = HH(cc, dd, ee, aa, bb, x[ 6],  6); ee = BitRotations.left(ee, 10);
        bb = HH(bb, cc, dd, ee, aa, x[13],  5); dd = BitRotations.left(dd, 10);
        aa = HH(aa, bb, cc, dd, ee, x[11], 12); cc = BitRotations.left(cc, 10);
        ee = HH(ee, aa, bb, cc, dd, x[ 5],  7); bb = BitRotations.left(bb, 10);
        dd = HH(dd, ee, aa, bb, cc, x[12],  5); aa = BitRotations.left(aa, 10);

        cc = II(cc, dd, ee, aa, bb, x[ 1], 11); ee = BitRotations.left(ee, 10);
        bb = II(bb, cc, dd, ee, aa, x[ 9], 12); dd = BitRotations.left(dd, 10);
        aa = II(aa, bb, cc, dd, ee, x[11], 14); cc = BitRotations.left(cc, 10);
        ee = II(ee, aa, bb, cc, dd, x[10], 15); bb = BitRotations.left(bb, 10);
        dd = II(dd, ee, aa, bb, cc, x[ 0], 14); aa = BitRotations.left(aa, 10);
        cc = II(cc, dd, ee, aa, bb, x[ 8], 15); ee = BitRotations.left(ee, 10);
        bb = II(bb, cc, dd, ee, aa, x[12],  9); dd = BitRotations.left(dd, 10);
        aa = II(aa, bb, cc, dd, ee, x[ 4],  8); cc = BitRotations.left(cc, 10);
        ee = II(ee, aa, bb, cc, dd, x[13],  9); bb = BitRotations.left(bb, 10);
        dd = II(dd, ee, aa, bb, cc, x[ 3], 14); aa = BitRotations.left(aa, 10);
        cc = II(cc, dd, ee, aa, bb, x[ 7],  5); ee = BitRotations.left(ee, 10);
        bb = II(bb, cc, dd, ee, aa, x[15],  6); dd = BitRotations.left(dd, 10);
        aa = II(aa, bb, cc, dd, ee, x[14],  8); cc = BitRotations.left(cc, 10);
        ee = II(ee, aa, bb, cc, dd, x[ 5],  6); bb = BitRotations.left(bb, 10);
        dd = II(dd, ee, aa, bb, cc, x[ 6],  5); aa = BitRotations.left(aa, 10);
        cc = II(cc, dd, ee, aa, bb, x[ 2], 12); ee = BitRotations.left(ee, 10);

        bb = JJ(bb, cc, dd, ee, aa, x[ 4],  9); dd = BitRotations.left(dd, 10);
        aa = JJ(aa, bb, cc, dd, ee, x[ 0], 15); cc = BitRotations.left(cc, 10);
        ee = JJ(ee, aa, bb, cc, dd, x[ 5],  5); bb = BitRotations.left(bb, 10);
        dd = JJ(dd, ee, aa, bb, cc, x[ 9], 11); aa = BitRotations.left(aa, 10);
        cc = JJ(cc, dd, ee, aa, bb, x[ 7],  6); ee = BitRotations.left(ee, 10);
        bb = JJ(bb, cc, dd, ee, aa, x[12],  8); dd = BitRotations.left(dd, 10);
        aa = JJ(aa, bb, cc, dd, ee, x[ 2], 13); cc = BitRotations.left(cc, 10);
        ee = JJ(ee, aa, bb, cc, dd, x[10], 12); bb = BitRotations.left(bb, 10);
        dd = JJ(dd, ee, aa, bb, cc, x[14],  5); aa = BitRotations.left(aa, 10);
        cc = JJ(cc, dd, ee, aa, bb, x[ 1], 12); ee = BitRotations.left(ee, 10);
        bb = JJ(bb, cc, dd, ee, aa, x[ 3], 13); dd = BitRotations.left(dd, 10);
        aa = JJ(aa, bb, cc, dd, ee, x[ 8], 14); cc = BitRotations.left(cc, 10);
        ee = JJ(ee, aa, bb, cc, dd, x[11], 11); bb = BitRotations.left(bb, 10);
        dd = JJ(dd, ee, aa, bb, cc, x[ 6],  8); aa = BitRotations.left(aa, 10);
        cc = JJ(cc, dd, ee, aa, bb, x[15],  5); ee = BitRotations.left(ee, 10);
        bb = JJ(bb, cc, dd, ee, aa, x[13],  6); dd = BitRotations.left(dd, 10);

        aaa = JJJ(aaa, bbb, ccc, ddd, eee, x[ 5],  8); ccc = BitRotations.left(ccc, 10);
        eee = JJJ(eee, aaa, bbb, ccc, ddd, x[14],  9); bbb = BitRotations.left(bbb, 10);
        ddd = JJJ(ddd, eee, aaa, bbb, ccc, x[ 7],  9); aaa = BitRotations.left(aaa, 10);
        ccc = JJJ(ccc, ddd, eee, aaa, bbb, x[ 0], 11); eee = BitRotations.left(eee, 10);
        bbb = JJJ(bbb, ccc, ddd, eee, aaa, x[ 9], 13); ddd = BitRotations.left(ddd, 10);
        aaa = JJJ(aaa, bbb, ccc, ddd, eee, x[ 2], 15); ccc = BitRotations.left(ccc, 10);
        eee = JJJ(eee, aaa, bbb, ccc, ddd, x[11], 15); bbb = BitRotations.left(bbb, 10);
        ddd = JJJ(ddd, eee, aaa, bbb, ccc, x[ 4],  5); aaa = BitRotations.left(aaa, 10);
        ccc = JJJ(ccc, ddd, eee, aaa, bbb, x[13],  7); eee = BitRotations.left(eee, 10);
        bbb = JJJ(bbb, ccc, ddd, eee, aaa, x[ 6],  7); ddd = BitRotations.left(ddd, 10);
        aaa = JJJ(aaa, bbb, ccc, ddd, eee, x[15],  8); ccc = BitRotations.left(ccc, 10);
        eee = JJJ(eee, aaa, bbb, ccc, ddd, x[ 8], 11); bbb = BitRotations.left(bbb, 10);
        ddd = JJJ(ddd, eee, aaa, bbb, ccc, x[ 1], 14); aaa = BitRotations.left(aaa, 10);
        ccc = JJJ(ccc, ddd, eee, aaa, bbb, x[10], 14); eee = BitRotations.left(eee, 10);
        bbb = JJJ(bbb, ccc, ddd, eee, aaa, x[ 3], 12); ddd = BitRotations.left(ddd, 10);
        aaa = JJJ(aaa, bbb, ccc, ddd, eee, x[12],  6); ccc = BitRotations.left(ccc, 10);

        eee = III(eee, aaa, bbb, ccc, ddd, x[ 6],  9); bbb = BitRotations.left(bbb, 10);
        ddd = III(ddd, eee, aaa, bbb, ccc, x[11], 13); aaa = BitRotations.left(aaa, 10);
        ccc = III(ccc, ddd, eee, aaa, bbb, x[ 3], 15); eee = BitRotations.left(eee, 10);
        bbb = III(bbb, ccc, ddd, eee, aaa, x[ 7],  7); ddd = BitRotations.left(ddd, 10);
        aaa = III(aaa, bbb, ccc, ddd, eee, x[ 0], 12); ccc = BitRotations.left(ccc, 10);
        eee = III(eee, aaa, bbb, ccc, ddd, x[13],  8); bbb = BitRotations.left(bbb, 10);
        ddd = III(ddd, eee, aaa, bbb, ccc, x[ 5],  9); aaa = BitRotations.left(aaa, 10);
        ccc = III(ccc, ddd, eee, aaa, bbb, x[10], 11); eee = BitRotations.left(eee, 10);
        bbb = III(bbb, ccc, ddd, eee, aaa, x[14],  7); ddd = BitRotations.left(ddd, 10);
        aaa = III(aaa, bbb, ccc, ddd, eee, x[15],  7); ccc = BitRotations.left(ccc, 10);
        eee = III(eee, aaa, bbb, ccc, ddd, x[ 8], 12); bbb = BitRotations.left(bbb, 10);
        ddd = III(ddd, eee, aaa, bbb, ccc, x[12],  7); aaa = BitRotations.left(aaa, 10);
        ccc = III(ccc, ddd, eee, aaa, bbb, x[ 4],  6); eee = BitRotations.left(eee, 10);
        bbb = III(bbb, ccc, ddd, eee, aaa, x[ 9], 15); ddd = BitRotations.left(ddd, 10);
        aaa = III(aaa, bbb, ccc, ddd, eee, x[ 1], 13); ccc = BitRotations.left(ccc, 10);
        eee = III(eee, aaa, bbb, ccc, ddd, x[ 2], 11); bbb = BitRotations.left(bbb, 10);

        ddd = HHH(ddd, eee, aaa, bbb, ccc, x[15],  9); aaa = BitRotations.left(aaa, 10);
        ccc = HHH(ccc, ddd, eee, aaa, bbb, x[ 5],  7); eee = BitRotations.left(eee, 10);
        bbb = HHH(bbb, ccc, ddd, eee, aaa, x[ 1], 15); ddd = BitRotations.left(ddd, 10);
        aaa = HHH(aaa, bbb, ccc, ddd, eee, x[ 3], 11); ccc = BitRotations.left(ccc, 10);
        eee = HHH(eee, aaa, bbb, ccc, ddd, x[ 7],  8); bbb = BitRotations.left(bbb, 10);
        ddd = HHH(ddd, eee, aaa, bbb, ccc, x[14],  6); aaa = BitRotations.left(aaa, 10);
        ccc = HHH(ccc, ddd, eee, aaa, bbb, x[ 6],  6); eee = BitRotations.left(eee, 10);
        bbb = HHH(bbb, ccc, ddd, eee, aaa, x[ 9], 14); ddd = BitRotations.left(ddd, 10);
        aaa = HHH(aaa, bbb, ccc, ddd, eee, x[11], 12); ccc = BitRotations.left(ccc, 10);
        eee = HHH(eee, aaa, bbb, ccc, ddd, x[ 8], 13); bbb = BitRotations.left(bbb, 10);
        ddd = HHH(ddd, eee, aaa, bbb, ccc, x[12],  5); aaa = BitRotations.left(aaa, 10);
        ccc = HHH(ccc, ddd, eee, aaa, bbb, x[ 2], 14); eee = BitRotations.left(eee, 10);
        bbb = HHH(bbb, ccc, ddd, eee, aaa, x[10], 13); ddd = BitRotations.left(ddd, 10);
        aaa = HHH(aaa, bbb, ccc, ddd, eee, x[ 0], 13); ccc = BitRotations.left(ccc, 10);
        eee = HHH(eee, aaa, bbb, ccc, ddd, x[ 4],  7); bbb = BitRotations.left(bbb, 10);
        ddd = HHH(ddd, eee, aaa, bbb, ccc, x[13],  5); aaa = BitRotations.left(aaa, 10);

        ccc = GGG(ccc, ddd, eee, aaa, bbb, x[ 8], 15); eee = BitRotations.left(eee, 10);
        bbb = GGG(bbb, ccc, ddd, eee, aaa, x[ 6],  5); ddd = BitRotations.left(ddd, 10);
        aaa = GGG(aaa, bbb, ccc, ddd, eee, x[ 4],  8); ccc = BitRotations.left(ccc, 10);
        eee = GGG(eee, aaa, bbb, ccc, ddd, x[ 1], 11); bbb = BitRotations.left(bbb, 10);
        ddd = GGG(ddd, eee, aaa, bbb, ccc, x[ 3], 14); aaa = BitRotations.left(aaa, 10);
        ccc = GGG(ccc, ddd, eee, aaa, bbb, x[11], 14); eee = BitRotations.left(eee, 10);
        bbb = GGG(bbb, ccc, ddd, eee, aaa, x[15],  6); ddd = BitRotations.left(ddd, 10);
        aaa = GGG(aaa, bbb, ccc, ddd, eee, x[ 0], 14); ccc = BitRotations.left(ccc, 10);
        eee = GGG(eee, aaa, bbb, ccc, ddd, x[ 5],  6); bbb = BitRotations.left(bbb, 10);
        ddd = GGG(ddd, eee, aaa, bbb, ccc, x[12],  9); aaa = BitRotations.left(aaa, 10);
        ccc = GGG(ccc, ddd, eee, aaa, bbb, x[ 2], 12); eee = BitRotations.left(eee, 10);
        bbb = GGG(bbb, ccc, ddd, eee, aaa, x[13],  9); ddd = BitRotations.left(ddd, 10);
        aaa = GGG(aaa, bbb, ccc, ddd, eee, x[ 9], 12); ccc = BitRotations.left(ccc, 10);
        eee = GGG(eee, aaa, bbb, ccc, ddd, x[ 7],  5); bbb = BitRotations.left(bbb, 10);
        ddd = GGG(ddd, eee, aaa, bbb, ccc, x[10], 15); aaa = BitRotations.left(aaa, 10);
        ccc = GGG(ccc, ddd, eee, aaa, bbb, x[14],  8); eee = BitRotations.left(eee, 10);

        bbb = FFF(bbb, ccc, ddd, eee, aaa, x[12] ,  8); ddd = BitRotations.left(ddd, 10);
        aaa = FFF(aaa, bbb, ccc, ddd, eee, x[15] ,  5); ccc = BitRotations.left(ccc, 10);
        eee = FFF(eee, aaa, bbb, ccc, ddd, x[10] , 12); bbb = BitRotations.left(bbb, 10);
        ddd = FFF(ddd, eee, aaa, bbb, ccc, x[ 4] ,  9); aaa = BitRotations.left(aaa, 10);
        ccc = FFF(ccc, ddd, eee, aaa, bbb, x[ 1] , 12); eee = BitRotations.left(eee, 10);
        bbb = FFF(bbb, ccc, ddd, eee, aaa, x[ 5] ,  5); ddd = BitRotations.left(ddd, 10);
        aaa = FFF(aaa, bbb, ccc, ddd, eee, x[ 8] , 14); ccc = BitRotations.left(ccc, 10);
        eee = FFF(eee, aaa, bbb, ccc, ddd, x[ 7] ,  6); bbb = BitRotations.left(bbb, 10);
        ddd = FFF(ddd, eee, aaa, bbb, ccc, x[ 6] ,  8); aaa = BitRotations.left(aaa, 10);
        ccc = FFF(ccc, ddd, eee, aaa, bbb, x[ 2] , 13); eee = BitRotations.left(eee, 10);
        bbb = FFF(bbb, ccc, ddd, eee, aaa, x[13] ,  6); ddd = BitRotations.left(ddd, 10);
        aaa = FFF(aaa, bbb, ccc, ddd, eee, x[14] ,  5); ccc = BitRotations.left(ccc, 10);
        eee = FFF(eee, aaa, bbb, ccc, ddd, x[ 0] , 15); bbb = BitRotations.left(bbb, 10);
        ddd = FFF(ddd, eee, aaa, bbb, ccc, x[ 3] , 13); aaa = BitRotations.left(aaa, 10);
        ccc = FFF(ccc, ddd, eee, aaa, bbb, x[ 9] , 11); eee = BitRotations.left(eee, 10);
        bbb = FFF(bbb, ccc, ddd, eee, aaa, x[11] , 11); ddd = BitRotations.left(ddd, 10);

        ddd += workArea[1] + cc;
        workArea[1] = workArea[2] + dd + eee;
        workArea[2] = workArea[3] + ee + aaa;
        workArea[3] = workArea[4] + aa + bbb;
        workArea[4] = workArea[0] + bb + ccc;
        workArea[0] = ddd;

        //Remove security sensitive information
        aa = 0;
        bb = 0;
        cc = 0;
        dd = 0;
        ee = 0;
        aaa = 0;
        bbb = 0;
        ccc = 0;
        ddd = 0;
        eee = 0;
    }
}