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

/**
 * The implementation of the Keyed-Hashing for Message Authentication
 * documented in RFC 2104. For any information, see that RFC.
 * <p>The code has been checked trying it against all the
 * test data provided in the said RFC.
 */
public final class HMAC2104 extends DigestEngine {
	private DigestEngine engine;
	private byte[] IPad = new byte[64];
	private byte[] OPad = new byte[64];

    /**
     * The parameterless constructor. The default digest function
	 * used is SHA1.
	 */
	public HMAC2104() {
		this(new SHA1());
	}

	/**
	 * This constructor version allows to set a given digest engine.
	 * @param engine and appropriate instance of a class derived from DigestEngine.
	 */
	public HMAC2104(DigestEngine engine) {
		super(engine.getSize());
		this.engine = engine;
	}

	/**
	 * Perform the RFC 2104 mandated computations to create the internal
	 * key-dependent paddings.
	 * @param key the byte vector representing the key.
	 */
	public void setupKey(byte[] key) {
		byte[] tmp = null;

		clear();

		if (key.length > 64) {
			engine.initiate();
			engine.add(key, 0, key.length);
			engine.terminate();

			tmp = new byte[engine.getSize()];

			engine.getAsBytes(tmp);

			key = tmp;
		}

		int index;
		for (index = 0; index < key.length; index++) {
			IPad[index] = key[index];
			OPad[index] = key[index];
		}

		for (index = 0; index < 64; index++) {
			IPad[index] ^= (byte)0x36;
			OPad[index] ^= (byte)0x5C;
		}

		if (tmp != null)
		Arrays.fill(tmp, (byte)0);
	}

	@Override
	public void initiate() {
		engine.initiate();
		engine.add(IPad, 0, 64);
	}

	@Override
	public void add(byte b) {
		engine.add(b);
	}

	@Override
	public void terminate() {
		engine.terminate();

		engine.getAsBytes(digest);

		engine.initiate();
		engine.add(OPad, 0, 64);
		engine.add(digest, 0, getSize());
		engine.terminate();

		engine.getAsBytes(digest);
	}

    /**
     * Remove security related information.
     */
	@Override
	public final void clear() {
		Arrays.fill(IPad, (byte)0);
		Arrays.fill(OPad, (byte)0);
	}
}