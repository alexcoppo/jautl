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
package net.sf.jautl.graphics.hashnoise;

import net.sf.jautl.md.SipHash_2_4;

/**
 * 
 */
public class CellNoise2 extends HashNoise2 {
	private int cellsX;
	private int cellsY;

    /**
     * The constructor.
     */
	public CellNoise2() {
		super(new SipHash_2_4());
	}
	
    /**
     * Return the number of cells per abscissa unit.
     * @return number of cells per abscissa unit
     */
	public final int getCellsX() {
		return cellsX;
	}
	
	public final void setCellsX(int cellsX) {
		this.cellsX = cellsX;
	}
	
	public final int getCellsY() {
		return cellsY;
	}
	
	public final void setCellsY(int cellsY) {
		this.cellsY = cellsY;
	}
	
	@Override
	public double generate(double x, double y) {
		final int _7F = 0x7FFFFFFF;
		final double _7FD = (double)_7F;

		initiate();
		
		de.add(Double.toHexString((int)(x * cellsX)));
		de.add(Double.toHexString((int)(y * cellsY)));

		int i = terminate();

		double z = (i & _7F) / _7FD;

		return z;
	}
}
