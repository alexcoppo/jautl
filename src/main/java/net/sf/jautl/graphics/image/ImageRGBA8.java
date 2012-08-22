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
package net.sf.jautl.graphics.image;

import net.sf.jautl.graphics.colors.ColorRGBA8;

/**
 * A rectangular matrix of RGBA colors specified as byte values.
 */
public class ImageRGBA8 implements IImageSourceRGBA8, IImageSinkRGBA8 {
	private int width;
	private int height;
	private byte[][] rows;

	/**
	 * Default constructor. Creates an empty, matrix.
	 */
	public ImageRGBA8() {
	}
	
	/**
	 * Create a new image of given dimensions.
	 * @param width the width of the matrix
	 * @param height the height of the matrix
	 */
	public ImageRGBA8(int width, int height) {
		resize(width, height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	/**
	 * Return whether the matrix is empty.
	 */
	public boolean isEmpty() {
		return width * height == 0;
	}
	
	/**
	 * Reallocate the matrix to new dimensions. 
	 * @param width the new width of the matrix
	 * @param height the new height of the matrix
	 * @return whether the matrix was actually reallocated
	 */
	public boolean resize(int width, int height) {
		if (width == this.width && height == this.height)
			return false;

		this.width = width;
		this.height = height;

		rows = new byte[height][];
		for (int row = 0; row < height; row++)
			rows[row] = new byte[4 * width];

		return true;
	}

	/**
	 * Get a pixel color.
	 * @param x the x (horizontal) coordinate of the pixel
	 * @param y the y (vertical) coordinate of the pixel
	 * @param color the color to set to the vlaue of the given pixel
	 */
	public void getColor(int x, int y, ColorRGBA8 color) {
		byte[] row = rows[y];
		
		color.setR((int)row[4 * x + 0] & 0xFF);
		color.setG((int)row[4 * x + 1] & 0xFF);
		color.setB((int)row[4 * x + 2] & 0xFF);
		color.setA((int)row[4 * x + 3] & 0xFF);
	}

	/**
	 * Set a pixel to a given color.
	 * @param x the x (horizontal) coordinate of the pixel
	 * @param y the y (vertical) coordinate of the pixel
	 * @param color the color to set the pixel to
	 */
	public void setColor(int x, int y, ColorRGBA8 color) {
		byte[] row = rows[y];
		
		row[4 * x + 0] = (byte)color.getR();
		row[4 * x + 1] = (byte)color.getG();
		row[4 * x + 2] = (byte)color.getB();
		row[4 * x + 3] = (byte)color.getA();
	}

	/**
	 * Set all pixels to the same color.
	 * @param color the color to set the pixels to
	 */
	public void setAll(ColorRGBA8 color) {
		for (int y = 0; y < height; y++) {
			byte[] row = rows[y];
			
			byte r = (byte)color.getR();
			byte g = (byte)color.getG();
			byte b = (byte)color.getB();
			byte a = (byte)color.getA();
			
			int pos = 0;
			for (int x = 0; x < width; x++) {
				row[pos++] = r;
				row[pos++] = g;
				row[pos++] = b;
				row[pos++] = a;
			}
		}
	}
}
