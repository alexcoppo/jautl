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

/**
 * The implementation of the Murmur2A algorithm.
 *   
 * This class implements the
 * <A href="http://sites.google.com/site/murmurhash/">Murmur2A</A>
 * hashing algorithm.
 * 
 * The code has been checked trying it against test data obtained from
 * the C reference implementation.
 */
public final class Murmur2A extends DigestEngine {
    private final int M = 0x5bd1e995;
    private final int R = 24;
    private int m_Hash;
    private int m_Tail;
    private int m_Count;
    private int m_Size;

    /**
     * The constructor.
     */
    public Murmur2A() {
		super(4);
	}

	public void initiate() {
		m_Hash = 0;
		m_Tail = 0;
		m_Count = 0;
		m_Size = 0;
	}

    public void add(byte b) {
    	m_Size++;
        m_Tail |= (int)b << (m_Count * 8);
        m_Count++;
        if (m_Count == 4) {
            mmix(m_Tail);
            m_Tail = 0;
            m_Count = 0;
        }
    }

	public void terminate() {
        mmix(m_Tail);
        mmix(m_Size);

        m_Hash ^= m_Hash >>> 13;
        m_Hash *= M;
        m_Hash ^= m_Hash >>> 15;

        digest[0] = (byte)((m_Hash & 0x000000FF)       );
        digest[1] = (byte)((m_Hash & 0x0000FF00) >>>  8);
        digest[2] = (byte)((m_Hash & 0x00FF0000) >>> 16);
        digest[3] = (byte)((m_Hash & 0xFF000000) >>> 24);
	}

    private void mmix(int K) {
        K *= M;
        K ^= K >>> R;
        K *= M;
        m_Hash *= M;
        m_Hash ^= K;
    }
}
