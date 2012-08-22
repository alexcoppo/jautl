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
package net.sf.jautl.graphics.colormaps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sf.jautl.graphics.colormaps.IColorMapRGBAF;
import net.sf.jautl.graphics.colors.ColorRGBAF;

public class ImageBasedColorMap implements IColorMapRGBAF {
	private BufferedImage im;
	private int rowIndex;
	
	public ImageBasedColorMap(String filename, int rowIndex) {
		try {
			im = ImageIO.read(new File(filename));
			this.rowIndex = rowIndex;
		} catch (IOException e) {
		} 
	}

	public boolean isValid() {
		return im != null;
	}

	public void lookup(double x, ColorRGBAF color) {
		if (!isValid()) {
			//TODO set to default;
			return;
		}

		final double ALMOST_ONE = .999999;
		
		if (x < 0) x = 0;
		if (x > ALMOST_ONE) x = ALMOST_ONE;
		
		int ix = (int)(im.getWidth() * x);

		int argb = im.getRGB(ix, rowIndex);
		
		color.setR(((argb & 0x000000FF) >>  0) / 255.0f);
		color.setG(((argb & 0x0000FF00) >>  8) / 255.0f);
		color.setB(((argb & 0x00FF0000) >> 16) / 255.0f);
		color.setA(((argb & 0xFF000000) >> 24) / 255.0f);
	}
}
