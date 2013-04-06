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
package net.sf.jautl.graphics.io.bmp;

import java.io.IOException;

import net.sf.jautl.io.EndianessAwareBinaryReader;
import net.sf.jautl.io.EndianessAwareBinaryWriter;

/**
 * This class encapsulates the processing required to handle BMP file headers.
 */
public class BMPHeader {
	private int width;
	private int height;
    private int logicalRowSize;
    private int physicalRowSize;
	
    /**
     * The constructor.
     */
	public BMPHeader() {
	}

	/**
	 * The constructor.
	 * @param width the width in pixels of the image
	 * @param height the height in pixels of the image
	 */
	public BMPHeader(int width, int height) {
		setWidth(width);
		this.height = height;
	}
	
	/**
	 * Get the width of the image.
	 * @return the width in pixels
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;

		logicalRowSize = 3 * width;

        physicalRowSize = logicalRowSize;

        while ((physicalRowSize & 0x3) != 0)
            physicalRowSize++;
	}
	
	/**
	 * Get the height of the image.
	 * @return the height in pixels
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Return the width of the image.
	 * @return width in pixels
	 */
    public int getLogicalRowSize() {
        return logicalRowSize;
    }

    /**
     * Return the width of the image row in file.
     * @return the width in bytes
     */
    public int getPhysicalRowSize() {
        return physicalRowSize;
    }

    /**
     * Compute the number of trailing padding bytes.
     * @return the number of padding bytes
     */
    public int getPaddingBytes() {
        return physicalRowSize - logicalRowSize;
    }

	/**
	 * Read the header.
	 * @param eabr the EndianessAwareBinaryReader to use for reading
	 * @throws IOException
	 */
	public void read(EndianessAwareBinaryReader eabr) throws IOException {
        int i; short s; byte b, m;

        b = eabr.readByte();
        m = eabr.readByte();
        if (b != 'B' || m != 'M') throw new IOException("Bad header");

        i = eabr.readInt();	//total file length
        i = eabr.readInt();	//reserved
        i = eabr.readInt();	//offeset to begin of bitmap data
        i = eabr.readInt();	//length of Bitmap Info Header
        
        i = eabr.readInt(); setWidth(i);
        height = eabr.readInt();

        s = eabr.readShort();
        if (s != 1) throw new IOException("Wrong number of planes");
        
        s = eabr.readShort();
        if (s != 24) throw new IOException("Unsupported bit depth");

        i = eabr.readInt();
        if (i != 0) throw new IOException("Unsupported compression");

        i = eabr.readInt();	//Bitmap data size
        i = eabr.readInt();	//HResolution
        i = eabr.readInt();	//VResolution
        i = eabr.readInt();	//Colors
        i = eabr.readInt();	//Important colors
	}
	
    /**
     * Write the header.
     * @param eabw the EndianessAwareBinaryWriter instance to use for writing
     * @throws IOException
     */
	public void write(EndianessAwareBinaryWriter eabw) throws IOException {
        eabw.writeByte('B');
        eabw.writeByte('M');
        eabw.writeInt(54 + height * getPhysicalRowSize());	//file size
        eabw.writeInt(0);	//2 reserved shorts
        eabw.writeInt(54);	//offset to data
        eabw.writeInt(40);	//BITMAPINFOHEADER size
        eabw.writeInt(width);
        eabw.writeInt(height);
        eabw.writeShort(1);		//1 plane
        eabw.writeShort(24);	//RGA
        eabw.writeInt(0);		//no compression
        eabw.writeInt(height * getPhysicalRowSize());
        eabw.writeInt(0);
        eabw.writeInt(0);
        eabw.writeInt(0);
        eabw.writeInt(0);
	}
}
