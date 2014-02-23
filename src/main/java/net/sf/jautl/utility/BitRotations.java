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
package net.sf.jautl.utility;

/**
 * This class implements methods to perform bit-level rotations on Java primitives.
 */
public class BitRotations {
	/**
	 * Rotate left a string.
	 * @param s the string to rate
	 * @param places the number of characters to rotate
	 * @return the rotated string
	 */
	public static String left(String s, int places) {
		int originalLength = s.length();
		places = places % originalLength;
		s = s + s;
		int startPos = places;
		return s.substring(startPos, startPos + originalLength);
	}

	/**
	 * Rotate right a string.
	 * @param s the string to rate
	 * @param places the number of characters to rotate
	 * @return the rotated string
	 */
	public static String right(String s, int places) {
		int originalLength = s.length();
		places = places % originalLength;
		s = s + s;
		int startPos = originalLength - places;
		return s.substring(startPos, startPos + originalLength);
	}

    /**
     * Rotate left a byte.
     * @param value the value to be rotated
     * @param count the number of bits to rotate
     * @return the rotated value
     */
	public static byte left(byte value, int count) {
		count = count & 7;

        return (byte)((value << count) | (value >>> (8 - count)));
	}

    /**
     * Rotate right a byte.
     * @param value the value to be rotated
     * @param count the number of bits to rotate
     * @return the rotated value
     */
    public static byte right(byte value, int count) {
    	count = count & 7;

        return (byte)((value >>> count) | (value << (8 - count)));
    }

    /**
     * Rotate left a short.
     * @param value the value to be rotated
     * @param count the number of bits to rotate
     * @return the rotated value
     */
    public static short left(short value, int count) {
    	count = count & 15;

        return (short)((value << count) | (value >>> (16 - count)));
	}

    /**
     * Rotate right a short.
     * @param value the value to be rotated
     * @param count the number of bits to rotate
     * @return the rotated value
     */
    public static short right(short value, int count) {
    	count = count & 15;

        return (short)((value >>> count) | (value << (16 - count)));
    }

    /**
     * Rotate left an int.
     * @param value the value to be rotated
     * @param count the number of bits to rotate
     * @return the rotated value
     */
    public static int left(int value, int count) {
    	count = count & 31;

	    return (value << count) | (value >>> (32 - count));
	}

    /**
     * Rotate right an int.
     * @param value the value to be rotated
     * @param count the number of bits to rotate
     * @return the rotated value
     */
    public static int right(int value, int count) {
    	count = count & 31;

        return (value >>> count) | (value << (32 - count));
	}

    /**
     * Rotate left a long.
     * @param value the value to be rotated
     * @param count the number of bits to rotate
     * @return the rotated value
     */
    public static long left(long value, int count) {
    	count = count & 63;

	    return (value << count) | (value >>> (64 - count));
	}

    /**
     * Rotate right a long.
     * @param value the value to be rotated
     * @param count the number of bits to rotate
     * @return the rotated value
     */
    public static long right(long value, int count) {
    	count = count & 63;

        return (value >>> count) | (value << (64 - count));
    }
}
