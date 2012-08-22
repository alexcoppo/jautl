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
package net.sf.jautl.rng.interfaces;

/**
 * This class is the base of all random sources which create entropy 
 * in fixed size blocks. It adapts the one-block-a-time behaviour of
 * these generators to the base one-byte-a-time behaviour.
 */
public class AdapterBlockedToBytesSource implements IBytesSource {
    private IBytesBlockSource ibbs;
	private int nextAvail;
	private byte[] buffer;

	/**
	 * The constructor.
	 * @param ibbs the IBytesBlockSource implementation  to use as source of entropy
	 */
	public AdapterBlockedToBytesSource(IBytesBlockSource ibbs) {
        this.ibbs = ibbs;
		this.buffer = new byte[ibbs.blockSize()];
		this.nextAvail = ibbs.blockSize();
	}

	public byte nextByte() {
		if (nextAvail == buffer.length) {
			ibbs.nextBlock(buffer);
			nextAvail = 0;
		}

		return buffer[nextAvail++];
	}
}