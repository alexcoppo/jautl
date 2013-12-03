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
/*
 * (c)2013 Forward Computing and Control Pty. Ltd. (www.forward.com.au)<br>
 * This code may be freely used for both private and commercial use.<br>
 * Provide this copyright is maintained.<br>
 */
package net.sf.jautl.md;

import net.sf.jautl.utility.BitRotations;

public class SipHash_2_4 extends DigestEngine {
	private byte[] key;
	private long v0;
	private long v1;
	private long v2;
	private long v3;

	private byte messageByteCounter; // hold number of msg bytes % 256
	private int accumulatedBytes; // counter of bytes being accumulated
	private long m; // long to accumulate bytes in until we have 8

	public SipHash_2_4() {
		super(8);

		//from hexadecimal pi representation
		this.key = new byte[] {
			(byte)0x32, (byte)0x43, (byte)0xF6, (byte)0xA8,
			(byte)0x88, (byte)0x5A, (byte)0x30, (byte)0x8D,
			(byte)0x31, (byte)0x31, (byte)0x98, (byte)0xA2,
			(byte)0xE0, (byte)0x37, (byte)0x07, (byte)0x34
		};
	}

	public SipHash_2_4(byte[] key) {
		super(8);

		this.key = normalize(key);
	}

	private byte[] normalize(byte[] seed) {
		if (seed.length == 16)
			return seed;
		
		byte[] result = new byte[16];
		
		for (int index = 0; index < 16; index++)
			result[index] = seed[index % seed.length];	//correct both of longer and shorter than 16
		
		return result;
	}
	
	@Override
	public void initiate() {
		v0 = 0x736f6d6570736575L;
		v1 = 0x646f72616e646f6dL;
		v2 = 0x6c7967656e657261L;
		v3 = 0x7465646279746573L;

		long k0 = bytesLEtoLong(key, 0);
		long k1 = bytesLEtoLong(key, 8);

		v0 ^= k0;
		v1 ^= k1;
		v2 ^= k0;
		v3 ^= k1;
		
		accumulatedBytes = 0;
		messageByteCounter = 0;
		m = 0;
	}

	private static long bytesLEtoLong(byte[] b, int offset) {
		if ((b.length - offset) < 8)
			throw new IllegalArgumentException("Less then 8 bytes starting from offset:" + offset);

		long m = 0;

		for (int i = 0; i < 8; i++)
			m |= ((((long) b[i + offset]) & 0xff) << (8 * i));

		return m;
	}
	
	@Override
	public void add(byte b) {
		messageByteCounter++;
		
		m |= (((long) b & 0xff) << (accumulatedBytes * 8));
		
		accumulatedBytes++;
		
		if (accumulatedBytes >= 8) {
			v3 ^= m;
			siphashRound();
			siphashRound();
			v0 ^= m;

			accumulatedBytes = 0;
			m = 0;
		}
	}

	@Override
	public void terminate() {
		byte msgLenMod256 = messageByteCounter;

		// padd out the last long with zeros
		// leave one space for the message length % 256
		while (accumulatedBytes < 7)
			add((byte) 0);

		// add the message length
		// this will force the last long to be added to the hash
		add(msgLenMod256);

		v2 ^= 0xff;

		siphashRound();
		siphashRound();
		siphashRound();
		siphashRound();
		
		v0 = v0 ^ v1 ^ v2 ^ v3;
		
        digest[0] = (byte)((v0 & 0xFF00000000000000L) >>> 56);
        digest[1] = (byte)((v0 & 0x00FF000000000000L) >>> 48);
        digest[2] = (byte)((v0 & 0x0000FF0000000000L) >>> 40);
		digest[3] = (byte)((v0 & 0x000000FF00000000L) >>> 32);
        digest[4] = (byte)((v0 & 0x00000000FF000000L) >>> 24);
        digest[5] = (byte)((v0 & 0x0000000000FF0000L) >>> 16);
        digest[6] = (byte)((v0 & 0x000000000000FF00L) >>>  8);
		digest[7] = (byte)((v0 & 0x00000000000000FFL)       );
	}

	private void siphashRound() {
		v0 += v1;
		v2 += v3;
		v1 = BitRotations.left(v1, 13);
		v3 = BitRotations.left(v3, 16);

		v1 ^= v0;
		v3 ^= v2;
		v0 = BitRotations.left(v0, 32);

		v2 += v1;
		v0 += v3;
		v1 = BitRotations.left(v1, 17);
		v3 = BitRotations.left(v3, 21);

		v1 ^= v2;
		v3 ^= v0;
		v2 = BitRotations.left(v2, 32);
	}
}
