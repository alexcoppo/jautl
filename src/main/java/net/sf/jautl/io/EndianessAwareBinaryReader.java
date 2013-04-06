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
package net.sf.jautl.io;

import java.io.DataInput;
import java.io.IOException;

/**
 * This class implements a binary reader which can dynamically changed the
 * endianess of the data.
 */
public class EndianessAwareBinaryReader {
    private Endianess endianess;
    private DataInput in;

    /**
     * The constructor. The endianess is initialized to Java default (big endian).
     * @param in the DataInput instance to use as source of data
     */
    public EndianessAwareBinaryReader(DataInput in) {
    	this(in, Endianess.BIG_ENDIAN);
    }

    /**
     * The constructor.
     * @param in the DataInput instance to use as source of data
     * @param e the endianess of data
     */
    public EndianessAwareBinaryReader(DataInput in, Endianess e) {
        this.endianess = e;
        this.in = in;
    }
    
    /**
     * Return the associated DataInput stream.
     * @return the stream
     */
    public DataInput getInput() {
		return in;
	}

    /**
     * Return the endianess of the conversion.
     * @return the current endianess
     */
    public Endianess getEndianess() {
        return endianess;
    }

    /**
     * Set the endianess of the conversion.
     * @param endianess the new endianess value
     */
    public void setEndianess(Endianess endianess) {
        this.endianess = endianess;
    }

    /**
     * Read a byte.
     * @return the read value
     * @throws IOException
     */
	public byte readByte() throws IOException {
		return in.readByte();
	}

    /**
     * Read a short.
     * @return the read value
     * @throws IOException
     */
	public short readShort() throws IOException {
        if (endianess == Endianess.SMALL_ENDIAN) {
            short s0 = (short)(((short)in.readByte()) & 0xFF);
            short s1 = (short)(((short)in.readByte()) & 0xFF);
    		return (short)((s1 << 8) | s0);
        } else
            return in.readShort();
	}

    /**
     * Read an int.
     * @return the read value
     * @throws IOException
     */
	public int readInt() throws IOException {
        if (endianess == Endianess.SMALL_ENDIAN) {
            int i0 = (int)in.readByte() & 0xFF;
            int i1 = (int)in.readByte() & 0xFF;
            int i2 = (int)in.readByte() & 0xFF;
            int i3 = (int)in.readByte() & 0xFF;
            return (i3 << 24) | (i2 << 16) | (i1 << 8) | i0;
        } else
            return in.readInt();
	}

    /**
     * Read a long. 
     * @return the read value
     * @throws IOException
     */
	public long readLong() throws IOException {
        if (endianess == Endianess.SMALL_ENDIAN) {
            long l0 = (long)readInt() & 0xFFFFFFFFL;
            long l1 = (long)readInt() & 0xFFFFFFFFL;
            return (l1 << 32) | l0;
        } else
            return in.readLong();
	}

    /**
     * Read a float.
     * @return the read value
     * @throws IOException
     */
	public float readFloat() throws IOException {
		int i = readInt();
		return Float.intBitsToFloat(i);
	}

    /**
     * Read a double.
     * @return the read value
     * @throws IOException
     */
	public double readDouble() throws IOException {
		long l = readLong();
		return Double.longBitsToDouble(l);
	}
}
