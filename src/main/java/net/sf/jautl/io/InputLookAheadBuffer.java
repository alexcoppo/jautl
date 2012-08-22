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

import java.io.Reader;
import java.io.IOException;

/**
 * This class provides an input buffer with a given lookahead capability.
 * Being able to look ahead for more than 1 character enables to write simpler
 * lexical analyzer with no need of putting back characters.
 */
public class InputLookAheadBuffer {
    /** The class constructor.
     * @param r the Reader to be used to get data
     * @param lookAheadDepth the maximum number of bytes to look ahead using
     * peek() method
     * @param blockReadSize the size of reads requested to the Reader.
     */
    public InputLookAheadBuffer(Reader r, int lookAheadDepth, int blockReadSize) {
        this.r = r;
        buffer = new char[lookAheadDepth + blockReadSize];
        this.blockReadSize = blockReadSize;
        activeAreaStart = 0;
        activeAreaSize = 0;
        reFill();
    }

    /** Peek the first available byte.
     * @return the first available byte
     */
    public final int peek() {
        return peek(0);
    }

    /** Peek a byte at a given offset.
     * @param offset the offset where the sought byte is.
     * @return the available byte at the given offset
     */
    public final int peek(int offset) {
        if (offset < activeAreaSize)
            return (int)buffer[activeAreaStart + offset];
        else
            return -1;
    }

    /** Return the number of available bytes.
     * @return the number of available bytes
     */
    public final int getActiveAreaSize() {
        return activeAreaSize;
    }

    /** Skip a given number of bytes.
     * @param bytes the number of bytes to be skipped.
     */
    public final void skip(int bytes) {
        activeAreaStart += bytes;
        if (activeAreaSize >= bytes)
            activeAreaSize -= bytes;
        else
            activeAreaSize = 0;
        reFill();
    }

    /** Dump the internal state of the class.
     */
    public final void dump() {
        System.out.println("------------------------------------------------------------------");
        System.out.print("*** ");
        for (int i = 0; i < buffer.length; i++) {
            switch (peek(i)) {
            case -1:
                System.out.print("  ");
                break;
            case 10:
                System.out.print("LF");
                break;
            case 13:
                System.out.print("CR");
                break;
            default:
                System.out.print((char)peek(i) + " ");
                break;
            }
            if (((i + 1) % 32) == 0) {
                System.out.println(" $$$");
                System.out.print("*** ");
            }
        }
        System.out.println();
    }

    private void reFill() {
        //Check whether there would be space to read at least a block.
        //If there is be no space to read, it is pointless to waste time...
        if (buffer.length - activeAreaSize < blockReadSize)
            return;

        //Shift active area at start of buffer
        if (activeAreaStart > 0) {
            int dst = 0;
            int src = activeAreaStart;
            int count = activeAreaSize;

            while (count-- > 0)
                buffer[dst++] = buffer[src++];

            activeAreaStart = 0;
        }

        //As long as there is space to read a block...
        while (activeAreaSize + blockReadSize <= buffer.length) {
            //...read it and...
            int readSize = 0;
            try {
                readSize = r.read(buffer, activeAreaSize, blockReadSize);
            } catch (IOException ioe) {
                return;
            }
            if (readSize > 0)
                activeAreaSize += readSize;
            //...go on as long as the stream is not over.
            if (readSize < blockReadSize)
                break;
        }
    }

    private Reader r;
    private char[] buffer;
    private int blockReadSize;
    private int activeAreaStart;
    private int activeAreaSize;
}