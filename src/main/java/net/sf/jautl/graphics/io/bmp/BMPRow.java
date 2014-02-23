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
package net.sf.jautl.graphics.io.bmp;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.sf.jautl.graphics.colors.ColorRGBA8;
import net.sf.jautl.graphics.colors.ColorRGBAF;

/**
 * This class encapsulates the processing required to handle a row of data in
 * a BMP file.
 */
public class BMPRow {
	/**
	 * The constructor.
	 * @param width the number of pixels in the row
	 */
	public BMPRow(int width) {
		logicalSize = 3 * width;

        physicalSize = logicalSize;

        while ((physicalSize & 0x3) != 0)
            physicalSize++;
		
		bytes = new byte[physicalSize];
	}
	
	/**
	 * Read a row.
	 * @param in the stream from which read the data
	 * @throws IOException
	 */
	public void read(DataInput in) throws IOException {
		in.readFully(bytes);
	}
	
	/**
	 * Write the row.
	 * @param out the stream on which write data
	 * @throws IOException
	 */
	public void write(DataOutput out) throws IOException {
		out.write(bytes);
	}

	/**
	 * Set a pixel to a given color.
	 * @param index the index of the pixel
	 * @param color the color to set
	 */
	public void setRGB(int index, ColorRGBA8 color) {
		bytes[index * 3 + 0] = (byte)color.getB();
		bytes[index * 3 + 1] = (byte)color.getG();
		bytes[index * 3 + 2] = (byte)color.getR();
	}
	
	/**
	 * Set a pixel to a given color.
	 * @param index the index of the pixel
	 * @param color the color to set
	 */
	public void setRGB(int index, ColorRGBAF color) {
		bytes[index * 3 + 0] = floatToByte(color.getB());
		bytes[index * 3 + 1] = floatToByte(color.getG());
		bytes[index * 3 + 2] = floatToByte(color.getR());
	}

	/**
	 * Get the color of a pixel. 
	 * @param index the index of the pixel
	 * @param color to color to load
	 */
	public void getRGB(int index, ColorRGBA8 color) {
		color.setA(ColorRGBA8.MAX_VALUE);
		color.setR(bytes[index * 3 + 2]);
		color.setG(bytes[index * 3 + 1]);
		color.setB(bytes[index * 3 + 0]);
	}

	/**
	 * Get the color of a pixel. 
	 * @param index the index of the pixel
	 * @param color to color to load
	 */
	public void getRGB(int index, ColorRGBAF color) {
		color.setA(ColorRGBAF.MAX_VALUE);
		color.setR(byteToFloat(bytes[index * 3 + 2]));
		color.setG(byteToFloat(bytes[index * 3 + 1]));
		color.setB(byteToFloat(bytes[index * 3 + 0]));
	}

	private static float byteToFloat(byte b) {
		int i = (int)b;
		i &= 0xFF;
		return i / 255f;
	}

	private static byte floatToByte(float f) {
		return (byte)(int)(f * 255.999);
	}
	
    private int logicalSize;
    private int physicalSize;
	private byte[] bytes;
}
