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
package net.sf.jautl.md;

import net.sf.jautl.utility.BitRotations;

/**
 * The Murmur-3 hashing algorithm.
 */
public final class Murmur3_32 extends DigestEngine {
	private int m_H1;
    private int m_Size;
    private int m_Count;
    private int m_Tail;
	private final int C1 = 0xcc9e2d51;
	private final int C2 = 0x1b873593;

	/**
	 * The constructor.
	 */
    public Murmur3_32() {
		super(4);
	}

	public void initiate() {
		m_Size = 0;
		m_Count = 0;
		m_Tail = 0;
		m_H1 = 0x1;	//SEED
	}

    public void add(byte b) {
    	m_Size++;
        m_Tail |= (int)b << (m_Count * 8);
        m_Count++;
        if (m_Count == 4) {
            doBlock();
        	m_Tail = 0;
            m_Count = 0;
        }
    }

    private void doBlock() {
		m_Tail *= C1;
		m_Tail = BitRotations.left(m_Tail, 15);
		m_Tail *= C2;

		m_H1 ^= m_Tail;
		m_H1 = BitRotations.left(m_H1, 13); 
		m_H1 = m_H1 * 5 + 0xe6546b64;
    }

	public final void terminate() {
		m_Tail *= C1;
		m_Tail = BitRotations.left(m_Tail, 16);
		m_Tail *= C2;
		
		m_H1 ^= m_Tail;
    	m_H1 ^= m_Size;
    	m_H1 = fmix(m_H1);

    	digest[0] = (byte)((m_H1 & 0x000000FF)       );
        digest[1] = (byte)((m_H1 & 0x0000FF00) >>>  8);
        digest[2] = (byte)((m_H1 & 0x00FF0000) >>> 16);
        digest[3] = (byte)((m_H1 & 0xFF000000) >>> 24);
	}

	private static int fmix(int h) {
		h ^= h >>> 16;
		h *= 0x85ebca6b;
		h ^= h >>> 13;
		h *= 0xc2b2ae35;
		h ^= h >>> 16;

		return h;
	}
}
