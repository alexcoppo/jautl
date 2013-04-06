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

/**
 * This class is the base of all matrices.
 * 
 * There is support for two different coordinate systems:
 * <ol>
 * <li>Row Column, with the 0,0 coordinate on the upper left corner of the matrix;</li>
 * <li>X, Y with the 0,0 coordinate on the lower left corner of the matrix.</li>
 * </ol>
 */
public abstract class MatrixBase {
	/** The width of the matrix. */
	protected int width;
	/** The height of the matrix. */
	protected int height;

	/**
	 * Default constructor: allocates an empty matrix.
	 */
    protected MatrixBase() {
		width = 0;
		height = 0;
	}

    /**
     * Sizing constructor.
     * @param width the width of the matrix
     * @param height the height of the matrix
     */
    protected MatrixBase(int width, int height) {
		this.width = width;
		this.height = height;
	}

    /**
     * Return the width of the matrix.
     * @return the value of the width
     */
	public final int getWidth() {
		return width;
	}

	/**
	 * Return the height of the matrix.
	 * @return the value of the height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * Return the size of the matrix.
	 * @return the size of the matrix in elements
	 */
	public final int getSize() {
		return width * height;
	}

	/**
	 * Return whether the matrix is empty.
	 * @return the result of the check
	 */
	public boolean isEmpty() {
		return getSize() == 0;
	}
	
	/**
	 * Reallocate the matrix. It must be appropriately redefined in derived
	 * classes.
	 * 
	 * @param width the new width of the matrix
	 * @param height the new height of the matrix
	 * @return whether the matrix storage should be reallocated
	 */
	public boolean resize(int width, int height) {
		boolean shouldReallocate = (width * height) != getSize();

		this.width = width;
		this.height = height;

		return shouldReallocate;
	}
	
	/**
	 * Check whether a coordinate pair is inside the matrix
	 * @param x the x of the pair
	 * @param y the y of the pair
	 * @return the result of the test
	 */
	public final boolean isInsideXY(int x, int y) {
		if (x < 0) return false;
		if (x >= width) return false;
		if (y < 0) return false;
		if (y >= height) return false;
		
		return true;
	}

	/**
	 * Check whether a coordinate pair is inside the matrix
	 * @param r the row of the pair
	 * @param c the column of the pair
	 * @return the result of the test
	 */
	public final boolean isInsideRC(int r, int c) {
		if (c < 0) return false;
		if (c >= width) return false;
		if (r < 0) return false;
		if (r >= height) return false;
		
		return true;
	}

	/**
	 * Clip the x coordinate to a value inside the matrix
	 * @param x the value to be clipped
	 * @return the clipped value
	 */
	public final int clipX(int x) {
		if (x < 0) return 0;
		if (x >= width) return width - 1;
		return x;
	}

	/**
	 * Clip the y coordinate to a value inside the matrix
	 * @param y the value to be clipped
	 * @return the clipped value
	 */
	public final int clipY(int y) {
		if (y < 0) return 0;
		if (y >= height) return height - 1;
		return y;
	}

	/**
	 * Clip the row coordinate to a value inside the matrix
	 * @param row the value to be clipped
	 * @return the clipped value
	 */
	public final int clipR(int row) {
		if (row < 0) return 0;
		if (row >= height) return height - 1;
		return row;
	}

	/**
	 * Clip the column coordinate to a value inside the matrix
	 * @param col the value to be clipped
	 * @return the clipped value
	 */
	public final int clipC(int col) {
		if (col < 0) return 0;
		if (col >= width) return width - 1;
		return col;
	}

	/**
	 * Compute a linear index for a row-wise storage representation.
	 * @param x the x index of the cell
	 * @param y the y index of the cell
	 * @return result of the calculation
	 */
	protected int cellIndexXY(int x, int y) {
		return x + (height - 1 - y) * width;
	}

	/**
	 * Compute a linear index for a row-wise storage representation.
	 * @param r the row index of the cell
	 * @param c the column index of the cell
	 * @return result of the calculation
	 */
	protected int cellIndexRC(int r, int c) {
		return c + r * width;
	}
}
