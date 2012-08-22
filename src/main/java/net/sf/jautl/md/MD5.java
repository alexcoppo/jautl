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
 * The implementation of the MD5 hashing algorithm as described in RFC 1321.
 * For any information, see that RFC. The code has been checked trying it 
 * against all the test data provided in RFC 1321.
 */
public final class MD5 extends DigestEngine {
    private InputBuffer buffer = new InputBuffer(64);
    private int[] workArea = new int[4];
    private int[] addDataTmp = new int[16];
    private int[] terminateTmp = new int[16];

    /**
     * The constructor of the MD5 class.
     */
    public MD5() {
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

    private static int F(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private static int G(int x, int y, int z) {
        return (x & z) | (y & ~z);
    }

    private static int H(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private static int I(int x, int y, int z) {
        return y ^ (x | ~z);
    }

    private static int FF(int a, int b, int c, int d, int x, int s, int ac) {
        a += F(b, c, d) + x + ac; 
        a  = BitRotations.left(a, s);
        a += b;
        return a;
    }

    private static int GG(int a, int b, int c, int d, int x, int s, int ac) {
        a += G(b, c, d) + x + ac;
        a  = BitRotations.left(a, s);
        a += b;
        return a;
    }

    private static int HH(int a, int b, int c, int d, int x, int s, int ac) {
        a += H(b, c, d) + x + ac;
        a  = BitRotations.left(a, s);
        a += b;
        return a;
    }

    private static int II(int a, int b, int c, int d, int x, int s, int ac) {
        a += I(b, c, d) + x + ac;
        a  = BitRotations.left(a, s);
        a += b;
        return a;
    }

    private void transform(int[] in) {
        int a = workArea[0];
        int b = workArea[1];
        int c = workArea[2];
        int d = workArea[3];

        a = FF(a, b, c, d, in[ 0],  7, 0xD76AA478);
        d = FF(d, a, b, c, in[ 1], 12, 0xE8C7B756);
        c = FF(c, d, a, b, in[ 2], 17, 0x242070DB);
        b = FF(b, c, d, a, in[ 3], 22, 0xC1BDCEEE);
        a = FF(a, b, c, d, in[ 4],  7, 0xF57C0FAF);
        d = FF(d, a, b, c, in[ 5], 12, 0x4787C62A);
        c = FF(c, d, a, b, in[ 6], 17, 0xA8304613);
        b = FF(b, c, d, a, in[ 7], 22, 0xFD469501);
        a = FF(a, b, c, d, in[ 8],  7, 0x698098D8);
        d = FF(d, a, b, c, in[ 9], 12, 0x8B44F7AF);
        c = FF(c, d, a, b, in[10], 17, 0xFFFF5BB1);
        b = FF(b, c, d, a, in[11], 22, 0x895CD7BE);
        a = FF(a, b, c, d, in[12],  7, 0x6B901122);
        d = FF(d, a, b, c, in[13], 12, 0xFD987193);
        c = FF(c, d, a, b, in[14], 17, 0xA679438E);
        b = FF(b, c, d, a, in[15], 22, 0x49B40821);

        a = GG(a, b, c, d, in[ 1],  5, 0xF61E2562);
        d = GG(d, a, b, c, in[ 6],  9, 0xC040B340);
        c = GG(c, d, a, b, in[11], 14, 0x265E5A51);
        b = GG(b, c, d, a, in[ 0], 20, 0xE9B6C7AA);
        a = GG(a, b, c, d, in[ 5],  5, 0xD62F105D);
        d = GG(d, a, b, c, in[10],  9, 0x02441453);
        c = GG(c, d, a, b, in[15], 14, 0xD8A1E681);
        b = GG(b, c, d, a, in[ 4], 20, 0xE7D3FBC8);
        a = GG(a, b, c, d, in[ 9],  5, 0x21E1CDE6);
        d = GG(d, a, b, c, in[14],  9, 0xC33707D6);
        c = GG(c, d, a, b, in[ 3], 14, 0xF4D50D87);
        b = GG(b, c, d, a, in[ 8], 20, 0x455A14ED);
        a = GG(a, b, c, d, in[13],  5, 0xA9E3E905);
        d = GG(d, a, b, c, in[ 2],  9, 0xFCEFA3F8);
        c = GG(c, d, a, b, in[ 7], 14, 0x676F02D9);
        b = GG(b, c, d, a, in[12], 20, 0x8D2A4C8A);

        a = HH(a, b, c, d, in[ 5],  4, 0xFFFA3942);
        d = HH(d, a, b, c, in[ 8], 11, 0x8771F681);
        c = HH(c, d, a, b, in[11], 16, 0x6D9D6122);
        b = HH(b, c, d, a, in[14], 23, 0xFDE5380C);
        a = HH(a, b, c, d, in[ 1],  4, 0xA4BEEA44);
        d = HH(d, a, b, c, in[ 4], 11, 0x4BDECFA9);
        c = HH(c, d, a, b, in[ 7], 16, 0xF6BB4B60);
        b = HH(b, c, d, a, in[10], 23, 0xBEBFBC70);
        a = HH(a, b, c, d, in[13],  4, 0x289B7EC6);
        d = HH(d, a, b, c, in[ 0], 11, 0xEAA127FA);
        c = HH(c, d, a, b, in[ 3], 16, 0xD4EF3085);
        b = HH(b, c, d, a, in[ 6], 23, 0x04881D05);
        a = HH(a, b, c, d, in[ 9],  4, 0xD9D4D039);
        d = HH(d, a, b, c, in[12], 11, 0xE6DB99E5);
        c = HH(c, d, a, b, in[15], 16, 0x1FA27CF8);
        b = HH(b, c, d, a, in[ 2], 23, 0xC4AC5665);

        a = II(a, b, c, d, in[ 0],  6, 0xF4292244);
        d = II(d, a, b, c, in[ 7], 10, 0x432AFF97);
        c = II(c, d, a, b, in[14], 15, 0xAB9423A7);
        b = II(b, c, d, a, in[ 5], 21, 0xFC93A039);
        a = II(a, b, c, d, in[12],  6, 0x655B59C3);
        d = II(d, a, b, c, in[ 3], 10, 0x8F0CCC92);
        c = II(c, d, a, b, in[10], 15, 0xFFEFF47D);
        b = II(b, c, d, a, in[ 1], 21, 0x85845DD1);
        a = II(a, b, c, d, in[ 8],  6, 0x6FA87E4F);
        d = II(d, a, b, c, in[15], 10, 0xFE2CE6E0);
        c = II(c, d, a, b, in[ 6], 15, 0xA3014314);
        b = II(b, c, d, a, in[13], 21, 0x4E0811A1);
        a = II(a, b, c, d, in[ 4],  6, 0xF7537E82);
        d = II(d, a, b, c, in[11], 10, 0xBD3AF235);
        c = II(c, d, a, b, in[ 2], 15, 0x2AD7D2BB);
        b = II(b, c, d, a, in[ 9], 21, 0xEB86D391);

        workArea[0] += a;
        workArea[1] += b;
        workArea[2] += c;
        workArea[3] += d;

        //Remove security sensitive information
        a = 0;
        b = 0;
        c = 0;
        d = 0;
    }
}