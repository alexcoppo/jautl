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
package net.sf.jautl.graphics.image;

import net.sf.jautl.graphics.colors.ColorGreyF;

/**
 * A rectangular matrix of grey levels specified as float values.
 */
public class ImageGreyF implements IImageSourceGreyF, IImageSinkGreyF {
	private int width;
	private int height;
	private float[][] rows;

	/**
	 * Default constructor. Creates an empty, matrix.
	 */
	public ImageGreyF() {
	}
	
	/**
	 * Create a new image of given dimensions.
	 * @param width the width of the matrix
	 * @param height the height of the matrix
	 */
	public ImageGreyF(int width, int height) {
		resize(width, height);
	}
	
	/**
	 * Return the width of the source.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Return the height of the source.
	 */
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

		rows = new float[height][];
		for (int row = 0; row < height; row++)
			rows[row] = new float[width];

		return true;
	}

	/**
	 * Get a pixel grey level.
	 * @param x the x (horizontal) coordinate of the pixel
	 * @param y the y (vertical) coordinate of the pixel
	 * @param grey the grey level to set to the vlaue of the given pixel
	 */
	public void getColor(int x, int y, ColorGreyF grey) {
		float[] row = rows[y];
		
        grey.setGrey(row[x]);
	}

	/**
	 * Set a pixel to a given grey level.
	 * @param x the x (horizontal) coordinate of the pixel
	 * @param y the y (vertical) coordinate of the pixel
	 * @param greyLevel the grey level to set the pixel to
	 */
	public void setColor(int x, int y, ColorGreyF greyLevel) {
		float[] row = rows[y];
		
        row[x] = greyLevel.getGrey();
	}

	/**
	 * Set all pixels to the same grey level.
	 * @param greyLevel the grey level to set the pixels to
	 */
	public void setAll(ColorGreyF greyLevel) {
		for (int y = 0; y < height; y++) {
			float[] row = rows[y];
			for (int x = 0; x < width; x++)
                row[x] = greyLevel.getGrey();
		}
	}
}
