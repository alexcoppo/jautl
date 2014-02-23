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
package net.sf.jautl.rng.interfaces;

/**
 * This class contains utility methods for generating bytes.
 */
public class GeneratorsByte {
	/**
	 * Fill a block with random bytes.
     * @param ibs the IBytesSource to use as entropy source
	 * @param buffer the buffer to be filled
	 * @param offset the first position to be filled
	 * @param length how many bytes to fill
	 */
	public static void generate(IBytesSource ibs, byte[] buffer, int offset, int length) {
		while (length-- > 0)
			buffer[offset++] = ibs.nextByte();
	}

	/**
	 * Fill a block with random bytes.
     * @param ibs the IBytesSource to use as entropy source
	 * @param buffer the buffer to be filled
	 */
	public static void generate(IBytesSource ibs, byte[] buffer) {
		generate(ibs, buffer, 0, buffer.length);
	}
}
