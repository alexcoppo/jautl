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

import java.security.SecureRandom;
import net.sf.jautl.rng.interfaces.IBytesSource;
import net.sf.jautl.rng.interfaces.IIntegersSource;
import net.sf.jautl.rng.interfaces.IRandomizable;

/**
 * This class creates random bytes by wrapping calls to java.security.SecureRandom.
 */
public class RandomSourceSecuritySecure implements IRandomizable, IBytesSource, IIntegersSource {
    private SecureRandom sr;

    /**
     * The constructor. Creates a new SecureRandom instance. 
     */
    public RandomSourceSecuritySecure() {
		this(new SecureRandom());
	}

    /**
     * The constructor.
     * @param sr the java.security.SecureRandom instance to use as source
     * of entropy
     */
    public RandomSourceSecuritySecure(SecureRandom sr) {
		this.sr = sr;
	}

	public final void factoryDefault() {
		byte[] seed = {(byte)'1', (byte)'q', (byte)'~', (byte)'W'};

		randomize(seed);
	}

	public final void randomize(byte[] seed) {
		sr.setSeed(seed);
	}

	/**
	 * Return the associated java.security.SecureRandom instance.
	 * @return the associated java.security.SecureRandom instance
	 */
	public final SecureRandom getInternalRandom() {
		return sr;
	}

    public final byte nextByte() {
		return (byte)(sr.nextInt() & 0xFF);
	}

    public int nextInt() {
 		return sr.nextInt();
   }
}