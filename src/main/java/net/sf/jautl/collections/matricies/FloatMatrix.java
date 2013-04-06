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
package net.sf.jautl.collections.matricies;

import java.util.Arrays;

/**
 * A matrix of floats.
 * 
 * Implemented as a non-generic class in order to remove all un-needed checks
 * and boxings/unboxings. 
 */
public class FloatMatrix extends MatrixBase {
	/**
	 * Default constructor; creates an empty matrix.
	 */
	public FloatMatrix() {
		data = new float[0];
	}

	/**
	 * Constructor which creates matrix of given dimensions.
	 * @param width the width of the matrix
	 * @param height the height of the matrix
	 */
	public FloatMatrix(int width, int height) {
		super(width, height);
		data = new float[width * height];
	}

	/**
	 * Assign the contents of another matrix this instance, resizing
	 * it if necessary.
	 * @param rhs the instance from which get the values
	 */
	public void assign(FloatMatrix rhs) {
		resize(rhs.getWidth(), rhs.getHeight());
		
		for (int index = 0; index < width * height; index++)
			data[index] = rhs.data[index];
	}

	/**
	 * Set all entries to the given value.
	 * @param value the value to which set the entries
	 */
	public void setAll(float value) {
		Arrays.fill(data, value);
	}
	
	/**
	 * Set a value at a given X, Y coordinate
	 * @param x the horizontal coordinate of the value
	 * @param y the vertical coordinate of the value
	 * @param value the value to set
	 */
	public void setXY(int x, int y, float value) {
		int index = cellIndexXY(x, y);
		data[index] = value;
	}
	
	/**
	 * Set a value at a given Row, Column coordinate
	 * @param r the row of the value
	 * @param c the column of the value
	 * @param value the value to set
	 */
	public void setRC(int r, int c, float value) {
		int index = cellIndexRC(r, c);
		data[index] = value;
	}
	
	/**
	 * Get the value at a given X, Y coordinate.
	 * @param x the horizontal coordinate of the value
	 * @param y the vertical coordinate of the value
	 * @return the value at the specified coordinates
	 */
	public float getXY(int x, int y) {
		int index = cellIndexXY(x, y);
		return data[index];
	}
	
	/**
	 * Get the value at a specified Row, Column location.
	 * @param r the row of the value
	 * @param c the column of the value
	 * @return the value at the specified coordinates
	 */
	public float getRC(int r, int c) {
		int index = cellIndexRC(r, c);
		return data[index];
	}
	
	/**
	 * Resize the matrix to new dimensions. If the matrix
	 * has already the required sizes, data are simply cleared.
	 * @param width the new width
	 * @param height the new height
	 * @return whether the underlying storage was reallocated
	 */
	public boolean resize(int width, int height) {
		if (super.resize(width, height)) {
			data = new float[width * height];
			return true;
		} else {
			setAll(0);
			return false;
		}
	}
	
	private float[] data;
}