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
package net.sf.jautl.rng.sources;

import net.sf.jautl.rng.interfaces.GeneratorsByte;
import java.util.Arrays;
import net.sf.jautl.md.*;
import net.sf.jautl.rng.interfaces.AdapterBlockedToBytesSource;
import net.sf.jautl.rng.interfaces.IBytesBlockSource;
import net.sf.jautl.rng.interfaces.IRandomizable;

/**
 * This class creates random bytes using a homegrown (but succesfully DieHard 
 * tested) algorithm using a generica message digest engine. The result should
 * be enough but for the most demanding applications (though it is significantly
 * slower than other algorithms).
 */
public class RandomSourceMD implements IRandomizable, IBytesBlockSource {
	private DigestEngine md;
	private int digestSize;
	private int ivUpdateFrequency;
	private int blockCount;
	private byte[] iv;
	private byte[] counter = new byte[16];

	/**
	 * The constructor. Uses an SHA-1 engine as source of entropy.
	 */
	public RandomSourceMD() {
		this(new SHA1());
	}

	/**
	 * The constructor.
	 * @param md the DigestEngine implementation to use as source of entropy
	 */
    public RandomSourceMD(DigestEngine md) {
		this.md = md;
		digestSize = md.getSize();
		iv = new byte[digestSize];
		blockCount = 0;
		ivUpdateFrequency = 20;

		Arrays.fill(counter, (byte)0);
	}

	public void factoryDefault() {
		byte[] seed = {(byte)'1', (byte)'q', (byte)'~', (byte)'W'};

		randomize(seed);
	}

	public void randomize(byte[] seed) {
		RandomSourceHMAC2104 rs = new RandomSourceHMAC2104();
        AdapterBlockedToBytesSource abtb = new AdapterBlockedToBytesSource(rs);
		rs.randomize(seed);

        GeneratorsByte.generate(abtb, iv);
        GeneratorsByte.generate(abtb, counter);

        blockCount = 0;
    }

	/**
	 * Set the number of blocks to generate before creating a new Initial Vector.
	 * @param value the number of blocks
	 */
	public void setUIVUpdateFrequency(int value) {
		ivUpdateFrequency = value;
	}

	/**
	 * Return the number of blocks to generate before creating a new Initial Vector.
	 * @return the value of the Initial Vector frequency
	 */
	public int getIVUpdateFrequency() {
		return ivUpdateFrequency;
	}

    public int blockSize() {
        return md.getSize();
    }

    public void nextBlock(byte[] block) {
		if (blockCount == ivUpdateFrequency) {
			md.initiate();
			md.add(iv);
			md.terminate();
			md.getAsBytes(iv);
			blockCount = 0;
		} else
			blockCount++;

		md.initiate();
		md.add(iv);
		md.add(counter);
		md.terminate();
		md.getAsBytes(block);

		int carry = 1;

		for (int i = 0; i < counter.length; i++) {
			int result = (int)(counter[i] & 0xFF) + carry;

			counter[i] = (byte)(result & 0xFF);
			carry = (result > 255) ? 1 : 0;
			if (carry == 0)
				break;
		}
	}
}