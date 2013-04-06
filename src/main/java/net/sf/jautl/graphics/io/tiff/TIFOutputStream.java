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
package net.sf.jautl.graphics.io.tiff;

import java.io.IOException;

import net.sf.jautl.io.EndianessAwareBinaryWriter;

/**
 * This class contains utility methods for writing TIFF files.
 */
public class TIFOutputStream {
	private EndianessAwareBinaryWriter writer;

    /**
     * The constructor.
     * @param eabw the instance of EndianessAwareBinaryWriter to
     *             use when writing
     */
	public TIFOutputStream(EndianessAwareBinaryWriter eabw) {
		this.writer = eabw;
	}

    /**
     * Return the wrapped EndianessAwareBinaryWriter instance.
     * @return the wrapped EndianessAwareBinaryWriter instance
     */
	public EndianessAwareBinaryWriter getWriter() {
		return writer;
	}

    /**
     * Write a SHORT.
     * @param tag the tag of the item
     * @param data the vaue to write
     * @throws IOException
     */
    public void writeSHORT(int tag, int data) throws IOException {
        writeSHORT(tag, 1, data);
    }

    /**
     * Write a SHORT vector
     * @param tag the tag of the item
     * @param count the number of elements
     * @param data
     * @throws IOException
     */
    public void writeSHORT(int tag, int count, int data) throws IOException {
        writer.writeShort(tag);
        writer.writeShort(3);
        writer.writeInt(count);
        writer.writeInt(data);
    }

    /**
     *
     * @param tag
     * @param data
     * @throws IOException
     */
    public void writeLONG(int tag, int data) throws IOException {
        writeLONG(tag, 1, data);
    }

    /**
     *
     * @param tag
     * @param count
     * @param data
     * @throws IOException
     */
    public void writeLONG(int tag, int count, int data) throws IOException {
    	writer.writeShort(tag);
    	writer.writeShort(4);
    	writer.writeInt(count);
    	writer.writeInt(data);
    }
}
