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
package net.sf.jautl.io;

/**
 * Implements the EndianessConverter interface for Small-End-In ordering.
 * Small Endian means that a number like 0x1234 is written first 0x34 and then
 * 0x12.
 */
public class SmallEndianEndianessConverter implements EndianessConverter {
	/** Insert an 8 bit unsigned integral number.
	 * @param value the value to insert into the buffer
	 * @param buffer the buffer to put the value
	 * @param offset the offset in the buffer (in bytes)
	 */
	public void putU8(byte value, byte[] buffer, int offset) {
		buffer[offset] = value;
	}

	/** Insert a 16 bit signed integral number.
	 * @param value the value to insert into the buffer
	 * @param buffer the buffer to put the value
	 * @param offset the offset in the buffer (in bytes)
	 */
	public void putS16(short value, byte[] buffer, int offset) {
		for (int index = 0; index < SHORT_WIDTH; index++) {
			buffer[offset + index] = (byte) (value & 0xFF);
			value >>=8;
		}
	}

	/** Insert a 32 bit signed integral number.
	 * @param value the value to insert into the buffer
	 * @param buffer the buffer to put the value
	 * @param offset the offset in the buffer (in bytes)
	 */
	public void putS32(int value, byte[] buffer, int offset) {
		for (int index = 0; index < INT_WIDTH; index++) {
			buffer[offset + index] = (byte) (value & 0xFF);
			value >>=8;
		}
	}

	/** Insert a 64 bit signed integral number.
	 * @param value the value to insert into the buffer
	 * @param buffer the buffer to put the value
	 * @param offset the offset in the buffer (in bytes)
	 */
	public void putS64(long value, byte[] buffer, int offset) {
		for (int index = 0; index < LONG_WIDTH; index++) {
			buffer[offset + index] = (byte) (value & 0xFF);
			value >>=8;
		}
	}

	/** Extract an 8 bit unsigned value.
	 * @param buffer the buffer from which get the value
	 * @param offset the offset in the buffer (in bytes)
	 * @return the value extracted from the buffer
	 */
	public byte getU8(byte[] buffer, int offset) {
		return buffer[offset];
	}

	/** Extract a 16 bit signed value.
	 * @param buffer the buffer from which get the value
	 * @param offset the offset in the buffer (in bytes)
	 * @return the value extracted from the buffer
	 */
	public short getS16(byte[] buffer, int offset) {
		short result = 0;

		for (int index = SHORT_WIDTH - 1; index >= 0; index--) {
			result <<= 8;
			result |= ((short)buffer[offset + index] & (short)0xFF);
		}

		return result;
	}

	/** Extract a 32 bit signed value.
	 * @param buffer the buffer from which get the value
	 * @param offset the offset in the buffer (in bytes)
	 * @return the value extracted from the buffer
	 */
	public int getS32(byte[] buffer, int offset) {
		int result = 0;

		for (int index = INT_WIDTH - 1; index >= 0; index--) {
			result <<= 8;
			result |= ((int)buffer[offset + index] & (int)0xFF);
		}

		return result;
	}

	/** Extract a 64 bit signed value.
	 * @param buffer the buffer from which get the value
	 * @param offset the offset in the buffer (in bytes)
	 * @return the value extracted from the buffer
	 */
	public long getS64(byte[] buffer, int offset) {
		long result = 0;

		for (int index = LONG_WIDTH - 1; index >= 0; index--) {
			result <<= 8;
			result |= ((long)buffer[offset + index] & (long)0xFF);
		}

		return result;
	}
}