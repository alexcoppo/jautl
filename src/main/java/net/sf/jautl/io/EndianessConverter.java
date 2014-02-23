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
package net.sf.jautl.io;

/**
 * This interface describes the generic tool to convert values into byte streams
 * and vice versa. It must be implemented appropriately for different byte orders.
 */
public interface EndianessConverter {
	/** The size in bytes of a byte. */
	int BYTE_WIDTH = 1;
	/** The size in bytes of a short */
	int SHORT_WIDTH = 2;
	/** The size in bytes of an int */
	int INT_WIDTH = 4;
	/** The size in bytes of a long */
	int LONG_WIDTH = 8;

	/** Convert a byte into a byte stream.
	* @param value the value to be converted
	* @param buffer the byte stream buffer
	* @param offset the location from which store bytes
	*/
	void putU8(byte value, byte[] buffer, int offset);

	/** Convert a short into a byte stream.
	* @param value the value to be converted
	* @param buffer the byte stream buffer
	* @param offset the location from which store bytes
	*/
	void putS16(short value, byte[] buffer, int offset);

	/** Convert an int into a byte stream.
	* @param value the value to be converted
	* @param buffer the byte stream buffer
	* @param offset the location from which store bytes
	*/
	void putS32(int value, byte[] buffer, int offset);

	/** Convert a long into a byte stream.
	* @param value the value to be converted
	* @param buffer the byte stream buffer
	* @param offset the location from which store bytes
	*/
	void putS64(long value, byte[] buffer, int offset);

	/** Convert a byte stream into a byte.
	 * @param buffer the byte stream buffer
	 * @param offset the location from which load bytes
	 * @return the extracted value
	 */
	byte getU8(byte[] buffer, int offset);

	/** Convert a byte stream into a short.
	 * @param buffer the byte stream buffer
	 * @param offset the location from which load bytes
	 * @return the extracted value
	 */
	short getS16(byte[] buffer, int offset);

	/** Convert a byte stream into an int.
	 * @param buffer the byte stream buffer
	 * @param offset the location from which load bytes
	 * @return the extracted value
	 */
	int getS32(byte[] buffer, int offset);

	/** Convert a byte stream into a long.
	 * @param buffer the byte stream buffer
	 * @param offset the location from which load bytes
	 * @return the extracted value
	 */
	long getS64(byte[] buffer, int offset);
}