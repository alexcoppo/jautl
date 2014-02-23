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
package net.sf.jautl.graphics.io.pfm;

import java.io.DataOutputStream;
import java.io.IOException;
import net.sf.jautl.graphics.colors.ColorRGBA8;
import net.sf.jautl.graphics.image.IImageSourceRGBA8;
import net.sf.jautl.graphics.io.ImageWriterRGBA8;
import net.sf.jautl.io.BinaryFileWriterHelper;

public class PFMWriterRGBA8 extends ImageWriterRGBA8 {
	protected PFMWriterRGBA8(String filename, IImageSourceRGBA8 source) {
		super(filename, source);
	}

	public void write() throws IOException {
		//open file
		BinaryFileWriterHelper bfwh = new BinaryFileWriterHelper();
		bfwh.open(filename);

		DataOutputStream dos = bfwh.getDataOutputStream();
		
		PFMHeader hdr = new PFMHeader(source);
		hdr.write(dos);

		//write data
		ColorRGBA8 color = new ColorRGBA8();
		for (int y = 0; y < hdr.getHeight(); y++)
			for (int x = 0; x < hdr.getWidth(); x++) {
				source.getColor(x, y, color);
				dos.writeFloat(color.getR() / 255.0f);
				dos.writeFloat(color.getG() / 255.0f);
				dos.writeFloat(color.getB() / 255.0f);
			}

		bfwh.flush();
		bfwh.close();
	}
}
