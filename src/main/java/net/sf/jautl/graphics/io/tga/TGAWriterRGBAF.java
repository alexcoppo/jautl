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
package net.sf.jautl.graphics.io.tga;

import java.io.IOException;

import net.sf.jautl.graphics.colors.ColorRGBAF;
import net.sf.jautl.graphics.image.IImageSourceRGBAF;
import net.sf.jautl.graphics.io.ImageWriterRGBAF;
import net.sf.jautl.io.BinaryFileWriterHelper;
import net.sf.jautl.io.Endianess;
import net.sf.jautl.io.EndianessAwareBinaryWriter;

/**
 * TGA file format writer. 
 */
public class TGAWriterRGBAF extends ImageWriterRGBAF {
	/**
	 * The constructor.
	 * @param filename the name of the file
	 * @param source the source of the image data 
	 */
	public TGAWriterRGBAF(String filename, IImageSourceRGBAF source) {
		super(filename, source);
	}
	
	@Override
	public void write() throws IOException {
		//open file
		BinaryFileWriterHelper bfwh = new BinaryFileWriterHelper();
		bfwh.open(filename);
		EndianessAwareBinaryWriter eabw = new EndianessAwareBinaryWriter(bfwh.getDataOutputStream(), Endianess.SMALL_ENDIAN);

		//write header
		TGAHeader bmpHdr = new TGAHeader(source.getWidth(), source.getHeight());
    	bmpHdr.write(eabw);

    	//write data
    	TGARow row = new TGARow(source.getWidth());
		ColorRGBAF color = new ColorRGBAF();
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
            	source.getColor(x, y, color);
        		row.setRGB(x, color);
            }
    		row.write(eabw.getOutput());
        }
		
    	//close file
        bfwh.flush();
        bfwh.close();
	}
}
