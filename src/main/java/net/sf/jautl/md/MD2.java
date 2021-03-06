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

/**
 * The implementation of the MD2 hashing algorithm as described in RFC 1319.
 * For any information, see that RFC. The code has been checked trying it 
 * against all the test data provided in RFC 1319.
 */
public final class MD2 extends DigestEngine {
    private InputBuffer buff = new InputBuffer(16);
    private byte[] checksum = new byte[16];
    private byte[] x = new byte[48];
    private byte[] pad = new byte[16];
    private byte[] s = {
        (byte) 41, (byte) 46, (byte) 67, (byte)201, (byte)162, (byte)216, (byte)124, (byte)  1,
        (byte) 61, (byte) 54, (byte) 84, (byte)161, (byte)236, (byte)240, (byte)  6, (byte) 19,
        (byte) 98, (byte)167, (byte)  5, (byte)243, (byte)192, (byte)199, (byte)115, (byte)140,
        (byte)152, (byte)147, (byte) 43, (byte)217, (byte)188, (byte) 76, (byte)130, (byte)202,
        (byte) 30, (byte)155, (byte) 87, (byte) 60, (byte)253, (byte)212, (byte)224, (byte) 22,
        (byte)103, (byte) 66, (byte)111, (byte) 24, (byte)138, (byte) 23, (byte)229, (byte) 18,
        (byte)190, (byte) 78, (byte)196, (byte)214, (byte)218, (byte)158, (byte)222, (byte) 73,
        (byte)160, (byte)251, (byte)245, (byte)142, (byte)187, (byte) 47, (byte)238, (byte)122,
        (byte)169, (byte)104, (byte)121, (byte)145, (byte) 21, (byte)178, (byte)  7, (byte) 63,
        (byte)148, (byte)194, (byte) 16, (byte)137, (byte) 11, (byte) 34, (byte) 95, (byte) 33,
        (byte)128, (byte)127, (byte) 93, (byte)154, (byte) 90, (byte)144, (byte) 50, (byte) 39,
        (byte) 53, (byte) 62, (byte)204, (byte)231, (byte)191, (byte)247, (byte)151, (byte)  3,
        (byte)255, (byte) 25, (byte) 48, (byte)179, (byte) 72, (byte)165, (byte)181, (byte)209,
        (byte)215, (byte) 94, (byte)146, (byte) 42, (byte)172, (byte) 86, (byte)170, (byte)198,
        (byte) 79, (byte)184, (byte) 56, (byte)210, (byte)150, (byte)164, (byte)125, (byte)182,
        (byte)118, (byte)252, (byte)107, (byte)226, (byte)156, (byte)116, (byte)  4, (byte)241,
        (byte) 69, (byte)157, (byte)112, (byte) 89, (byte)100, (byte)113, (byte)135, (byte) 32,
        (byte)134, (byte) 91, (byte)207, (byte)101, (byte)230, (byte) 45, (byte)168, (byte)  2,
        (byte) 27, (byte) 96, (byte) 37, (byte)173, (byte)174, (byte)176, (byte)185, (byte)246,
        (byte) 28, (byte) 70, (byte) 97, (byte)105, (byte) 52, (byte) 64, (byte)126, (byte) 15,
        (byte) 85, (byte) 71, (byte)163, (byte) 35, (byte)221, (byte) 81, (byte)175, (byte) 58,
        (byte)195, (byte) 92, (byte)249, (byte)206, (byte)186, (byte)197, (byte)234, (byte) 38,
        (byte) 44, (byte) 83, (byte) 13, (byte)110, (byte)133, (byte) 40, (byte)132, (byte)  9,
        (byte)211, (byte)223, (byte)205, (byte)244, (byte) 65, (byte)129, (byte) 77, (byte) 82,
        (byte)106, (byte)220, (byte) 55, (byte)200, (byte)108, (byte)193, (byte)171, (byte)250,
        (byte) 36, (byte)225, (byte)123, (byte)  8, (byte) 12, (byte)189, (byte)177, (byte) 74,
        (byte)120, (byte)136, (byte)149, (byte)139, (byte)227, (byte) 99, (byte)232, (byte)109,
        (byte)233, (byte)203, (byte)213, (byte)254, (byte) 59, (byte)  0, (byte) 29, (byte) 57,
        (byte)242, (byte)239, (byte)183, (byte) 14, (byte)102, (byte) 88, (byte)208, (byte)228,
        (byte)166, (byte)119, (byte)114, (byte)248, (byte)235, (byte)117, (byte) 75, (byte) 10,
        (byte) 49, (byte) 68, (byte) 80, (byte)180, (byte)143, (byte)237, (byte) 31, (byte) 26,
        (byte)219, (byte)153, (byte)141, (byte) 51, (byte)159, (byte) 17,(byte) 131, (byte) 20
    };

    /**
     * The constructor of the MD2 class.
     */
    public MD2() {
        super(16);
    }

    public void initiate() {
        Arrays.fill(digest, (byte)0);
        Arrays.fill(checksum, (byte)0);
    }

    public void add(byte b) {
        buff.add(b);
        if (!buff.isFull()) return;
        update();
        buff.reset();
    }

    public void terminate() {
        int bytesToAppend = 16 - (int)(buff.getBytesCount() % 16);
        if (bytesToAppend == 0)
        bytesToAppend = 16;

        int i;
        for (i = 0; i < bytesToAppend; i++)
            pad[i] = (byte)bytesToAppend;

        add(pad, 0, bytesToAppend);
        add(checksum, 0, 16);

        buff.clear();
        Arrays.fill(checksum, (byte)0);
        Arrays.fill(x, (byte)0);
    }

    private void update() {
        int j, k, t;
        byte[] block = buff.getRawBuffer();

        //Form encryption block from state, block, state ^ block.
        for (j = 0; j < 16; j++) {
            x[j +  0] = digest[j];
            x[j + 16] = block[j];
            x[j + 32] = (byte)(digest[j] ^ block[j]);
        }

        //Encrypt block (size+2 rounds).
        t = 0;
        for (j = 0; j <= 17; j++) {
            for (k = 0; k < 48; k++) {
                t = ((x[k] ^ s[t]) & 0xFF);
                x[k] = (byte)t;
            }
            t = (t + j) & 0xFF;
        }

        //Save digest
        for (j = 0; j < 16; j++)
            digest[j] = x[j];

        //Update checksum.
        t = checksum[15];

        for (j = 0; j < 16; j++) {
            checksum[j] ^= s[(block[j] ^ t) & 0xFF];
            t = checksum[j];
        }
    }
}