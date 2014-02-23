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
package net.sf.jautl.rng.sources;

import net.sf.jautl.rng.interfaces.GeneratorsInteger;
import java.util.Random;
import net.sf.jautl.rng.interfaces.AdapterBlockedToBytesSource;
import net.sf.jautl.rng.interfaces.IBytesSource;
import net.sf.jautl.rng.interfaces.IRandomizable;

/**
 * This class wraps the standard java.util.Random calls.
 * <p>This way, you have results comparable with the ones you hand using these 
 * calls directly.
 * <p>Though I have not been able to find documentation about its behaviour,
 * testing performed using Diehard test suite suggest that it is in fact rather
 * good.
 */
public class RandomSourceJavaUtil implements IRandomizable, IBytesSource {
    private Random random;

    /**
     * The constructor. Creates a new instance of Random to use as source
     * of entropy.
     */
    public RandomSourceJavaUtil() {
		this(new Random());
	}

    /**
     * The constructor.
     * @param r the instance of Random to use as source of entropy
     */
	public RandomSourceJavaUtil(Random r) {
		random = r;
	}

	public void factoryDefault() {
		random.setSeed(1);
	}

	public void randomize(byte[] seed) {
		RandomSourceHMAC2104 rs = new RandomSourceHMAC2104();
        AdapterBlockedToBytesSource abtb = new AdapterBlockedToBytesSource(rs);
		rs.randomize(seed);

		random.setSeed(GeneratorsInteger.generate(abtb));
	}

	/**
	 * Return the underlying Random instance.
	 * @return the underlying Random instance
	 */
	public Random getInternalRandom() {
		return random;
	}

    public byte nextByte() {
		return (byte)(random.nextInt() & 0xFF);
    }
}