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
 * This class provides a wrapper on a vector of bytes as a source of
 * "entropy".
 * It is especially useful for testing, as there is full control
 * on each generated "random" byte.
 */
public class RandomSourceDeterministic implements IRandomizable, IBytesSource {
    private byte[] buffer;
	private int nextAvail;

    /**
     * Create a new instance, with a zero memory of given size.
     * @param memorySize the size in byte of the internal data vector
     */
	public RandomSourceDeterministic(int memorySize) {
		buffer = new byte[memorySize];
	}

    /**
     * Create an instance, using a initialization vector
     * @param data the byte vector to use as internal data initializer
     */
	public RandomSourceDeterministic(byte[] data) {
		buffer = data;
	}

	public void factoryDefault() {
		reset();
	}

	public void randomize(byte[] seed) {
		reset();
	}

	/**
	 * Set a byte to a given value.
	 * @param offset the offset in the sequence
	 * @param value the new byte value.
	 */
	public void setByte(int offset, byte value) {
		buffer[offset] = value;
	}

	/**
	 * Reset the position of the next byte to return at 0.
	 */
	public void reset() {
		nextAvail = 0;
	}

    public byte nextByte() {
		byte result = buffer[nextAvail++];
		if (nextAvail == this.buffer.length)
			nextAvail = 0;
		return result;
    }
}