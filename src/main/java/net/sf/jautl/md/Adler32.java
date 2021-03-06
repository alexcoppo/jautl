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

/**
 * An adapter of the Adler-32 hash algorithm to the DigestEngine interface. 
 */
public final class Adler32 extends DigestEngine {
	private java.util.zip.Adler32 m_Engine = new java.util.zip.Adler32();

	/**
	 * The constructor.
	 */
    public Adler32() {
		super(4);
	}

	@Override
	public void initiate() {
		m_Engine.reset();
	}

    /**
     * Add a block of data to the currently processed.
     * @param data the data to be added
     * @param offset the offset in the vector from which start reading
     * @param length the number of bytes to process
     */
	@Override
	public void add(byte[] data, int offset, int length) {
		m_Engine.update(data, offset, length);
	}

	@Override
    public void add(byte b) {
        byte[] bytes = new byte[1];
        bytes[0] = b;
        add(bytes);
    }

	@Override
	public void terminate() {
		long hash = m_Engine.getValue();
        
		digest[0] = (byte)((hash & 0x000000FF)       );
        digest[1] = (byte)((hash & 0x0000FF00) >>>  8);
        digest[2] = (byte)((hash & 0x00FF0000) >>> 16);
        digest[3] = (byte)((hash & 0xFF000000) >>> 24);
	}
}