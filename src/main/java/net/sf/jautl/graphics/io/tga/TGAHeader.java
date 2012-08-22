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
package net.sf.jautl.graphics.io.tga;

import java.io.IOException;

import net.sf.jautl.io.EndianessAwareBinaryReader;
import net.sf.jautl.io.EndianessAwareBinaryWriter;

/**
 * This class encapsulates the processing required to handle TGA file headers. 
 */
public class TGAHeader {
	private int width;
	private int height;
	
    /**
     * The constructor.
     */
	public TGAHeader() {
	}
	
	/**
	 * The constructor.
	 * @param width the width in pixels of the image
	 * @param height the height in pixels of the image
	 */
	public TGAHeader(int width, int height) {
		this.width = width;
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
	 * Get the height of the image.
	 * @return the height in pixels
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Read the header.
	 * @param eabr the EndianessAwareBinaryReader to use for reading
	 * @throws IOException
	 */
	public void read(EndianessAwareBinaryReader eabr) throws IOException {
		byte b;
		
		boolean ok = true;
		
        b = eabr.readByte(); ok &= (b == 0);
        b = eabr.readByte(); ok &= (b == 0);
        b = eabr.readByte(); ok &= (b == 2);
        for (int Index = 0; Index < 5; Index++) {
            b = eabr.readByte(); ok &= (b == 0);
        }
        
        eabr.readShort();
        eabr.readShort();
        
        width = (eabr.readShort() & 0xFFFF);
        height = (eabr.readShort() & 0xFFFF);
        b = eabr.readByte(); ok &= (b == 24);
        b = eabr.readByte(); ok &= (b == 0);
        
        if (!ok)
        	throw new IOException("Bad header");
	}

    /**
     * Write the header.
     * @param eabw the EndianessAwareBinaryWriter instance to use for writing
     * @throws IOException
     */
	public void write(EndianessAwareBinaryWriter eabw) throws IOException {
        eabw.writeByte(0);	                        //IDLength
		eabw.writeByte(0);	                        //color map type
		eabw.writeByte(2);                          //image type
        for (int Index = 0; Index < 5; Index++)	    //CMap spec
        	eabw.writeByte(0);
        eabw.writeShort(0);	                    	//image x origin
        eabw.writeShort(0);	                   		//image y origin
        eabw.writeShort(width);
        eabw.writeShort(height);
        eabw.writeByte(24);	                        //pixel depth
        eabw.writeByte(0);	                        //image desciptor
	}
}
