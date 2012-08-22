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

/**
 * This class contains utility function to compute non-cryptographic 
 * hashes on 32 bit unsigned integers.
 */
public class UintHashUtilities {
	/**
	 * 
	 * @param value
	 * @return
	 */
    public static int wang(int value) {
        value = ~value + (value << 15);   // key = (key << 15) - key - 1;
        value = value ^ (value >>> 12);
        value = value + (value << 2);
        value = value ^ (value >>> 4);
        value = value * 2057;          // key = (key + (key << 3)) + (key << 11);
        value = value ^ (value >>> 16);

        return value;
    }

    /**
     * 
     * @param value
     * @return
     */
    public static int jenkins(int value) {
        value = (value + 0x7ed55d16) + (value << 12);
        value = (value ^ 0xc761c23c) ^ (value >>> 19);
        value = (value + 0x165667b1) + (value << 5);
        value = (value + 0xd3a2646c) ^ (value << 9);
        value = (value + 0xfd7046c5) + (value << 3);
        value = (value ^ 0xb55a4f09) ^ (value >>> 16);

        return value;
    }

    /**
     * 
     * @param value
     * @return
     */
    public static int ward(int value) {
        value = (value << 13) ^ value;
        value = (value * (value * value * 15731 + 789221) + 1376312589) & 0x7FFFFFFF;

        return value;
    }

    /**
     * 
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static int mix(int a, int b, int c) {
        a = a - b; a = a - c; a = a ^ (c >>> 13);
        b = b - c; b = b - a; b = b ^ (a << 8);
        c = c - a; c = c - b; c = c ^ (b >>> 13);
        a = a - b; a = a - c; a = a ^ (c >>> 12);
        b = b - c; b = b - a; b = b ^ (a << 16);
        c = c - a; c = c - b; c = c ^ (b >>> 5);
        a = a - b; a = a - c; a = a ^ (c >>> 3);
        b = b - c; b = b - a; b = b ^ (a << 10);
        c = c - a; c = c - b; c = c ^ (b >>> 15);

        return c;
    }

    /**
     * 
     * @param value
     * @param salt
     * @return
     */
    public static int murmur2(int value, int salt) {
        final int M = 0x5bd1e995;
        final int R = 24;

        int hash = salt;

        value *= M;
        value ^= value >>> R;
        value *= M;

        hash *= M;
        hash ^= value;

        hash ^= hash >>> 13;
        hash *= M;
        hash ^= hash >>> 15;

        return hash;
    }

    /**
     * 
     * @param value
     * @return
     */
	public static int murmur3fmix(int value) {
		value ^= value >>> 16;
		value *= 0x85ebca6b;
		value ^= value >>> 13;
		value *= 0xc2b2ae35;
		value ^= value >>> 16;

		return value;
	}
	
	/*
	 * hash *= CONST;
	 * hash ^= CONST;
	 * hash += CONST;
	 * 
	 * hash  = BitRotations.left(hash, CONST);
	 * hash ^= BitRotations.left(hash, CONST);
	 * hash += BitRotations.left(hash, CONST);
	 */

	/*
	 * mix = a + b
	 * mix = a ^ b
	 */
}
