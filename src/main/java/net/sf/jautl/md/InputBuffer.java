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

import java.util.Arrays;

/**
 * This class is used internally by hashing algorithms as byte/bit counting
 * engine.
 * The hashing algorithms usually require, in addition to message data, the
 * total number of processed bytes inside the given message. Unless it is
 * necessary to write a new hashing function requiring MD strengthening, it
 * is not necessary to understand the behaviour of this class.
 */
public final class InputBuffer {
    private byte[] buffer;
    private int firstFree;
    private long count;

    /**
     * Constructs an InputBuffer capable of holding a given number of bytes.
     * @param size the number of bytes to buffered.
     */
    public InputBuffer(int size) {
        buffer = new byte[size];
        firstFree = 0;
        count = 0;
    }

    /**
     * Checks whether the buffer is full.
     * @return whether the buffer is full
     */
    public boolean isFull() {
        return firstFree == buffer.length;
    }

    /**
     * Returns the buffer content as a byte vector.
     * @return a reference to the internal buffer.
     */
    public byte[] getRawBuffer() {
        return buffer;
    }

    /**
     * Return 32 bits in small endian ordering.
     * @param index the index (in terms of 4 bytes words)
     * of the 32 bit block to be returned
     * @return 32 bits in small endian ordering
     */
    public int getInt32SmallEndianAt(int index) {
        index *= 4;

        int result = 0;

        result |= ((int)buffer[index + 3] & 0xFF) << 24;
        result |= ((int)buffer[index + 2] & 0xFF) << 16;
        result |= ((int)buffer[index + 1] & 0xFF) <<  8;
        result |= ((int)buffer[index + 0] & 0xFF);

        return result;
    }

    /**
     * Return 32 bits in big endian ordering.
     * @param index the index (in terms of 4 bytes words)
     * of the 32 bit block to be returned
     * @return 32 bits in big endian ordering
     */
    public int getInt32BigEndianAt(int index) {
        index *= 4;

        int result = 0;

        result |= ((int)buffer[index + 0] & 0xFF) << 24;
        result |= ((int)buffer[index + 1] & 0xFF) << 16;
        result |= ((int)buffer[index + 2] & 0xFF) <<  8;
        result |= ((int)buffer[index + 3] & 0xFF);

        return result;
    }

    /**
     * Return 64 bits in big endian ordering.
     * @param index the index (in terms of 8 bytes words)
     * of the 64 bit block to be returned
     * @return 64 bits in big endian ordering
     */
    public long getInt64BigEndianAt(int index) {
        index *= 8;

        long result = 0;

        result |= ((long)buffer[index + 0] & 0xFF) << 56;
        result |= ((long)buffer[index + 1] & 0xFF) << 48;
        result |= ((long)buffer[index + 2] & 0xFF) << 40;
        result |= ((long)buffer[index + 3] & 0xFF) << 32;
        result |= ((long)buffer[index + 4] & 0xFF) << 24;
        result |= ((long)buffer[index + 5] & 0xFF) << 16;
        result |= ((long)buffer[index + 6] & 0xFF) <<  8;
        result |= ((long)buffer[index + 7] & 0xFF);

        return result;
    }

    /**
     * Resets the buffer byte index to 0. To be called
     * after having consumed the buffer contentes.
     */
    public void reset() {
        firstFree = 0;
    }

    /**
     * Erase any security sensitive information.
     */
    public void clear() {
        reset();
        count = 0;
        Arrays.fill(buffer, (byte)0);
    }

    /**
     * Append a byte to the buffer.
     * @param b the byte to be appended.
     */
    public void add(byte b) {
        buffer[firstFree++] = b;
        count++;
    }

    /**
     * Returns the total number of processed bytes.
     * @return the total number of processed bytes
     */
    public long getBytesCount() {
        return count;
    }

    /**
     * Return the 32 least significant bits of the processed bits count.
     * @return the 32 least significant bits of the processed bits count
     */
    public long getLoBitsCount() {
        return (count << 3) & 0xFFFFFFFFL;
    }

    /**
     * Return the 32 most significant bits of the processed bits count.
     * @return the 32 most significant bits of the processed bits count
     */
    public long getHiBitsCount() {
        return (count >> 29) & 0xFFFFFFFFL; 
    }

    /**
     * Return the 32 least significant bits of the processed bytes count.
     * @return the 32 least significant bits of the processed bytes count
     */
    public int getLoBytesCount() {
        return (int)(count & 0xFFFFFFFFL);
    }
}