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
 * This class is the root of every hashing function class. It defines the
 * procotol common to all the hashing function, implements some utility
 * functions and provides some information about the actual hashing function.
 * <p>Message digesting is a three phase process:
 * <ol>
 * <li>starting the engine using the initiate method;
 * <li>adding data to be hashed, using the various forms of add method;
 * <li>terminating the process calling the terminate method.
 * </ol>
 * After this sequence, the digest can be read as long as required.
 */
public abstract class DigestEngine {
    /**
     * The buffer which contains the computed digest.
     */
    protected byte[] digest;

    /**
     * The constructor. It requires, in order to correctly dimension digest,
     * the size of the digest.
     * @param size the size in bytes of the digest.
     */
    protected DigestEngine(int size) {
        digest = new byte[size];
    }

    /**
     * The size of this digest.
     * @return the size in bytes of the digest.
     */
    public int getSize() {
        return digest.length;
    }

    /**
     * Gets a given byte of the digest.
     * @param index the index of the byte to be returned
     * ranging from <code>0</code> to <code>getSize() - 1</code>.
     * @return the sought for byte
     */
    public final byte getByte(int index) {
        return digest[index];
    }

    /**
     * Gets one or more bytes from the initial segment of the
     * digest. If the buffer passed to this routine is longer
     * than the digest, the remaining bytes are zeroed.
     * @param result the byte array to be filled.
     */
    public final void getAsBytes(byte[] result) {
        for (int i = 0; i < result.length; i++)
            if (i < getSize())
                result[i] = getByte(i);
            else
                result[i] = (byte)0;
    }

    /**
     * Returns the digest as an hexadecimal uppercase string.
     * @return the digest as an hexadecimal uppercase string
     */
    public final String getAsHex() {
        char[] nibbleToHex = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

        int size = getSize();
        StringBuilder buffer = new StringBuilder(2 * size);

        for (int index = 0; index < size; index++) {
            byte b = getByte(index);

            buffer.append(nibbleToHex[(byte)((b & 0xF0) >>> 4)]);
            buffer.append(nibbleToHex[(byte) (b & 0x0F)       ]);
        }

        return buffer.toString();
    }

    /**
     * Setup the engine for message digest computation.
     */
    public abstract void initiate();

    /**
     * Add a block of data to the currently processed.
     * @param data the data to be added
     * @param offset the offset in the vector from which start reading
     * @param length the number of bytes to process
     */
    public void add(byte[] data, int offset, int length) {
		while (length > 0) {
			add(data[offset++]);
			length--;
		}
    }

    /**
     * Add a byte to the message.
     * @param b the byte to add
     */
    public abstract void add(byte b);

    /**
     * Helper overload of add.
     * @param data the block of bytes to be added.
     */
    public final void add(byte[] data) {
        add(data, 0, data.length);
    }

    /**
     * Helper overload of add.
     * @param s the String to be converted in a byte array and added.
     */
    public final void add(String s) {
        byte[] data = s.getBytes();
        add(data, 0, data.length);
    }

    /**
     * Perform final computations to determined the digest value.
     */
    public abstract void terminate();

    /**
     * Remove security related information.
     * It is possible that the engine contains security sensitive
     * information which might be spied externally. In this case,
     * this method must be appropriately redefined in order to clear
     * them.
     */
    public void clear() {
    }
}