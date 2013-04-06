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

import java.io.DataOutput;
import java.io.IOException;

/**
 * This class implements a binary writer which can dynamically modify the
 * endianess of output.
 */
public class EndianessAwareBinaryWriter {
    private Endianess endianess;
    private DataOutput out;

    /**
     * The constructor. By default, the endinianess is big (Java default).
     * @param out the DataOutput instance to use for the output
     */
    public EndianessAwareBinaryWriter(DataOutput out) {
        this.endianess = Endianess.BIG_ENDIAN;
        this.out = out;
    }

    /**
     * The constructor.
     * @param out the DataOutput instance to use for the output
     * @param e the endianess to use
     */
    public EndianessAwareBinaryWriter(DataOutput out, Endianess e) {
        this.endianess = e;
        this.out = out;
    }
    
    /**
     * Access the underlying DataOutput instance.
     * @return the output stream
     */
    public DataOutput getOutput() {
		return out;
	}

	/**
     * Return the current endianess.
     * @return the endianess value
     */
    public Endianess getEndianess() {
        return endianess;
    }

    /**
     * Set the endianess to use for future operations.
     * @param endianess the new value of the endianess
     */
    public void setEndianess(Endianess endianess) {
        this.endianess = endianess;
    }
    /**
     * Write a byte.
     * @param i the data to write
     * @throws IOException
     */
	public void writeByte(int i) throws IOException {
		out.write(i);
	}

    /**
     * Write a short.
     * @param i the data to write
     * @throws IOException
     */
	public void writeShort(int i) throws IOException {
        if (endianess == Endianess.SMALL_ENDIAN) {
        	out.write(i); i >>>= 8;
        	out.write(i);
        } else
        	out.writeShort(i);
	}

    /**
     * Write an integer.
     * @param i the data to write
     * @throws IOException
     */
	public void writeInt(int i) throws IOException {
        if (endianess == Endianess.SMALL_ENDIAN) {
        	out.write(i); i >>>= 8;
        	out.write(i); i >>>= 8;
        	out.write(i); i >>>= 8;
        	out.write(i);
        } else
        	out.write(i);
	}

    /**
     * Write a long.
     * @param l the data to write
     * @throws IOException
     */
	public void writeLong(long l) throws IOException {
        if (endianess == Endianess.SMALL_ENDIAN) {
            int i;

            i = (int)(l & 0xFFFFFFFF); writeInt(i); l >>= 32;
            i = (int)(l & 0xFFFFFFFF); writeInt(i);
        } else
        	out.writeLong(l);
	}

    /**
     * Write a float.
     * @param f the data to write
     * @throws IOException
     */
	public void writeFloat(float f) throws IOException {
		writeInt(Float.floatToIntBits(f));
	}

    /**
     * Write a double.
     * @param d the data to write
     * @throws IOException
     */
	public void writeDouble(double d) throws IOException {
		writeLong(Double.doubleToLongBits(d));
	}
}
