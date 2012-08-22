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
package net.sf.jautl.rng.sources;

import net.sf.jautl.rng.interfaces.IBytesSource;
import net.sf.jautl.rng.interfaces.IRandomizable;

/**
 * This class create random bytes using the 'Alleged RC4 algorithm'.
 */
public class RandomSourceRC4 implements IRandomizable, IBytesSource {
	private byte[] state = new byte[256];
    private int x;
	private int y;

    public void factoryDefault() {
		byte[] seed = {(byte)'1', (byte)'q', (byte)'~', (byte)'W'};

		randomize(seed);
	}

	public void randomize(byte[] seed) {
		int counter;

		for (counter = 0; counter < 256; counter++)
		    state[counter] = (byte)counter;

		x = 0;
		y = 0;

		int index1 = 0;
		int index2 = 0;
		byte tmp;

		for (counter = 0; counter < 256; counter++) {
		    index2 = (seed[index1] + state[counter] + index2) & 0xff;

			tmp = state[counter];
	    	state[counter] = state[index2];
		    state[index2] = tmp;

			index1++;
			if (index1 >= seed.length)
				index1 = 0;
	   	}
	}

	public byte nextByte() {
		x = (x + 1) & 0xff;
		byte sx = state[x];

		y = (y + state[x]) & 0xff;
		byte sy = state[y];

		byte tmp = state[x];
		state[x] = state[y];
		state[y] = tmp;

		return state[(sx + sy) & 0xff];
	}
}