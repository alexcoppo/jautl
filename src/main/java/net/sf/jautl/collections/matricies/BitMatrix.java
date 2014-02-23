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
package net.sf.jautl.collections.matricies;

import java.util.BitSet;

/**
 * A matrix of bits.
 */
public class BitMatrix extends MatrixBase {
	/**
	 * Default constructor; creates an empty matrix.
	 */
	public BitMatrix() {
		data = new BitSet();
	}

	/**
	 * Constructor which creates matrix of given dimensions.
	 * @param width the width of the matrix
	 * @param height the height of the matrix
	 */
	public BitMatrix(int width, int height) {
		super(width, height);
		data = new BitSet(width * height);
	}

	/**
	 * Set all bits to the given value.
	 * @param value the value to which set the bits
	 */
	public void setAll(boolean value) {
		data.set(0, getSize(), value);
	}

	/**
	 * Set a value at a given X, Y coordinate
	 * @param x the horizontal coordinate of the value
	 * @param y the vertical coordinate of the value
	 * @param value the value to set
	 */
	public void setXY(int x, int y, boolean value) {
		data.set(cellIndexXY(x, y), value);
	}
	
	/**
	 * Set a value at a given Row, Column coordinate
	 * @param r the row of the value
	 * @param c the column of the value
	 * @param value the value to set
	 */
	public void setRC(int r, int c, boolean value) {
		data.set(cellIndexRC(r, c), value);
	}
	
	/**
	 * Get the value at a given X, Y coordinate.
	 * @param x the horizontal coordinate of the value
	 * @param y the vertical coordinate of the value
	 * @return the value at the specified coordinates
	 */
	public boolean getXY(int x, int y) {
		return data.get(cellIndexXY(x, y));
	}
	
	/**
	 * Get the value at a specified Row, Column location.
	 * @param r the row of the value
	 * @param c the column of the value
	 * @return the value at the specified coordinates
	 */
	public boolean getRC(int r, int c) {
		return data.get(cellIndexRC(r, c));
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
			data = new BitSet(width * height);
			return true;
		} else {
			setAll(false);
			return false;
		}
	}
	
	/**
	 * Assign the contents of another matrix this instance, resizing
	 * it if necessary.
	 * @param rhs the instance from which get the values
	 */
	public void assign(BitMatrix rhs) {
		resize(rhs.getWidth(), rhs.getHeight());
		
		for (int index = 0; index < width * height; index++)
			data.set(index, rhs.data.get(index));
	}

	private BitSet data;
}