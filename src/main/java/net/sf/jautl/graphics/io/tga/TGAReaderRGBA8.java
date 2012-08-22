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

import net.sf.jautl.graphics.colors.ColorRGBA8;
import net.sf.jautl.graphics.image.IImageSinkRGBA8;
import net.sf.jautl.graphics.io.ImageReaderRGBA8;
import net.sf.jautl.io.BinaryFileReaderHelper;
import net.sf.jautl.io.Endianess;
import net.sf.jautl.io.EndianessAwareBinaryReader;

/**
 * TGA file format reader. 
 */
public class TGAReaderRGBA8 extends ImageReaderRGBA8 {
	/**
	 * The constructor.
	 * @param filename the name of the file
	 * @param sink the destination of the image data
	 */
	public TGAReaderRGBA8(String filename, IImageSinkRGBA8 sink) {
		super(filename, sink);
	}
	
	@Override
	public void read() throws IOException {
		//open file
		BinaryFileReaderHelper bfrh = new BinaryFileReaderHelper();
		bfrh.open(filename);
		EndianessAwareBinaryReader eabr = new EndianessAwareBinaryReader(bfrh.getDataInputStream(), Endianess.SMALL_ENDIAN);

		//read header
		TGAHeader bmpHdr = new TGAHeader();
		bmpHdr.read(eabr);
    	sink.resize(bmpHdr.getWidth(), bmpHdr.getHeight());

    	//read data
    	TGARow row = new TGARow(bmpHdr.getWidth());
		ColorRGBA8 color = new ColorRGBA8();
        for (int y = 0; y < bmpHdr.getHeight(); y++) {
        	row.read(eabr.getInput());
            for (int x = 0; x < bmpHdr.getWidth(); x++) {
            	row.getRGB(x, color);
            	sink.setColor(x, y, color);
            }
        }

    	//close file
        bfrh.close();
	}
}
